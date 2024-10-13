package by.bsuir.skillhub.services;

import by.bsuir.skillhub.dto.AllCoursesDto;
import by.bsuir.skillhub.dto.ContinueCourseDto;
import by.bsuir.skillhub.dto.RequestAccessDto;
import by.bsuir.skillhub.entity.*;
import by.bsuir.skillhub.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoursesService {

    private final CourseAccessRepository courseAccessRepository;
    private final UserProgressRepository userProgressRepository;
    private final ChaptersRepository chaptersRepository;
    private final LessonsRepository lessonsRepository;
    private final CoursesRepository coursesRepository;
    private final ReviewsRepository reviewsRepository;
    private final UsersRepository usersRepository;

    public List<Courses> getUserCoursesWithAccess(Users user) {
        List<CourseAccess> allUserCourses = courseAccessRepository.findByUser(user);

        List<Courses> userCoursesWithAccess = new ArrayList<>();
        for (CourseAccess courseAccess : allUserCourses) {
            if (courseAccess.getStatus() == CourseAccess.AccessStatus.APPROVED) {
                userCoursesWithAccess.add(courseAccess.getCourse());
            }
        }

        return userCoursesWithAccess;
    }

    public List<UserProgress> getUserFinishedLessons(Users user, Courses course) {
        //Кол-во пройденных уроков
        return userProgressRepository.findByUserAndCourse(user, course);
    }

    public List<Lessons> getAllCourseLessons(Courses course) {
        List<Chapters> allCourseChapters = chaptersRepository.findByCourse(course);
        //Кол-во всех уроков в курсе
        List<Lessons> allCourseLessons = new ArrayList<>();
        for (Chapters chapter : allCourseChapters) {
            List<Lessons> lessons = lessonsRepository.findByChapter(chapter);
            allCourseLessons.addAll(lessons);
        }
        return allCourseLessons;
    }

    public float calculateUserProgressInPercents(Users user, Courses course) {
        List<UserProgress> userProgress = getUserFinishedLessons(user, course);
        List<Lessons> allCourseLessons = getAllCourseLessons(course);

        float progress = ((float) userProgress.size() / allCourseLessons.size()) * 100;
        if (Float.isNaN(progress)) progress = 0f;

        return progress;
    }

    public List<ContinueCourseDto> getContinueCourses(Users user) {
        List<Courses> userCoursesWithAccess = getUserCoursesWithAccess(user);

        List<ContinueCourseDto> continueCourseDtos = new ArrayList<>();
        for (Courses course : userCoursesWithAccess) {
            ContinueCourseDto continueCourseDto = new ContinueCourseDto();
            continueCourseDto.setCourse(course);
            continueCourseDto.setProgressInPercents(calculateUserProgressInPercents(user, course));
            continueCourseDto.setAllLessonsCount(getAllCourseLessons(course).size());
            continueCourseDto.setCompletedLessonsCount(getUserFinishedLessons(user, course).size());
            continueCourseDtos.add(continueCourseDto);
        }
        return continueCourseDtos;
    }

    public List<AllCoursesDto> getAllCoursesForUser(Users user) {
        List<Courses> allCourses = coursesRepository.findAll();
        return makeAllCoursesDtoList(allCourses, user);
    }

    public List<AllCoursesDto> findCoursesByNameForUser(Users user, String courseName) {
        List<Courses> allCourses = coursesRepository.findByCourseNameContainingIgnoreCase(courseName);
        return makeAllCoursesDtoList(allCourses, user);
    }

    public List<AllCoursesDto> makeAllCoursesDtoList (List<Courses> courses, Users user) {
        List<AllCoursesDto> allCourseDtos = new ArrayList<>();
        for (Courses course : courses) {
            int sumDuration = 0;
            List<Lessons> allCourseLessons = getAllCourseLessons(course);
            for (Lessons lesson : allCourseLessons) {
                if (lesson.getDuration() == null) {
                    sumDuration += 0;
                } else {
                    sumDuration += lesson.getDuration();

                }
            }

            Integer sumRating = 0;
            List<Reviews> courseReviews = reviewsRepository.findByCourse(course);
            for (Reviews review : courseReviews) {
                sumRating += review.getRating();
            }

            float rating = (float) sumRating / courseReviews.size();
            if (Float.isNaN(rating)) rating = 0f;

            Optional<CourseAccess> courseAccess = courseAccessRepository.findByUserAndCourse(user, course);

            AllCoursesDto allCoursesDto = new AllCoursesDto();
            allCoursesDto.setCourse(course);
            allCoursesDto.setDuration(sumDuration);
            allCoursesDto.setAllLessonsCount(allCourseLessons.size());
            allCoursesDto.setRating(rating);
            allCoursesDto.setReviewsCount(courseReviews.size());
            if (courseAccess.isPresent()) {
                allCoursesDto.setStatus(String.valueOf(courseAccess.get().getStatus()));
            } else {
                allCoursesDto.setStatus("NO_REQUEST");
            }
            allCourseDtos.add(allCoursesDto);

        }
        return allCourseDtos;
    }

    public HttpStatus requestAccess(RequestAccessDto requestAccessDto) throws Exception {

        try {
            Optional<CourseAccess> possibleCourseAccess = courseAccessRepository.findByUserAndCourse(
                    usersRepository.findById(requestAccessDto.getUserId()).get(),
                    coursesRepository.findById(requestAccessDto.getCourseId()).get()
            );
            if (possibleCourseAccess.isPresent()) {

                if(possibleCourseAccess.get().getStatus() == CourseAccess.AccessStatus.REJECTED)
                {
                    possibleCourseAccess.get().setStatus(CourseAccess.AccessStatus.PENDING);
                    possibleCourseAccess.get().setRequestDate(new Timestamp(new Date().getTime()));
                    possibleCourseAccess.get().setGrantedDate(null);
                    courseAccessRepository.save(possibleCourseAccess.get());
                    return HttpStatus.OK;
                }
                else{
                    return HttpStatus.NOT_FOUND;
                }
            } else {
                CourseAccess courseAccess = new CourseAccess();
                courseAccess.setUser(usersRepository.findById(requestAccessDto.getUserId()).get());
                courseAccess.setCourse(coursesRepository.findById(requestAccessDto.getCourseId()).get());
                courseAccess.setRequestDate(new Timestamp(new Date().getTime()));
                courseAccess.setStatus(CourseAccess.AccessStatus.PENDING);
                courseAccessRepository.save(courseAccess);
                return HttpStatus.OK;
            }
        } catch (Exception e) {
            throw new Exception("Can't add access request", e);
        }
    }

}
