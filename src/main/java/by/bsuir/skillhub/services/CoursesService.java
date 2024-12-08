package by.bsuir.skillhub.services;

import by.bsuir.skillhub.dto.*;
import by.bsuir.skillhub.entity.*;
import by.bsuir.skillhub.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final ResourcesRepository resourcesRepository;
    private final VideoLessonsRepository videoLessonsRepository;
    private final TextLessonsRepository textLessonsRepository;
    private final TestsRepository testsRepository;
    private final TestQuestionsRepository testQuestionsRepository;
    private final TestAnswersRepository testAnswersRepository;
    private final UserNotesRepository userNotesRepository;
    private final QuestionsRepository questionsRepository;
    private final AnswersRepository answersRepository;

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

    public List<GetAccessUsersDto> getRequestAccessUsers(Long courseId) {
        List<CourseAccess> courseAccessList = courseAccessRepository.findByCourse(coursesRepository.findById(courseId).get());
        return getAccessList(courseAccessList, CourseAccess.AccessStatus.PENDING);
    }

    public List<GetAccessUsersDto> getRequestAccessUsersByName(GetHasAccessUsersByNameDto getHasAccessUsersByNameDto) {
        List<CourseAccess> courseAccessList =
                courseAccessRepository.findByCourse(coursesRepository.findById(getHasAccessUsersByNameDto.getCourseId()).get());
        return getAccessListByName(courseAccessList, CourseAccess.AccessStatus.PENDING, getHasAccessUsersByNameDto.getUsername());
    }

    public List<GetAccessUsersDto> getHasAccessUsers(Long courseId) {
        List<CourseAccess> courseAccessList = courseAccessRepository.findByCourse(coursesRepository.findById(courseId).get());
        return getAccessList(courseAccessList, CourseAccess.AccessStatus.APPROVED);
    }

    public List<GetAccessUsersDto> getHasAccessUsersByName(GetHasAccessUsersByNameDto getHasAccessUsersByNameDto) {
        List<CourseAccess> courseAccessList = courseAccessRepository.findByCourse(coursesRepository.findById(getHasAccessUsersByNameDto.getCourseId()).get());
        return getAccessListByName(courseAccessList, CourseAccess.AccessStatus.APPROVED, getHasAccessUsersByNameDto.getUsername());
    }

    public HttpStatus approveCourseAccess(EditCourseAccessDto editCourseAccessDto) {
        try {
            CourseAccess courseAccess = courseAccessRepository.findById(editCourseAccessDto.getAccessId()).get();
            courseAccess.setStatus(CourseAccess.AccessStatus.APPROVED);
            courseAccess.setGrantedDate(new Timestamp(new Date().getTime()));
            courseAccessRepository.save(courseAccess);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public HttpStatus rejectCourseAccess(EditCourseAccessDto editCourseAccessDto) {
        try {
            CourseAccess courseAccess = courseAccessRepository.findById(editCourseAccessDto.getAccessId()).get();
            courseAccess.setStatus(CourseAccess.AccessStatus.REJECTED);
            courseAccess.setGrantedDate(new Timestamp(new Date().getTime()));
            courseAccessRepository.save(courseAccess);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


    private List<GetAccessUsersDto> getAccessList(List<CourseAccess> courseAccessList, CourseAccess.AccessStatus status) {
        List<GetAccessUsersDto> getAccessUsersDtoList = new ArrayList<>();
        for (CourseAccess courseAccess : courseAccessList) {
            if (courseAccess.getStatus() == status) {

                getAccessUserDto(getAccessUsersDtoList, courseAccess);
            }
        }
        return getAccessUsersDtoList;
    }

    private List<GetAccessUsersDto> getAccessListByName(List<CourseAccess> courseAccessList, CourseAccess.AccessStatus status, String username) {
        List<GetAccessUsersDto> getAccessUsersDtoList = new ArrayList<>();
        for (CourseAccess courseAccess : courseAccessList) {
            if (courseAccess.getStatus() == status && (
                    courseAccess.getUser().getPerson().getName().contains(username) ||
                            courseAccess.getUser().getPerson().getSurname().contains(username) ||
                            (courseAccess.getUser().getPerson().getName() + " " + courseAccess.getUser().getPerson().getSurname()).contains(username))
            ) {

                getAccessUserDto(getAccessUsersDtoList, courseAccess);
            }
        }
        return getAccessUsersDtoList;
    }

    public HttpStatus removeUserAccess(Long userId, Long courseId) {
        try {
            CourseAccess courseAccess = courseAccessRepository.findByUserAndCourse(usersRepository.findById(userId).get(), coursesRepository.findById(courseId).get()).get();
            courseAccessRepository.delete(courseAccess);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    private void getAccessUserDto(List<GetAccessUsersDto> getAccessUsersDtoList, CourseAccess courseAccess) {
        GetAccessUsersDto getAccessUsersDto = new GetAccessUsersDto();
        getAccessUsersDto.setAccessId(courseAccess.getAccessId());
        getAccessUsersDto.setGrantedDate(courseAccess.getGrantedDate());
        getAccessUsersDto.setRequestDate(courseAccess.getRequestDate());

        Users user = courseAccess.getUser();
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setLogin(user.getLogin());
        userDto.setDiamonds(user.getDiamonds());
        userDto.setRole(user.getRole());
        userDto.setPerson(user.getPerson());
        getAccessUsersDto.setUser(userDto);

        getAccessUsersDtoList.add(getAccessUsersDto);
    }

    public List<UserProgress> getUserFinishedLessons(Users user, Courses course) {
        //Кол-во пройденных уроков
        return userProgressRepository.findByUserAndCourse(user, course);
    }

    public List<ChapterDto> getAllCourseChapters(Courses course) {
        List<Chapters> allCourseChapters = chaptersRepository.findByCourse(course);
        List<ChapterDto> allCourseChaptersDto = new ArrayList<>();
        for (Chapters chapter : allCourseChapters) {
            ChapterDto chapterDto = new ChapterDto();
            chapterDto.setCourseId(chapter.getCourse().getCourseId());
            chapterDto.setChapterTitle(chapter.getChapterTitle());
            chapterDto.setChapterOrder(chapter.getChapterOrder());
            chapterDto.setChapterId(chapter.getChapterId());
            chapterDto.setLessonsCount(getAllChapterLessons(chapter).size());
            chapterDto.setDuration(countLessonsDuration(getAllChapterLessons(chapter)));
            allCourseChaptersDto.add(chapterDto);
        }
        return allCourseChaptersDto;
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

    public List<Lessons> getAllChapterLessons(Chapters chapter) {
        return lessonsRepository.findByChapter(chapter);
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

    public UserProgressDto getUserProgress(Long userId, Long courseId) {
        Users user = usersRepository.findById(userId).get();
        Courses course = coursesRepository.findById(courseId).get();
        UserProgressDto userProgress = new UserProgressDto();
        userProgress.setProgressInPercents(calculateUserProgressInPercents(user, course));
        userProgress.setAllLessonsCount(getAllCourseLessons(course).size());
        userProgress.setCompletedLessonsCount(getUserFinishedLessons(user, course).size());
        return userProgress;
    }

    public HttpStatus markLessonAsPassed(Long userId, Long courseId, Long lessonId) {
        try {

            Users user = usersRepository.findById(userId).get();
            Courses course = coursesRepository.findById(courseId).get();
            Lessons lesson = lessonsRepository.findById(lessonId).get();
            UserProgress userProgress = new UserProgress();
            userProgress.setUser(user);
            userProgress.setCourse(course);
            userProgress.setLesson(lesson);
            userProgressRepository.save(userProgress);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    public HttpStatus markLessonAsUnpassed(Long userId, Long lessonId) {
        try {
            Users user = usersRepository.findById(userId).get();
            Lessons lesson = lessonsRepository.findById(lessonId).get();
            UserProgress userProgress = userProgressRepository.findByUserAndLesson(user,lesson).get();
            userProgressRepository.delete(userProgress);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    public boolean isLessonPassed(Long userId, Long lessonId) {
        try {
            Users user = usersRepository.findById(userId).get();
            Lessons lesson = lessonsRepository.findById(lessonId).get();
            return userProgressRepository.findByUserAndLesson(user, lesson).isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    public List<AllCoursesDto> getTeacherCourses(Users user) {
        List<Courses> teacherCourses = coursesRepository.findByAuthor(user);
        return makeAllCoursesDtoList(teacherCourses, user);
    }

    public List<AllCoursesDto> findTeacherCoursesByName(Users user, String courseName) {
        List<Courses> teacherCourses = coursesRepository.findByAuthorAndCourseNameContainingIgnoreCase(user, courseName);
        return makeAllCoursesDtoList(teacherCourses, user);
    }

    public List<AllCoursesDto> getAllCoursesForUser(Users user) {
        List<Courses> allCourses = coursesRepository.findAll();
        return makeAllCoursesDtoList(allCourses, user);
    }

    public List<AllCoursesDto> findCoursesByNameForUser(Users user, String courseName) {
        List<Courses> allCourses = coursesRepository.findByCourseNameContainingIgnoreCase(courseName);
        return makeAllCoursesDtoList(allCourses, user);
    }

    public List<AllCoursesDto> makeAllCoursesDtoList(List<Courses> courses, Users user) {

        List<AllCoursesDto> allCourseDtos = new ArrayList<>();
        for (Courses course : courses) {

            //Находим общую длительность уроков
            List<Lessons> allCourseLessons = getAllCourseLessons(course);
            int sumDuration = countLessonsDuration(allCourseLessons);

            //Находим рейтинг курса
            List<Reviews> courseReviews = reviewsRepository.findByCourse(course);
            float rating = countAverageCourseRating(courseReviews);

            Optional<CourseAccess> courseAccess = courseAccessRepository.findByUserAndCourse(user, course);

            AllCoursesDto allCoursesDto = new AllCoursesDto();
            allCoursesDto.setCourse(course);
            allCoursesDto.setDuration(sumDuration);
            allCoursesDto.setAllLessonsCount(allCourseLessons.size());
            allCoursesDto.setRating(rating);
            allCoursesDto.setReviewsCount(courseReviews.size());

            //Узнаем имеет ли пользователь доступ к курсу
            if (courseAccess.isPresent()) {
                allCoursesDto.setStatus(String.valueOf(courseAccess.get().getStatus()));
            } else {
                allCoursesDto.setStatus("NO_REQUEST");
            }
            allCourseDtos.add(allCoursesDto);

        }
        return allCourseDtos;
    }

    public List<UserInterestCoursesDto> findUserInterestCourses(Users user) {
        //Находим курсы, к которым пользователь имеет или запрашивал доступ
        List<CourseAccess> courseAccessList = courseAccessRepository.findByUser(user);
        List<Courses> courses = new ArrayList<>();
        for (CourseAccess courseAccess : courseAccessList) {
            courses.add(courseAccess.getCourse());
        }
        return makeUserInterestCoursesDtoList(courses, user);
    }

    public List<UserInterestCoursesDto> findUserInterestCoursesByName(Users user, String courseName) {
        //Находим курсы, к которым пользователь имеет или запрашивал доступ
        List<CourseAccess> courseAccessList = courseAccessRepository.findByUser(user);
        List<Courses> courses = new ArrayList<>();
        for (CourseAccess courseAccess : courseAccessList) {
            //Проверяем совпадает ли имя курса с искомым
            if (courseAccess.getCourse().getCourseName().toLowerCase().contains(courseName.toLowerCase())) {
                courses.add(courseAccess.getCourse());
            }
        }
        return makeUserInterestCoursesDtoList(courses, user);
    }

    public List<UserInterestCoursesDto> makeUserInterestCoursesDtoList(List<Courses> courses, Users user) {

        List<UserInterestCoursesDto> userInterestCoursesDtos = new ArrayList<>();
        for (Courses course : courses) {

            //Находим общую длительность уроков
            List<Lessons> allCourseLessons = getAllCourseLessons(course);
            int sumDuration = countLessonsDuration(allCourseLessons);

            //Находим рейтинг курса
            List<Reviews> courseReviews = reviewsRepository.findByCourse(course);
            float rating = countAverageCourseRating(courseReviews);

            Optional<CourseAccess> courseAccess = courseAccessRepository.findByUserAndCourse(user, course);

            UserInterestCoursesDto userInterestCoursesDto = new UserInterestCoursesDto();
            userInterestCoursesDto.setCourse(course);
            userInterestCoursesDto.setDuration(sumDuration);
            userInterestCoursesDto.setAllLessonsCount(allCourseLessons.size());
            userInterestCoursesDto.setRating(rating);

            //Кол-во оценок курса
            userInterestCoursesDto.setReviewsCount(courseReviews.size());

            userInterestCoursesDto.setStatus(courseAccess.get().getStatus());
            userInterestCoursesDto.setProgressInPercents(calculateUserProgressInPercents(user, course));
            userInterestCoursesDto.setCompletedLessonsCount(getUserFinishedLessons(user, course).size());


            userInterestCoursesDtos.add(userInterestCoursesDto);

        }
        return userInterestCoursesDtos;
    }

    public HttpStatus requestAccess(RequestAccessDto requestAccessDto) throws Exception {

        try {
            Optional<CourseAccess> possibleCourseAccess = courseAccessRepository.findByUserAndCourse(
                    usersRepository.findById(requestAccessDto.getUserId()).get(),
                    coursesRepository.findById(requestAccessDto.getCourseId()).get()
            );
            if (possibleCourseAccess.isPresent()) {

                if (possibleCourseAccess.get().getStatus() == CourseAccess.AccessStatus.REJECTED) {
                    possibleCourseAccess.get().setStatus(CourseAccess.AccessStatus.PENDING);
                    possibleCourseAccess.get().setRequestDate(new Timestamp(new Date().getTime()));
                    possibleCourseAccess.get().setGrantedDate(null);
                    courseAccessRepository.save(possibleCourseAccess.get());
                    return HttpStatus.OK;
                } else {
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

    public int countLessonsDuration(List<Lessons> lessons) {
        int sumDuration = 0;
        for (Lessons lesson : lessons) {
            if (lesson.getDuration() == null) {
                sumDuration += 0;
            } else {
                sumDuration += lesson.getDuration();
            }
        }
        return sumDuration;
    }

    public float countAverageCourseRating(List<Reviews> courseReviews) {
        Integer sumRating = 0;
        for (Reviews review : courseReviews) {
            sumRating += review.getRating();
        }

        //Вычисляем средний рейтинг
        float rating = (float) sumRating / courseReviews.size();
        if (Float.isNaN(rating)) rating = 0f;

        return rating;
    }

    public HttpStatus addNewCourse(AddNewCourseDto addNewCourseDto) {

        try {
            if (!coursesRepository.findByCourseName(addNewCourseDto.getCourseName()).isEmpty())
                return HttpStatus.ALREADY_REPORTED;

            Courses course = new Courses();
            course.setCourseName(addNewCourseDto.getCourseName());
            course.setAuthor(usersRepository.findById(addNewCourseDto.getAuthorId()).get());
            return setCourseInfo(course, addNewCourseDto.getTopic(), addNewCourseDto.getShortDescription(), addNewCourseDto.getLongDescription(), addNewCourseDto.getCourseImg(), addNewCourseDto.getSkillLevel());
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }

    }

    public HttpStatus addNewChapter(AddNewChapterDto addNewChapterDto) {
        try {
            if (!chaptersRepository.findByChapterTitle(addNewChapterDto.getChapterTitle()).isEmpty())
                return HttpStatus.ALREADY_REPORTED;

            Courses course = coursesRepository.findById(addNewChapterDto.getCourseId()).get();

            Chapters chapter = new Chapters();
            chapter.setCourse(course);
            chapter.setChapterTitle(addNewChapterDto.getChapterTitle());
            chapter.setChapterOrder(findLastChapterOrder(getAllCourseChapters(course)) + 1);
            chaptersRepository.save(chapter);

            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }

    }

    public HttpStatus addNewVideoLesson(AddNewVideoLessonDto addNewVideoLessonDto) {
        try {
            Chapters chapter = chaptersRepository.findById(addNewVideoLessonDto.getChapterId()).get();

            Lessons lesson = new Lessons();
            lesson.setLessonTitle(addNewVideoLessonDto.getLessonTitle());
            lesson.setLessonType(addNewVideoLessonDto.getLessonType());
            lesson.setLessonTitle(addNewVideoLessonDto.getLessonTitle());
            lesson.setDuration(addNewVideoLessonDto.getDuration());
            lesson.setDiamondReward((short) addNewVideoLessonDto.getDiamondReward());
            lesson.setChapter(chapter);
            lesson.setLessonOrder(findLastLessonOrder(lessonsRepository.findByChapter(chapter)) + 1);
            lessonsRepository.save(lesson);

            VideoLessons videoLesson = new VideoLessons();
            videoLesson.setLesson(lesson);
            videoLesson.setVideoUrl(addNewVideoLessonDto.getVideoUrl());
            videoLessonsRepository.save(videoLesson);

            List<Resources> resources = new ArrayList<>();
            for (ResourcesDto resourcesDto : addNewVideoLessonDto.getResources()) {
                Resources resource = new Resources();
                resource.setLesson(lesson);
                resource.setResourceLink(resourcesDto.getLink());
                resource.setResourceTitle(resourcesDto.getTitle());
                resources.add(resource);
            }
            resourcesRepository.saveAll(resources);

            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }

    }

    public HttpStatus addNewTextLesson(AddNewTextLessonDto addNewTextLessonDto) {
        try {
            Chapters chapter = chaptersRepository.findById(addNewTextLessonDto.getChapterId()).get();

            Lessons lesson = new Lessons();
            lesson.setLessonTitle(addNewTextLessonDto.getLessonTitle());
            lesson.setLessonType(addNewTextLessonDto.getLessonType());
            lesson.setLessonTitle(addNewTextLessonDto.getLessonTitle());
            lesson.setDuration(addNewTextLessonDto.getDuration());
            lesson.setDiamondReward((short) addNewTextLessonDto.getDiamondReward());
            lesson.setChapter(chapter);
            lesson.setLessonOrder(findLastLessonOrder(lessonsRepository.findByChapter(chapter)) + 1);
            lessonsRepository.save(lesson);

            TextLessons textLessons = new TextLessons();
            textLessons.setLesson(lesson);
            textLessons.setLessonBody(addNewTextLessonDto.getHtml());
            textLessonsRepository.save(textLessons);

            List<Resources> resources = new ArrayList<>();
            for (ResourcesDto resourcesDto : addNewTextLessonDto.getResources()) {
                Resources resource = new Resources();
                resource.setLesson(lesson);
                resource.setResourceLink(resourcesDto.getLink());
                resource.setResourceTitle(resourcesDto.getTitle());
                resources.add(resource);
            }
            resourcesRepository.saveAll(resources);

            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    public HttpStatus addTestLesson(AddTestLessonDto addTestLessonDto) {
        try {
            Chapters chapter = chaptersRepository.findById(addTestLessonDto.getChapterId()).get();

            Lessons lesson = new Lessons();
            lesson.setLessonTitle(addTestLessonDto.getLessonTitle());
            lesson.setLessonType(addTestLessonDto.getLessonType());
            lesson.setLessonTitle(addTestLessonDto.getLessonTitle());
            lesson.setDuration(null);
            lesson.setDiamondReward((short) addTestLessonDto.getDiamondReward());
            lesson.setChapter(chapter);
            lesson.setLessonOrder(findLastLessonOrder(lessonsRepository.findByChapter(chapter)) + 1);
            lessonsRepository.save(lesson);

            Tests test = new Tests();
            test.setLesson(lesson);
            testsRepository.save(test);

            for (AddTestQuestionDto addTestQuestionDto : addTestLessonDto.getQuestions()) {

                TestQuestions testQuestion = new TestQuestions();
                testQuestion.setTest(test);
                testQuestion.setQuestionText(addTestQuestionDto.getQuestionText());
                testQuestion.setCorrectAnswer(null);
                testQuestionsRepository.save(testQuestion);

                for (AddTestAnswerDto addTestAnswerDto : addTestQuestionDto.getAnswers()) {
                    TestAnswers testAnswer = new TestAnswers();
                    testAnswer.setTestQuestion(testQuestion);
                    testAnswer.setAnswer(addTestAnswerDto.getAnswerText());

                    if (addTestQuestionDto.getCorrectAnswerId().equals(addTestAnswerDto.getAnswerId())) {
                        testQuestion.setCorrectAnswer(testAnswer);
                        testQuestion.setAnswerDescription(addTestAnswerDto.getAnswerText());
                    }
                    testAnswersRepository.save(testAnswer);
                }
                testQuestionsRepository.save(testQuestion);

            }

            List<Resources> resources = new ArrayList<>();
            for (ResourcesDto resourcesDto : addTestLessonDto.getResources()) {
                Resources resource = new Resources();
                resource.setLesson(lesson);
                resource.setResourceLink(resourcesDto.getLink());
                resource.setResourceTitle(resourcesDto.getTitle());
                resources.add(resource);
            }
            resourcesRepository.saveAll(resources);

            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    public HttpStatus editTestLesson(EditTestLessonDto editTestLessonDto) {
        try {
            Chapters chapter = chaptersRepository.findById(editTestLessonDto.getChapterId()).get();

            Lessons lesson = lessonsRepository.findById(editTestLessonDto.getLessonId()).get();
            lesson.setLessonTitle(editTestLessonDto.getLessonTitle());
            lesson.setLessonType(editTestLessonDto.getLessonType());
            lesson.setLessonTitle(editTestLessonDto.getLessonTitle());
            lesson.setDiamondReward((short) editTestLessonDto.getDiamondReward());
            lesson.setChapter(chapter);
            lessonsRepository.save(lesson);

            Tests test = testsRepository.findByLesson(lesson).get();
            test.setLesson(lesson);
            testsRepository.save(test);

            List<TestQuestions> testQuestions = testQuestionsRepository.findByTest(test);
            for (TestQuestions testQuestion : testQuestions) {
                testQuestion.setCorrectAnswer(null);

                List<TestAnswers> testAnswers = testAnswersRepository.findByTestQuestion(testQuestion);
                testAnswersRepository.deleteAll(testAnswers);
                testQuestionsRepository.delete(testQuestion);
            }

            for (AddTestQuestionDto addTestQuestionDto : editTestLessonDto.getQuestions()) {

                TestQuestions testQuestion = new TestQuestions();
                testQuestion.setTest(test);
                testQuestion.setQuestionText(addTestQuestionDto.getQuestionText());
                testQuestion.setCorrectAnswer(null);
                testQuestionsRepository.save(testQuestion);

                for (AddTestAnswerDto addTestAnswerDto : addTestQuestionDto.getAnswers()) {
                    TestAnswers testAnswer = new TestAnswers();
                    testAnswer.setTestQuestion(testQuestion);
                    testAnswer.setAnswer(addTestAnswerDto.getAnswerText());

                    if (addTestQuestionDto.getCorrectAnswerId().equals(addTestAnswerDto.getAnswerId())) {
                        testQuestion.setCorrectAnswer(testAnswer);
                        testQuestion.setAnswerDescription(addTestAnswerDto.getAnswerText());
                    }
                    testAnswersRepository.save(testAnswer);
                }
                testQuestionsRepository.save(testQuestion);

            }

            resourcesRepository.deleteAll(resourcesRepository.findByLesson(lesson));
            List<Resources> resources = new ArrayList<>();
            for (ResourcesDto resourcesDto : editTestLessonDto.getResources()) {
                Resources resource = new Resources();
                resource.setLesson(lesson);
                resource.setResourceLink(resourcesDto.getLink());
                resource.setResourceTitle(resourcesDto.getTitle());
                resources.add(resource);
            }
            resourcesRepository.saveAll(resources);

            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    public int findLastChapterOrder(List<ChapterDto> chapters) {
        int lastOrder = 0;
        for (ChapterDto chapter : chapters) {
            if (chapter.getChapterOrder() > lastOrder) {
                lastOrder = chapter.getChapterOrder();
            }
        }
        return lastOrder;
    }

    public int findLastLessonOrder(List<Lessons> lessons) {
        int lastOrder = 0;
        for (Lessons lesson : lessons) {
            if (lesson.getLessonOrder() > lastOrder) {
                lastOrder = lesson.getLessonOrder();
            }
        }
        return lastOrder;
    }

    public HttpStatus editCourse(EditCourseDto editCourseDto) {
        try {
            if (!coursesRepository.findByCourseName(editCourseDto.getCourseName()).isEmpty()
                    && !coursesRepository.findById(editCourseDto.getCourseId()).get().getCourseName().equals(editCourseDto.getCourseName()))
                return HttpStatus.ALREADY_REPORTED;

            Courses course = coursesRepository.findById(editCourseDto.getCourseId()).get();
            course.setCourseName(editCourseDto.getCourseName());
            return setCourseInfo(course, editCourseDto.getTopic(), editCourseDto.getShortDescription(), editCourseDto.getLongDescription(), editCourseDto.getCourseImg(), editCourseDto.getSkillLevel());
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }

    }

    public HttpStatus editVideoLesson(EditVideoLessonDto editVideoLessonDto) {
        try {

            Lessons lesson = lessonsRepository.findById(editVideoLessonDto.getLessonId()).get();
            lesson.setLessonTitle(editVideoLessonDto.getLessonTitle());
            lesson.setLessonType(editVideoLessonDto.getLessonType());
            lesson.setDiamondReward((short) editVideoLessonDto.getDiamondReward());
            lesson.setDuration(editVideoLessonDto.getDuration());
            lesson.setChapter(chaptersRepository.findById(editVideoLessonDto.getChapterId()).get());
            lessonsRepository.save(lesson);

            VideoLessons videoLesson = videoLessonsRepository.findByLesson(lesson).get();
            videoLesson.setVideoUrl(editVideoLessonDto.getVideoUrl());
            videoLessonsRepository.save(videoLesson);

            resourcesRepository.deleteAll(resourcesRepository.findByLesson(lesson));
            List<Resources> resources = new ArrayList<>();
            for (ResourcesDto resourcesDto : editVideoLessonDto.getResources()) {
                Resources resource = new Resources();
                resource.setLesson(lesson);
                resource.setResourceLink(resourcesDto.getLink());
                resource.setResourceTitle(resourcesDto.getTitle());
                resources.add(resource);
            }
            resourcesRepository.saveAll(resources);

            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }

    }

    public HttpStatus editTextLesson(EditTextLessonDto editTextLessonDto) {
        try {

            Lessons lesson = lessonsRepository.findById(editTextLessonDto.getLessonId()).get();
            lesson.setLessonTitle(editTextLessonDto.getLessonTitle());
            lesson.setLessonType(editTextLessonDto.getLessonType());
            lesson.setDiamondReward((short) editTextLessonDto.getDiamondReward());
            lesson.setDuration(editTextLessonDto.getDuration());
            lesson.setChapter(chaptersRepository.findById(editTextLessonDto.getChapterId()).get());
            lessonsRepository.save(lesson);

            TextLessons textLessons = textLessonsRepository.findByLesson(lesson).get();
            textLessons.setLesson(lesson);
            textLessons.setLessonBody(editTextLessonDto.getHtml());
            textLessonsRepository.save(textLessons);

            resourcesRepository.deleteAll(resourcesRepository.findByLesson(lesson));
            List<Resources> resources = new ArrayList<>();
            for (ResourcesDto resourcesDto : editTextLessonDto.getResources()) {
                Resources resource = new Resources();
                resource.setLesson(lesson);
                resource.setResourceLink(resourcesDto.getLink());
                resource.setResourceTitle(resourcesDto.getTitle());
                resources.add(resource);
            }
            resourcesRepository.saveAll(resources);

            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    private HttpStatus setCourseInfo(Courses course, String topic, String shortDescription, String longDescription, String courseImg, Courses.SkillLevel skillLevel) {
        course.setTopic(topic);
        course.setShortDescription(shortDescription);
        course.setLongDescription(longDescription);
        course.setCourseImg(courseImg);
        course.setSkillLevel(skillLevel);
        course.setLastUpdate(new Timestamp(new Date().getTime()));
        coursesRepository.save(course);
        return HttpStatus.OK;
    }

    public HttpStatus deleteCourse(Long courseId) {
        try {
            // Получаем курс, если он существует
            Courses course = coursesRepository.findById(courseId).orElse(null);
            if (course == null) {
                return HttpStatus.NOT_FOUND; // Возвращаем 404, если курс не найден
            }

            // Получаем связанные главы
            List<Chapters> chapters = chaptersRepository.findByCourse(course);
            for (Chapters chapter : chapters) {
                // Получаем уроки по главе
                List<Lessons> lessons = lessonsRepository.findByChapter(chapter);

                for (Lessons lesson : lessons) {
                    // Удаляем ресурсы урока
                    resourcesRepository.deleteAll(resourcesRepository.findByLesson(lesson));

                    // Удаляем текстовые уроки, если они существуют
                    textLessonsRepository.findByLesson(lesson)
                            .ifPresent(textLessonsRepository::delete);

                    // Удаляем видеоролики урока, если они существуют
                    videoLessonsRepository.findByLesson(lesson)
                            .ifPresent(videoLessonsRepository::delete);

                    // Получаем тесты по уроку
                    Tests tests = testsRepository.findByLesson(lesson).orElse(null);
                    if (tests != null) {
                        // Получаем вопросы по тесту
                        List<TestQuestions> testQuestions = testQuestionsRepository.findByTest(tests);
                        for (TestQuestions testQuestion : testQuestions) {
                            testQuestion.setCorrectAnswer(null);
                            testQuestionsRepository.save(testQuestion);

                            // Удаляем ответы на вопросы
                            List<TestAnswers> testAnswers = testAnswersRepository.findByTestQuestion(testQuestion);
                            testAnswersRepository.deleteAll(testAnswers);
                        }

                        // Удаляем все тестовые вопросы и тесты
                        testQuestionsRepository.deleteAll(testQuestions);
                        testsRepository.delete(tests);
                    }

                    // Удаляем заметки пользователей по уроку
                    userNotesRepository.deleteAll(userNotesRepository.findByLesson(lesson));

                    // Удаляем вопросы урока
                    List<Questions> questions = questionsRepository.findByLesson(lesson);
                    for (Questions question : questions) {
                        answersRepository.deleteAll(answersRepository.findByQuestion(question));
                    }
                    questionsRepository.deleteAll(questions);
                }
                // Удаляем все уроки в главе
                lessonsRepository.deleteAll(lessons);
            }

            // Удаляем все главы
            chaptersRepository.deleteAll(chapters);
            // Удаляем отзывы, доступы и прогресс пользователей по курсу
            reviewsRepository.deleteAll(reviewsRepository.findByCourse(course));
            courseAccessRepository.deleteAll(courseAccessRepository.findByCourse(course));
            userProgressRepository.deleteAll(userProgressRepository.findByCourse(course));

            // Удаляем курс
            coursesRepository.deleteById(courseId);

            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    public HttpStatus deleteLesson(Long lessonId) {
        try {

            Lessons lesson = lessonsRepository.findById(lessonId).get();

            // Удаляем ресурсы урока
            resourcesRepository.deleteAll(resourcesRepository.findByLesson(lesson));

            userProgressRepository.deleteAll(userProgressRepository.findByLesson(lesson));

            // Удаляем текстовые уроки, если они существуют
            textLessonsRepository.findByLesson(lesson)
                    .ifPresent(textLessonsRepository::delete);

            // Удаляем видеоролики урока, если они существуют
            videoLessonsRepository.findByLesson(lesson)
                    .ifPresent(videoLessonsRepository::delete);

            // Получаем тесты по уроку
            Tests tests = testsRepository.findByLesson(lesson).orElse(null);
            if (tests != null) {
                // Получаем вопросы по тесту
                List<TestQuestions> testQuestions = testQuestionsRepository.findByTest(tests);
                for (TestQuestions testQuestion : testQuestions) {
                    testQuestion.setCorrectAnswer(null);
                    testQuestionsRepository.save(testQuestion);

                    // Удаляем ответы на вопросы
                    List<TestAnswers> testAnswers = testAnswersRepository.findByTestQuestion(testQuestion);
                    testAnswersRepository.deleteAll(testAnswers);
                }

                // Удаляем все тестовые вопросы и тесты
                testQuestionsRepository.deleteAll(testQuestions);
                testsRepository.delete(tests);
            }

            // Удаляем заметки пользователей по уроку
            userNotesRepository.deleteAll(userNotesRepository.findByLesson(lesson));

            // Удаляем вопросы урока
            List<Questions> questions = questionsRepository.findByLesson(lesson);
            for (Questions question : questions) {
                answersRepository.deleteAll(answersRepository.findByQuestion(question));
            }
            questionsRepository.deleteAll(questions);

            lessonsRepository.delete(lesson);

            return HttpStatus.OK;


        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    public HttpStatus changeCourseCertificate(Long courseId, String certificatePath) {
        try{
            Courses course = coursesRepository.findById(courseId).orElse(null);
            assert course != null;
            course.setCertificate(certificatePath);
            coursesRepository.save(course);
            return HttpStatus.OK;
        }catch (Exception e){
            return HttpStatus.BAD_REQUEST;
        }
    }

    public HttpStatus deleteCourseCertificate(Long courseId) {
        try{
            Courses course = coursesRepository.findById(courseId).orElse(null);
            assert course != null;
            course.setCertificate(null);
            coursesRepository.save(course);
            return HttpStatus.OK;
        }catch (Exception e){
            return HttpStatus.BAD_REQUEST;
        }
    }

    public CourseInfoDto getCourseInfo(Courses course) {

        List<Lessons> lessons = getAllCourseLessons(course);
        List<LessonWithResourcesDto> lessonWithResourcesDtoList = new ArrayList<>();

        //находим информацию о курсе
        int duration = countLessonsDuration(lessons);
        int allLessonsCount = lessons.size();
        //Находим рейтинг курса
        List<Reviews> courseReviews = reviewsRepository.findByCourse(course);
        float rating = countAverageCourseRating(courseReviews);
        int reviewsCount = courseReviews.size();
        // Находим кол-во студентов
        List<CourseAccess> courseAccessList = courseAccessRepository.findByCourse(course);
        int studentsCount = courseAccessList.size();
        //Кол-во тестов
        int testsCount = 0;
        for (Lessons lesson : lessons) {
            //Подсчитываем кол-во тестов
            if (lesson.getLessonType().equals(Lessons.LessonType.TEST)) testsCount++;

            //Находим ресурсы для урока
            List<Resources> lessonResources = resourcesRepository.findByLesson(lesson);
            List<ResourceDto> resourceDtoList = new ArrayList<>();
            for (Resources resource : lessonResources) {
                ResourceDto resourceDto = new ResourceDto();
                resourceDto.setResourceId(resource.getResourceId());
                resourceDto.setLessonId(resource.getLesson().getLessonId());
                resourceDto.setResourceTitle(resource.getResourceTitle());
                resourceDto.setResourceLink(resource.getResourceLink());
                resourceDtoList.add(resourceDto);
            }

            //Заполняем список уроков
            LessonWithResourcesDto lessonWithResourcesDto = makeLessonWithResourcesDto(lesson, resourceDtoList);
            lessonWithResourcesDtoList.add(lessonWithResourcesDto);
        }

        //Заполняем информацию о курсе
        InfoDto infoDto = new InfoDto();
        infoDto.setDuration(duration);
        infoDto.setAllLessonsCount(allLessonsCount);
        infoDto.setRating(rating);
        infoDto.setReviewsCount(reviewsCount);
        infoDto.setStudentsCount(studentsCount);
        infoDto.setTestsCount(testsCount);

        //Заполняем список глав
        List<Chapters> chapters = chaptersRepository.findByCourse(course);
        List<ChapterDto> chapterDtoList = new ArrayList<>();
        for (Chapters chapter : chapters) {
            //Заполняем базовой информацией
            ChapterDto chapterDto = makeChapterDto(chapter);
            //Находим кол-во уроков в главе
            List<Lessons> chapterLessons = getAllChapterLessons(chapter);
            chapterDto.setLessonsCount(chapterLessons.size());
            //Находим общую длительность главы
            chapterDto.setDuration(countLessonsDuration(chapterLessons));
            chapterDtoList.add(chapterDto);
        }

        //Заполняем Dto
        CourseInfoDto courseInfoDto = new CourseInfoDto();
        courseInfoDto.setCourse(makeCourseDto(course));
        courseInfoDto.setInfo(infoDto);
        courseInfoDto.setChapters(chapterDtoList);
        courseInfoDto.setLessons(lessonWithResourcesDtoList);

        return courseInfoDto;
    }

    public CourseDto makeCourseDto(Courses course) {
        CourseDto courseDto = new CourseDto();

        courseDto.setCourseId(course.getCourseId());
        courseDto.setCourseImg(course.getCourseImg());
        courseDto.setCourseName(course.getCourseName());
        courseDto.setAuthor(course.getAuthor());
        courseDto.setLastUpdate(course.getLastUpdate());
        courseDto.setTopic(course.getTopic());
        courseDto.setCertificate(course.getCertificate());
        courseDto.setSkillLevel(course.getSkillLevel());
        courseDto.setShortDescription(course.getShortDescription());
        courseDto.setLongDescription(course.getLongDescription());

        return courseDto;
    }

    public ChapterDto makeChapterDto(Chapters chapter) {
        ChapterDto chapterDto = new ChapterDto();
        chapterDto.setChapterId(chapter.getChapterId());
        chapterDto.setChapterTitle(chapter.getChapterTitle());
        chapterDto.setChapterOrder(chapter.getChapterOrder());
        chapterDto.setCourseId(chapter.getCourse().getCourseId());
        return chapterDto;
    }

    public LessonWithResourcesDto makeLessonWithResourcesDto(Lessons lessons, List<ResourceDto> resources) {
        LessonWithResourcesDto lessonWithResourcesDto = new LessonWithResourcesDto();
        lessonWithResourcesDto.setLessonId(lessons.getLessonId());
        lessonWithResourcesDto.setChapterId(lessons.getChapter().getChapterId());
        lessonWithResourcesDto.setLessonOrder(lessons.getLessonOrder());
        lessonWithResourcesDto.setLessonTitle(lessons.getLessonTitle());
        lessonWithResourcesDto.setLessonType(lessons.getLessonType());
        //Проверяем длительность урока
        lessonWithResourcesDto.setDuration(lessons.getDuration() == null ? null : lessons.getDuration());
        lessonWithResourcesDto.setDiamondReward(lessons.getDiamondReward());
        lessonWithResourcesDto.setResources(resources);

        return lessonWithResourcesDto;
    }

    public CourseLessonDto getCourseLessonById(Long lessonId) throws Exception {
        try {
            Optional<Lessons> lesson = lessonsRepository.findById(lessonId);
            switch (lesson.get().getLessonType()) {
                case VIDEO -> {
                    Optional<VideoLessons> videoLessons = videoLessonsRepository.findByLesson(lesson.get());

                    return getVideoLessonDto(videoLessons, lesson);
                }
                case TEST -> {
                    Optional<Tests> test = testsRepository.findByLesson(lesson.get());

                    TestLessonDto testLessonDto = new TestLessonDto();
                    testLessonDto.setLessonId(lesson.get().getLessonId());
                    testLessonDto.setTestId(test.get().getTestId());
                    testLessonDto.setTestDescription(test.get().getTestDescription());

                    //Заполняем вопросы для теста
                    List<TestQuestionDto> testQuestionDtoList = getTestQuestionsByTest(test.get());
                    testLessonDto.setTestQuestions(testQuestionDtoList);
                    testLessonDto.setName(lesson.get().getLessonTitle());
                    //Заполняем ответы для теста
                    List<TestAnswerDto> testAnswerDtoList = new ArrayList<>();
                    for (TestQuestionDto testQuestionDto : testQuestionDtoList) {
                        //Ищем ответы для каждого вопроса и добавляем к общим ответам
                        testAnswerDtoList.addAll(getTestAnswersByTestQuestion(testQuestionsRepository.findById(testQuestionDto.getQuestionId()).get()));
                    }
                    testLessonDto.setTestAnswers(testAnswerDtoList);

                    CourseLessonDto courseLessonDto = new CourseLessonDto();
                    courseLessonDto.setLessonId(lesson.get().getLessonId());
                    courseLessonDto.setLessonType(lesson.get().getLessonType());
                    courseLessonDto.setTestLesson(testLessonDto);
                    courseLessonDto.setLessonTitle(lesson.get().getLessonTitle());

                    return courseLessonDto;
                }
                case TEXT -> {
                    Optional<TextLessons> textLessons = textLessonsRepository.findByLesson(lesson.get());

                    return getTestLessonDto(lesson, textLessons);
                }

            }
        } catch (Exception e) {
            throw new Exception("Can't add access request", e);
        }
        return null;
    }

    private List<TestQuestionDto> getTestQuestionsByTest(Tests test) {
        List<TestQuestions> testQuestions = testQuestionsRepository.findByTest(test);
        List<TestQuestionDto> testQuestionDtoList = new ArrayList<>();
        for (TestQuestions testQuestion : testQuestions) {
            TestQuestionDto testQuestionDto = new TestQuestionDto();
            testQuestionDto.setQuestionId(testQuestion.getQuestionId());
            testQuestionDto.setTestId(test.getTestId());
            testQuestionDto.setQuestionText(testQuestion.getQuestionText());
            testQuestionDto.setAnswerDescription(testQuestion.getAnswerDescription());
            testQuestionDto.setCorrectAnswerId(testQuestion.getCorrectAnswer().getTestAnswerId());
            testQuestionDtoList.add(testQuestionDto);
        }
        return testQuestionDtoList;
    }

    private List<TestAnswerDto> getTestAnswersByTestQuestion(TestQuestions testQuestions) {
        List<TestAnswers> testAnswers = testAnswersRepository.findByTestQuestion(testQuestions);
        List<TestAnswerDto> testAnswerDtoList = new ArrayList<>();
        for (TestAnswers testAnswer : testAnswers) {
            TestAnswerDto testAnswerDto = new TestAnswerDto();
            testAnswerDto.setTestAnswerId(testAnswer.getTestAnswerId());
            testAnswerDto.setTestQuestionId(testAnswer.getTestQuestion().getQuestionId());
            testAnswerDto.setAnswer(testAnswer.getAnswer());
            testAnswerDtoList.add(testAnswerDto);
        }
        return testAnswerDtoList;
    }

    private static CourseLessonDto getTestLessonDto(Optional<Lessons> lesson, Optional<TextLessons> textLessons) {
        TextLessonDto textLessonDto = new TextLessonDto();
        textLessonDto.setLessonId(lesson.get().getLessonId());
        textLessonDto.setTextLessonId(textLessons.get().getTextLessonId());
        textLessonDto.setLessonBody(textLessons.get().getLessonBody());
        textLessonDto.setTitle(lesson.get().getLessonTitle());

        CourseLessonDto courseLessonDto = new CourseLessonDto();
        courseLessonDto.setLessonId(lesson.get().getLessonId());
        courseLessonDto.setLessonType(lesson.get().getLessonType());
        courseLessonDto.setTextLesson(textLessonDto);
        courseLessonDto.setLessonTitle(lesson.get().getLessonTitle());
        return courseLessonDto;
    }

    private static CourseLessonDto getVideoLessonDto(Optional<VideoLessons> videoLessons, Optional<Lessons> lesson) {
        VideoLessonDto videoLessonDto = new VideoLessonDto();
        videoLessonDto.setLessonId(videoLessons.get().getLesson().getLessonId());
        videoLessonDto.setVideoLessonId(videoLessons.get().getVideoLessonId());
        videoLessonDto.setVideoUrl(videoLessons.get().getVideoUrl());

        CourseLessonDto courseLessonDto = new CourseLessonDto();
        courseLessonDto.setLessonId(lesson.get().getLessonId());
        courseLessonDto.setLessonType(lesson.get().getLessonType());
        courseLessonDto.setVideoLesson(videoLessonDto);
        courseLessonDto.setLessonTitle(lesson.get().getLessonTitle());
        return courseLessonDto;
    }


}
