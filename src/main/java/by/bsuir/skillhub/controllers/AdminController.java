package by.bsuir.skillhub.controllers;

import by.bsuir.skillhub.dto.AddRegKeyDto;
import by.bsuir.skillhub.dto.AllCoursesDto;
import by.bsuir.skillhub.entity.RegistrationKeys;
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

}
