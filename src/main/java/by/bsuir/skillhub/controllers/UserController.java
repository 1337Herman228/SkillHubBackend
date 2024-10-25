package by.bsuir.skillhub.controllers;

import by.bsuir.skillhub.dto.*;
import by.bsuir.skillhub.repo.CoursesRepository;
import by.bsuir.skillhub.repo.UsersRepository;
import by.bsuir.skillhub.entity.UserProgress;
import by.bsuir.skillhub.repo.UserProgressRepository;
import by.bsuir.skillhub.services.CoursesService;
import by.bsuir.skillhub.services.UserService;
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
    private final UsersRepository usersRepository;
    private final UserProgressRepository userProgressRepository;
    private final CoursesRepository coursesRepository;

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

}
