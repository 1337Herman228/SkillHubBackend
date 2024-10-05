package by.bsuir.skillhub.services;

import by.bsuir.skillhub.dto.AuthenticationRequest;
import by.bsuir.skillhub.dto.AuthenticationResponse;
import by.bsuir.skillhub.dto.NewUserDto;

public interface AuthenticationService {

    AuthenticationResponse register(NewUserDto newUserDao);

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
