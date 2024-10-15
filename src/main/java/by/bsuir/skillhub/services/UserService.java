package by.bsuir.skillhub.services;

import by.bsuir.skillhub.dto.EditPersonDto;
import by.bsuir.skillhub.dto.UserDto;
import by.bsuir.skillhub.entity.Persons;
import by.bsuir.skillhub.entity.Users;
import by.bsuir.skillhub.repo.PersonsRepository;
import by.bsuir.skillhub.repo.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final PersonsRepository personsRepository;

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

    public HttpStatus editUserInfo (EditPersonDto requestBody) throws Exception {
        try{
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
