package by.bsuir.skillhub.controllers;

import by.bsuir.skillhub.dto.UserDto;
import by.bsuir.skillhub.repo.UsersRepository;
import by.bsuir.skillhub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
@PreAuthorize("hasAnyAuthority('user','admin','teacher')")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UsersRepository usersRepository;

    //Находим пользователя по ID
    @GetMapping("/get-user/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws Exception {
        return userService.getUser(userId);
    }
}
