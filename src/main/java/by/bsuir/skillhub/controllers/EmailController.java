package by.bsuir.skillhub.controllers;

import by.bsuir.skillhub.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public HttpStatus sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text) {
        return emailService.sendEmail(to, subject, text);
    }
}

