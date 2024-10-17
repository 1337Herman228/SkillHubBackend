package by.bsuir.skillhub.services;

import by.bsuir.skillhub.dto.*;
import by.bsuir.skillhub.entity.BecomeTeacher;
import by.bsuir.skillhub.entity.Persons;
import by.bsuir.skillhub.entity.Users;
import by.bsuir.skillhub.repo.BecomeTeacherRepository;
import by.bsuir.skillhub.repo.PersonsRepository;
import by.bsuir.skillhub.repo.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final PersonsRepository personsRepository;
    private final BecomeTeacherRepository becomeTeacherRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<UserDto> getUser(Long userId) throws Exception {
        try {
            Optional<Users> userOptional = usersRepository.findById(userId);

            if (userOptional.isPresent()) {
                Users user = userOptional.get();
                UserDto userDto = convertToDto(user); // Метод для преобразования Users в UserDto
                return ResponseEntity.status(HttpStatus.OK).body(userDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            throw new Exception("Can't get user", e);
        }
    }

    public HttpStatus editUserInfo(EditPersonDto requestBody) throws Exception {
        try {
            Users user = usersRepository.findById(requestBody.getUserId()).orElse(null);
            assert user != null;
            Persons person = personsRepository.findById(user.getPerson().getPersonId()).orElse(null);
            user.setLogin(requestBody.getLogin());
            assert person != null;
            person.setName(requestBody.getName());
            person.setSurname(requestBody.getSurname());
            person.setEmail(requestBody.getEmail());
            usersRepository.save(user);
            personsRepository.save(person);
            return HttpStatus.OK;
        } catch (Exception e) {
            throw new Exception("Can't edit user info", e);
        }
    }

    public HttpStatus editUserPhoto(EditPhotoDto requestBody) throws Exception {
        try {
            Users user = usersRepository.findById(requestBody.getUserId()).orElse(null);
            assert user != null;
            Persons person = personsRepository.findById(user.getPerson().getPersonId()).orElse(null);
            assert person != null;
            person.setAvatarImg(requestBody.getImgLink());
            personsRepository.save(person);
            return HttpStatus.OK;
        } catch (Exception e) {
            throw new Exception("Can't edit user photo", e);
        }
    }

    public HttpStatus addBecomeTeacherRecord(AddTeacherRequest requestBody) {
        if (becomeTeacherRepository.findByUser(usersRepository.findById(requestBody.getUserId()).get()).isPresent()) {
            return HttpStatus.BAD_REQUEST;
        } else {
            BecomeTeacher becomeTeacher = new BecomeTeacher();
            becomeTeacher.setUser(usersRepository.findById(requestBody.getUserId()).get());
            becomeTeacher.setCourseName(requestBody.getCourseName());
            becomeTeacher.setCourseSphere(requestBody.getCourseSphere());
            becomeTeacher.setCourseDescription(requestBody.getCourseDescription());
            becomeTeacher.setRequestDate(new Timestamp(new Date().getTime()));
            becomeTeacher.setStatus(BecomeTeacher.TeacherStatus.PENDING);
            becomeTeacherRepository.save(becomeTeacher);
            return HttpStatus.OK;
        }
    }

    public HttpStatus changeUserPassword(ChangeUserPasswordDto requestBody) {
        Users user = usersRepository.findById(requestBody.getUserId()).orElse(null);
        assert user != null;
        if (passwordEncoder.matches(requestBody.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(requestBody.getNewPassword()));
            usersRepository.save(user);
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    // Преобразование Users в UserDto
    public UserDto convertToDto(Users user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setPerson(user.getPerson());
        userDto.setRole(user.getRole());
        userDto.setLogin(user.getLogin());
        userDto.setDiamonds(user.getDiamonds());
        return userDto;
    }
}
