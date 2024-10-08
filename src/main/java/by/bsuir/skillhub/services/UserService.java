package by.bsuir.skillhub.services;

import by.bsuir.skillhub.dto.UserDto;
import by.bsuir.skillhub.entity.Users;
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

    public ResponseEntity<UserDto> getUser(Long userId) {
        try {
            Optional<Users> userOptional = usersRepository.findById(userId);

            if (userOptional.isPresent()) {
                Users user = userOptional.get();
                UserDto userDto = convertToDto(user); // Метод для преобразования Users в UserDto
                return ResponseEntity.ok(userDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
