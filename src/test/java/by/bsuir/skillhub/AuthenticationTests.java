package by.bsuir.skillhub;

import by.bsuir.skillhub.dto.AuthenticationRequest;
import by.bsuir.skillhub.dto.FindCourseByNameForUserDto;
import by.bsuir.skillhub.repo.CoursesRepository;
import by.bsuir.skillhub.repo.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private CoursesRepository coursesRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAuthenticate_Success() throws Exception {
        //Создаем объект аутентификации
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                false, // redirect
                "",    // csrfToken
                "", // callbackUrl
                "{}", // json, например, пустой JSON
                "user", // login
                "useruser" // password
        );

        // Преобразуем объект в JSON
        String requestBody = objectMapper.writeValueAsString(authenticationRequest);

        // Выполняем POST-запрос к контроллеру
        ResultActions result = mockMvc.perform(post("/auth/authenticate")
                .contentType("application/json")
                .content(requestBody));

        // Проверяем успешный ответ
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.authenticationResponse.token").exists())  // Проверка наличия токена
                .andExpect(jsonPath("$.role").value("user"))  // Проверка роли
                .andExpect(jsonPath("$.userId").exists());  // Проверка наличия userId
    }

    @Test
    public void testAuthenticate_Failure_InvalidCredentials() throws Exception {
        // Создаем объект с неверными данными для аутентификации
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                false,  // redirect
                "csrfToken123",  /* csrfToken */
                "http://callback.url", // callbackUrl
                "{}",  // json
                "wrong-login",  // login
                "wrong-password"  // password
        );

        // Преобразуем объект в JSON
        String requestBody = objectMapper.writeValueAsString(authenticationRequest);

        // Выполняем POST-запрос к контроллеру
        ResultActions result = mockMvc.perform(post("/auth/authenticate")
                .contentType("application/json")
                .content(requestBody));

        // Проверяем, что вернулся статус ошибки (403 Forbidden)
        result.andExpect(status().isForbidden());
    }


    @Test
    public void testFindCoursesByNameForUser_Success() throws Exception {

//        Данные пользователя, лежащие в БД
//        diamonds = 10,
//        person_id = 10002,
//        role_id = 10002,
//        user_id = 10002,
//        login = user,
//        password = $2a$10$ifTWyzL6LxlJmKimHYUHX.dQa9I/yHGjxBn6lzgWEgAE8AHmI8b56

//        Данные курса, лежащие в БД
//        course_id = 10,
//        course_img = course-previewt=1731491408431.png,
//        course_name = Python from zero to pro,
//        certificate = certificate-python-from-zero-to-prot=1733680737585.pdf

        // Создаем объект запроса для поиска курса по имени
        FindCourseByNameForUserDto request = new FindCourseByNameForUserDto();
        request.setUserId(10002L);  // ID пользователя
        request.setCourseName("Python from");  // Текст, по которому будет проводится поиск

        // Преобразуем объект в JSON
        String requestBody = objectMapper.writeValueAsString(request);

        // Заранее задаем валидный токен
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZWFjaGVyIiwiaWF0IjoxNzMzNjcyMTQyLCJleHAiOjE3MzM4NDQ5NDJ9.kk8rkj5pKUZVKkLf5plOXXsa7RPXeMSTdWfQlJtKW54";

        // Выполняем POST-запрос к контроллеру
        ResultActions result = mockMvc.perform(post("/user/find-courses-by-name-for-user")
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)  // Добавляем токен в заголовок
                .content(requestBody));

        // Проверяем успешный ответ
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].course.courseName").value("Python from zero to pro"));
    }
}
