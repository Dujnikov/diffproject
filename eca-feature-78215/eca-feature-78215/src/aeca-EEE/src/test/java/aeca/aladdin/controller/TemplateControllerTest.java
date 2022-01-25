package aeca.aladdin.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TemplateController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void whenGetTemplatesThenStatus200() throws Exception {
        mockMvc.perform(get("/templates/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetTemplateByIdThenStatus200() throws Exception {
        mockMvc.perform(get("/templates/info/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetTemplateByBadIdThenStatus404() throws Exception {
        mockMvc.perform(get("/templates/info/-5"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}