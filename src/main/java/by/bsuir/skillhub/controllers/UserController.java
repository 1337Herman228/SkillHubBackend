package by.bsuir.skillhub.controllers;

import by.bsuir.skillhub.dto.*;
import by.bsuir.skillhub.entity.AvatarStrokes;
import by.bsuir.skillhub.entity.Dignities;
import by.bsuir.skillhub.entity.NicknameColors;
import by.bsuir.skillhub.repo.*;
import by.bsuir.skillhub.entity.UserProgress;
import by.bsuir.skillhub.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
@PreAuthorize("hasAnyAuthority('user','admin','teacher')")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CoursesService coursesService;
    private final QuestionsService questionsService;
    private final NotesService notesService;
    private final ReviewsService reviewsService;
    private final UsersRepository usersRepository;
    private final UserProgressRepository userProgressRepository;
    private final CoursesRepository coursesRepository;
    private final LessonsRepository lessonsRepository;
    private final AvatarStrokesRepository avatarStrokesRepository;
    private final DignitiesRepository dignitiesRepository;
    private final NicknameColorsRepository nicknameColorsRepository;

    //Находим пользователя по ID
    @GetMapping("/get-user/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws Exception {
        return userService.getUser(userId);
    }

    @GetMapping("/get-user-progress/{userId}")
    public List<UserProgress> getUserProgress(@PathVariable Long userId) throws Exception {
        return userProgressRepository.findByUser(usersRepository.findById(userId).get());
    }

    @GetMapping("/get-continue-courses/{userId}")
    public List<ContinueCourseDto> getContinueCourses(@PathVariable Long userId) throws Exception {
        return coursesService.getContinueCourses(usersRepository.findById(userId).get());
    }

    @GetMapping("/get-all-courses-for-user/{userId}")
    public List<AllCoursesDto> getAllCoursesForUser(@PathVariable Long userId) throws Exception {
        return coursesService.getAllCoursesForUser(usersRepository.findById(userId).get());
    }

    @PostMapping("/find-courses-by-name-for-user")
    public List<AllCoursesDto> findCoursesByNameForUser(@RequestBody FindCourseByNameForUserDto requestBody) throws Exception {
        return coursesService.findCoursesByNameForUser(
                usersRepository.findById(requestBody.getUserId()).get(),
                requestBody.getCourseName());
    }

    @PostMapping("/request-access")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus requestAccess(@RequestBody RequestAccessDto requestBody) throws Exception {
        return coursesService.requestAccess(requestBody);
    }

    @PutMapping("/edit-profile-info")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus editProfileInfo(@RequestBody EditPersonDto requestBody) throws Exception {
        return userService.editUserInfo(requestBody);
    }

    @PutMapping("/edit-profile-photo")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus editProfilePhoto(@RequestBody EditPhotoDto requestBody) throws Exception {
        return userService.editUserPhoto(requestBody);
    }

    @PostMapping("/add-teacher-request")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus addTeacherRequest(@RequestBody AddTeacherRequest requestBody) {
        return userService.addBecomeTeacherRecord(requestBody);
    }

    @PutMapping("/change-user-password")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus changeUserPassword(@RequestBody ChangeUserPasswordDto requestBody) {
        return userService.changeUserPassword(requestBody);
    }

    @GetMapping("/get-user-interest-courses/{userId}")
    public List<UserInterestCoursesDto> getUserInterestCourses(@PathVariable Long userId) {
        return coursesService.findUserInterestCourses(usersRepository.findById(userId).get());
    }

    @GetMapping("/get-user-progress/{userId}/{courseId}")
    public UserProgressDto getUserProgress(@PathVariable Long userId, @PathVariable Long courseId) {
        return coursesService.getUserProgress(userId, courseId);
    }

    @GetMapping("/is-lesson-passed/{userId}/{lessonId}")
    public boolean isLessonPassed(@PathVariable Long userId, @PathVariable Long lessonId) {
        return coursesService.isLessonPassed(userId, lessonId);
    }

    @PostMapping("/mark-lesson-as-passed")
    public HttpStatus markLessonAsPassed(@RequestBody MarkLessonAsPassedDto markLessonAsPassedDto) {
        return coursesService.markLessonAsPassed(
                markLessonAsPassedDto.getUserId(),
                markLessonAsPassedDto.getCourseId(),
                markLessonAsPassedDto.getLessonId());
    }

    @DeleteMapping("/mark-lesson-as-unpassed/{userId}/{lessonId}")
    public HttpStatus markLessonAsUnpassed(@PathVariable Long userId, @PathVariable Long lessonId) {
        return coursesService.markLessonAsUnpassed(userId, lessonId);
    }

    @PostMapping("/find-user-interest-courses-by-name")
    public List<UserInterestCoursesDto> findUserInterestCoursesByName(@RequestBody FindCourseByNameForUserDto requestBody) throws Exception {
        return coursesService.findUserInterestCoursesByName(
                usersRepository.findById(requestBody.getUserId()).get(),
                requestBody.getCourseName());
    }

    @GetMapping("/get-course-info/{courseId}")
    public CourseInfoDto getCourseInfo(@PathVariable Long courseId) {
        return coursesService.getCourseInfo(coursesRepository.findById(courseId).get());
    }

    @GetMapping("/get-course-lesson/{lessonId}")
    public CourseLessonDto getLessonById(@PathVariable Long lessonId) throws Exception {
        return coursesService.getCourseLessonById(lessonId);
    }

    @GetMapping("/get-all-course-chapters/{courseId}")
    public List<ChapterDto> getAllCourseChapters(@PathVariable Long courseId) {
        return coursesService.getAllCourseChapters(coursesRepository.findById(courseId).get());
    }

    @GetMapping("/get-questions-for-lesson/{lessonId}")
    public List<QuestionDto> getQuestionsForLesson(@PathVariable Long lessonId) {
        return questionsService.getQuestionsForLesson(lessonsRepository.findById(lessonId).get());
    }

    @PostMapping("/add-answer")
    public HttpStatus addAnswer(@RequestBody AddAnswerDto answer) {
        return questionsService.addAnswer(answer);
    }

    @PostMapping("/add-question")
    public HttpStatus addQuestionToLesson(@RequestBody AddQuestionDto question) {
        return questionsService.addQuestion(question);
    }

    @GetMapping("/get-user-note/{userId}/{lessonId}")
    public NoteDto getUserNote(@PathVariable Long userId, @PathVariable Long lessonId) {
        return notesService.getNoteByLessonAndUser(lessonsRepository.findById(lessonId).get(), usersRepository.findById(userId).get());
    }

    @PostMapping("/save-user-note")
    public HttpStatus saveUserNote(@RequestBody NoteDto noteDto) {
        return notesService.saveNote(noteDto);
    }

    @GetMapping("/get-course-rating-info/{courseId}")
    public CourseRatingInfoDto getCourseRatingInfo(@PathVariable Long courseId) {
        return reviewsService.getCourseRatingInfo(coursesRepository.findById(courseId).get());
    }

    @GetMapping("/get-course-reviews/{courseId}")
    public List<ReviewDto> getCourseReviews(@PathVariable Long courseId) {
        return reviewsService.getCourseReviews(coursesRepository.findById(courseId).get());
    }

    @GetMapping("/get-review-by-course-and-user/{courseId}/{userId}")
    public ReviewDto getCourseReviews(@PathVariable Long courseId, @PathVariable Long userId) {
        return reviewsService.getReviewByCourseAndUser(coursesRepository.findById(courseId).get(), usersRepository.findById(userId).get());
    }

    @PostMapping("/save-user-review")
    public HttpStatus saveUserReview(@RequestBody AddReviewDto reviewDto) {
        return reviewsService.saveReview(reviewDto);
    }

    @GetMapping("/get-user-purchased-avatar-strokes/{userId}")
    public List<AvatarStrokes> getUserPurchasedAvatarStrokes(@PathVariable Long userId) {
        return userService.getPurchasedStrokesForUser(userId);
    }

    @PutMapping("/change-user-avatar-stroke")
    public HttpStatus changeUserAvatarStroke(@RequestBody ChangeAvatarStrokeDto avatarStrokeDto) {
        return userService.changeAvatarStroke(avatarStrokeDto.getUserId(), avatarStrokeDto.getAvatarStrokeId());
    }

    @PutMapping("/remove-user-avatar-stroke/{userId}")
    public HttpStatus removeUserAvatarStroke(@PathVariable Long userId) {
        return userService.removeAvatarStroke(userId);
    }

    @GetMapping("/get-avatar-strokes-catalog")
    public List<AvatarStrokes> getAvatarStrokesCatalog() {
        return avatarStrokesRepository.findAll();
    }

    @GetMapping("/get-dignities-catalog")
    public List<Dignities> getDignitiesCatalog() {
        return dignitiesRepository.findAll();
    }

    @GetMapping("/get-nickname-colors-catalog")
    public List<NicknameColors> getNicknameColorsCatalog() {
        return nicknameColorsRepository.findAll();
    }

    @PostMapping("/buy-avatar-stroke")
    public HttpStatus buyAvatarStroke(@RequestBody ChangeAvatarStrokeDto avatarStrokeDto) {
        return userService.buyAvatarStroke(avatarStrokeDto.getUserId(),avatarStrokeDto.getAvatarStrokeId());
    }

    @PostMapping("/buy-dignity")
    public HttpStatus buyDignity(@RequestBody ChangeDignityDto changeDignityDto) {
        return userService.buyDignity(changeDignityDto.getUserId(),changeDignityDto.getDignityId());
    }

    @PostMapping("/buy-nickname-color")
    public HttpStatus buyNicknameColor(@RequestBody ChangeNicknameColorDto nicknameColorDto) {
        return userService.buyNicknameColor(nicknameColorDto.getUserId(),nicknameColorDto.getNicknameColorId());
    }

    @PutMapping("/change-user-dignity")
    public HttpStatus changeUserDignity(@RequestBody ChangeDignityDto dignityDto) {
        return userService.changeDignity(dignityDto.getUserId(), dignityDto.getDignityId());
    }

    @PutMapping("/remove-user-dignity/{userId}")
    public HttpStatus removeUserDignity(@PathVariable Long userId) {
        return userService.removeDignity(userId);
    }

    @PutMapping("/change-user-nickname-color")
    public HttpStatus changeUserNicknameColor(@RequestBody ChangeNicknameColorDto changeNicknameColorDto) {
        return userService.changeNicknameColor(changeNicknameColorDto.getUserId(), changeNicknameColorDto.getNicknameColorId());
    }

    @PutMapping("/remove-user-nickname-color/{userId}")
    public HttpStatus removeUserNicknameColor(@PathVariable Long userId) {
        return userService.removeNicknameColor(userId);
    }
}
