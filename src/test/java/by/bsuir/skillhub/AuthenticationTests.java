package by.bsuir.skillhub;

import by.bsuir.skillhub.dto.AuthenticationRequest;
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

}
