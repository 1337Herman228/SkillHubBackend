package by.bsuir.skillhub.controllers;

import by.bsuir.skillhub.dto.*;
import by.bsuir.skillhub.entity.RegistrationKeys;
import by.bsuir.skillhub.entity.Roles;
import by.bsuir.skillhub.repo.RolesRepository;
import by.bsuir.skillhub.services.CoursesService;
import by.bsuir.skillhub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('admin')")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final RolesRepository rolesRepository;
    private final CoursesService coursesService;

    @GetMapping("/get-all-reg-keys")
    public List<RegistrationKeys> getAllRegistrationKeys() {
        return userService.getAllRegistrationKeys();
    }

    @GetMapping("/get-reg-keys-by-email/{email}")
    public List<RegistrationKeys> getRegistrationKeysByEmail(@PathVariable String email) {
        return userService.getRegistrationKeysByEmail(email);
    }

    @DeleteMapping("/delete-reg-key/{id}")
    public HttpStatus deleteRegistrationKey(@PathVariable Long id) {
        return userService.deleteRegistrationKey(id);
    }

    @PostMapping("add-reg-key")
    public HttpStatus addRegistrationKey(@RequestBody AddRegKeyDto requestBody) {
        return userService.addRegistrationKey(requestBody.getEmail());
    }

    @GetMapping("/get-all-users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get-all-roles")
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    @PutMapping("/edit-user")
    public HttpStatus editUser(@RequestBody EditUserDto requestBody) {
        return userService.editUser(requestBody);
    }

    @DeleteMapping("/delete-user/{id}")
    public HttpStatus deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/get-all-courses")
    public List<AllCoursesDto> getAllCourses() throws Exception {
        return coursesService.getAllCourses();
    }

    @GetMapping("/find-admin-courses-by-name/{courseName}")
    public List<AllCoursesDto> findAdminCoursesByName(@PathVariable String courseName) throws Exception {
        return coursesService.findAdminCoursesByName(courseName);
    }
}
