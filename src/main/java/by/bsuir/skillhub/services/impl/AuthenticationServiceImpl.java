package by.bsuir.skillhub.services.impl;

import by.bsuir.skillhub.dto.AuthenticationRequest;
import by.bsuir.skillhub.dto.AuthenticationResponse;
import by.bsuir.skillhub.dto.NewUserDto;
import by.bsuir.skillhub.entity.Persons;
import by.bsuir.skillhub.entity.Roles;
import by.bsuir.skillhub.entity.Users;
import by.bsuir.skillhub.repo.PersonsRepository;
import by.bsuir.skillhub.repo.RegistrationKeysRepository;
import by.bsuir.skillhub.repo.RolesRepository;
import by.bsuir.skillhub.repo.UsersRepository;
import by.bsuir.skillhub.security.MyUserDetails;
import by.bsuir.skillhub.services.AuthenticationService;
import by.bsuir.skillhub.services.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final UsersRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PersonsRepository personRepository;
    private final RegistrationKeysRepository registrationKeysRepository;


    @Override
    @Transactional
    public AuthenticationResponse register(NewUserDto newUser) {

        // Проверяем существует ли пользователь
        String login = newUser.getLogin();
        if (userRepository.findByLogin(login).isPresent()) {
            throw new RuntimeException("User with login: %s already exists".formatted(login));
        }

        // Проверяем совпадают ли реферальные ключи
        String email = newUser.getEmail();
        String referralKey = newUser.getReferralKey();
        if ( registrationKeysRepository.findByEmail(email).isEmpty() || !registrationKeysRepository.findByEmail(email).get().getRegKey().equals(referralKey)) {
            throw new RuntimeException("Referral keys are not equal");
        }

        // Создаем пользователя и вносим в БД
        Persons person = new Persons();
        person.setName(newUser.getName());
        person.setSurname(newUser.getSurname());
        person.setEmail(newUser.getEmail());
        personRepository.save(person);

        Roles role = rolesRepository.findByPosition("user").orElseThrow();
        Users user = new Users()
                .setLogin(login)
                .setPassword(passwordEncoder.encode(newUser.getPassword()))
                .setRole(role)
                .setPerson(person);
        userRepository.save(user);
        String token = jwtService.generateToken(new MyUserDetails(user));

        // Удаляем запись с реферальным ключом после регистрации
        registrationKeysRepository.delete(registrationKeysRepository.findByEmail(email).get());

        return new AuthenticationResponse(token);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.login(),
                        authenticationRequest.password()
                )
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.login());
        String token = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(token);
    }
}
