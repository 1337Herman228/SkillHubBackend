package by.bsuir.skillhub.controllers;

import by.bsuir.skillhub.dto.UserDto;
import by.bsuir.skillhub.entity.Users;
import by.bsuir.skillhub.repo.UsersRepository;
import by.bsuir.skillhub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UsersRepository usersRepository;

    //Находим пользователя по ID
    @GetMapping("/get-user/{userId}")
    public UserDto getUser(@PathVariable Long userId) throws Exception {
        System.out.println(userId);
        return userService.getUser(userId);
    }

//    //Находим пользователя по ID
//    @GetMapping("/get-user/{userId}")
//    public Users getUser(@PathVariable Long userId) {
//        System.out.println(userId);
//        return usersRepository.findById(userId).get();
//    }
}
