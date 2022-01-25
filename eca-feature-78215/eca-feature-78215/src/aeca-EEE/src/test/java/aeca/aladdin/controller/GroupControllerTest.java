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
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GroupController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void whenGetGroupsThenStatus200() throws Exception {
        mockMvc.perform(get("/groups/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetGroupByIdThenStatus200() throws Exception {
        mockMvc.perform(get("/groups/info/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetSubjectByBadIdThenStatus404() throws Exception {
        mockMvc.perform(get("/groups/info/-5"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetGroupTemplateByIdThenStatus200() throws Exception {
        mockMvc.perform(get("/groups/info/1/template"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}