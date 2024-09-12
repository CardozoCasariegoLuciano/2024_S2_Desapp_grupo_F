package unq.CryptoExchange.PersonTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;

import unq.CryptoExchange.dto.request.PersonRegistrationDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldRegisterPersonSuccessfully() throws Exception {

        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
            .name("Pablo")
            .lastname("Berna")
            .email("pablo.berna@example.com")
            .password("unaPassword123")
            .build();

        mockMvc.perform(post("/api/v1/person/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Person registered successfully"));
    }
}
