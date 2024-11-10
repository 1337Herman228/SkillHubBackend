package by.bsuir.skillhub.controllers;


import by.bsuir.skillhub.dto.*;
import by.bsuir.skillhub.repo.UsersRepository;
import by.bsuir.skillhub.services.CoursesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/teacher")
@PreAuthorize("hasAnyAuthority('admin','teacher')")
@RequiredArgsConstructor
public class TeacherController {

    private final CoursesService coursesService;
    private final UsersRepository usersRepository;

    @GetMapping("/get-teacher-courses/{teacherId}")
    public List<AllCoursesDto> getTeacherCourses(@PathVariable Long teacherId) throws Exception {
        return coursesService.getTeacherCourses(usersRepository.findById(teacherId).get());
    }

    @GetMapping("/get-request-access-users/{courseId}")
    public List<GetAccessUsersDto> getRequestAccessUsers(@PathVariable Long courseId) throws Exception {
        return coursesService.getRequestAccessUsers(courseId);
    }

    @PostMapping("/get-request-access-users-by-name")
    public List<GetAccessUsersDto> getRequestAccessUsersByName(@RequestBody GetHasAccessUsersByNameDto requestBody) throws Exception {
        return coursesService.getRequestAccessUsersByName(requestBody);
    }

    @PutMapping("/approve-course-access")
    public HttpStatus approveCourseAccess(@RequestBody EditCourseAccessDto requestBody) throws Exception {
        return coursesService.approveCourseAccess(requestBody);
    }

    @PutMapping("/reject-course-access")
    public HttpStatus rejectCourseAccess(@RequestBody EditCourseAccessDto requestBody) throws Exception {
        return coursesService.rejectCourseAccess(requestBody);
    }

    @GetMapping("/get-has-access-users/{courseId}")
    public List<GetAccessUsersDto> getHasAccessUsers(@PathVariable Long courseId) throws Exception {
        return coursesService.getHasAccessUsers(courseId);
    }

    @PostMapping("/get-has-access-users-by-name")
    public List<GetAccessUsersDto> getHasAccessUsersByName(@RequestBody GetHasAccessUsersByNameDto requestBody) throws Exception {
        return coursesService.getHasAccessUsersByName(requestBody);
    }

    @PostMapping("/find-teacher-courses-by-name")
    public List<AllCoursesDto> findTeacherCoursesByName(@RequestBody FindCourseByNameForUserDto requestBody) throws Exception {
        return coursesService.findTeacherCoursesByName(
                usersRepository.findById(requestBody.getUserId()).get(),
                requestBody.getCourseName());
    }

    @PostMapping("/add-new-course")
    public HttpStatus addNewCourse(@RequestBody AddNewCourseDto requestBody) throws Exception {
        return coursesService.addNewCourse(requestBody);
    }

    @PostMapping("/add-new-chapter")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus addNewChapter(@RequestBody AddNewChapterDto requestBody) throws Exception {
        return coursesService.addNewChapter(requestBody);
    }

    @PostMapping("/add-new-video-lesson")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus addNewVideoLesson(@RequestBody AddNewVideoLessonDto requestBody) throws Exception {
        return coursesService.addNewVideoLesson(requestBody);
    }

    @PostMapping("/add-new-text-lesson")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus addNewTextLesson(@RequestBody AddNewTextLessonDto requestBody) throws Exception {
        return coursesService.addNewTextLesson(requestBody);
    }

    @PostMapping("/add-new-test-lesson")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus addNewTestLesson(@RequestBody AddTestLessonDto requestBody) throws Exception {
        return coursesService.addTestLesson(requestBody);
    }

    @PutMapping("/edit-video-lesson")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus editVideoLesson(@RequestBody EditVideoLessonDto requestBody) throws Exception {
        return coursesService.editVideoLesson(requestBody);
    }

    @PutMapping("/edit-text-lesson")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus editTextLesson(@RequestBody EditTextLessonDto requestBody) throws Exception {
        return coursesService.editTextLesson(requestBody);
    }

    @PutMapping("/edit-test-lesson")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus editTestLesson(@RequestBody EditTestLessonDto requestBody) throws Exception {
        return coursesService.editTestLesson(requestBody);
    }

    @PutMapping("/edit-course")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus editCourse(@RequestBody EditCourseDto requestBody) throws Exception {
        return coursesService.editCourse(requestBody);
    }

    @DeleteMapping("/delete-course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus deleteCourse(@PathVariable Long courseId) throws Exception {
        return coursesService.deleteCourse(courseId);
    }

    @DeleteMapping("/delete-lesson/{lessonId}")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus deleteLesson(@PathVariable Long lessonId) throws Exception {
        return coursesService.deleteLesson(lessonId);
    }

    @DeleteMapping("/remove-user-access/{userId}/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus removeUserAccess(@PathVariable Long userId, @PathVariable Long courseId) throws Exception {
        return coursesService.removeUserAccess(userId, courseId);
    }

}
