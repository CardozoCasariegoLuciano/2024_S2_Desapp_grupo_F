package unq.cryptoexchange.PersonTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterPersonSuccessfully() throws Exception {
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .name("name")
                .lastname("lastame")
                .email("email@mail.com")
                .password("unaPassword123_")
                .address("una adress")
                .cvu("stringstringstringstri")
                .wallet("stgasdasdst")
                .build();
        mockMvc.perform(post("/api/v1/person/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Person registered successfully"));
    }
}
