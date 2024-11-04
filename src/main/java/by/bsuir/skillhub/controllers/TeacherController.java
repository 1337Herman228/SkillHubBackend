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

    @PutMapping("/edit-video-lesson")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus editVideoLesson(@RequestBody EditVideoLessonDto requestBody) throws Exception {
        return coursesService.editLesson(requestBody);
    }

    @PutMapping("/edit-course")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus editCourse(@RequestBody EditCourseDto requestBody) throws Exception {
        return coursesService.editCourse(requestBody);
    }

}
