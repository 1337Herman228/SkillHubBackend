package by.bsuir.skillhub.services;

import by.bsuir.skillhub.dto.ContinueCourseDto;
import by.bsuir.skillhub.entity.*;
import by.bsuir.skillhub.repo.ChaptersRepository;
import by.bsuir.skillhub.repo.CourseAccessRepository;
import by.bsuir.skillhub.repo.LessonsRepository;
import by.bsuir.skillhub.repo.UserProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursesService {

    private final CourseAccessRepository courseAccessRepository;
    private final UserProgressRepository userProgressRepository;
    private final ChaptersRepository chaptersRepository;
    private final LessonsRepository lessonsRepository;

    public List<Courses> getUserCoursesWithAccess(Users user) {
        List<CourseAccess> allUserCourses = courseAccessRepository.findByUser(user);

        List<Courses> userCoursesWithAccess = new ArrayList<>();
        for(CourseAccess courseAccess : allUserCourses) {
            if(courseAccess.getStatus() == CourseAccess.AccessStatus.APPROVED){
                userCoursesWithAccess.add(courseAccess.getCourse());
            }
        }

        return userCoursesWithAccess;
    }

    public List<UserProgress> getUserFinishedLessons(Users user, Courses course) {
        //Кол-во пройденных уроков
        return userProgressRepository.findByUserAndCourse(user,course);
    }

    public List<Lessons> getAllCourseLessons(Courses course) {
        List<Chapters> allCourseChapters = chaptersRepository.findByCourse(course);
        //Кол-во всех уроков в курсе
        List<Lessons> allCourseLessons = new ArrayList<>();
        for(Chapters chapter : allCourseChapters) {
            List<Lessons> lessons = lessonsRepository.findByChapter(chapter);
            allCourseLessons.addAll(lessons);
        }
        return allCourseLessons;
    }

    public float calculateUserProgressInPercents(Users user, Courses course) {
        List<UserProgress> userProgress = getUserFinishedLessons(user,course);
        List<Lessons> allCourseLessons = getAllCourseLessons(course);

        return ((float) userProgress.size() / allCourseLessons.size())*100;
    }

    public List<ContinueCourseDto> getContinueCourses(Users user) {
        List<Courses> userCoursesWithAccess = getUserCoursesWithAccess(user);

        List<ContinueCourseDto> continueCourseDtos = new ArrayList<>();
        for(Courses course : userCoursesWithAccess) {
            ContinueCourseDto continueCourseDto = new ContinueCourseDto();
            continueCourseDto.setCourse(course);
            continueCourseDto.setProgressInPercents(calculateUserProgressInPercents(user,course));
            continueCourseDto.setAllLessonsCount(getAllCourseLessons(course).size());
            continueCourseDto.setCompletedLessonsCount(getUserFinishedLessons(user,course).size());
            continueCourseDtos.add(continueCourseDto);
        }
        return continueCourseDtos;
    }


}
