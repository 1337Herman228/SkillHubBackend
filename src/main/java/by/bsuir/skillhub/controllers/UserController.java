package by.bsuir.skillhub.controllers;

import by.bsuir.skillhub.dto.ContinueCourseDto;
import by.bsuir.skillhub.dto.UserDto;
import by.bsuir.skillhub.entity.Courses;
import by.bsuir.skillhub.entity.UserProgress;
import by.bsuir.skillhub.entity.Users;
import by.bsuir.skillhub.repo.UserProgressRepository;
import by.bsuir.skillhub.repo.UsersRepository;
import by.bsuir.skillhub.services.CoursesService;
import by.bsuir.skillhub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
@PreAuthorize("hasAnyAuthority('user', 'admin','teacher')")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CoursesService coursesService;
    private final UsersRepository usersRepository;
    private final UserProgressRepository userProgressRepository;

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

//    @GetMapping("/get-all-courses")
//    public List<Courses> getUserProgress(@PathVariable Long userId) throws Exception {
//        return userProgressRepository.findByUser(usersRepository.findById(userId).get());
//    }
}
