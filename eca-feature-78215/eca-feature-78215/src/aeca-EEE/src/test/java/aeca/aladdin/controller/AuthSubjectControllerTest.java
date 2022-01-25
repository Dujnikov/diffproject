package aeca.aladdin.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthSubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthSubjectController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void whenGetSubjectsThenStatus200() throws Exception {
        mockMvc.perform(get("/subjects/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetSubjectByIdThenStatus200() throws Exception {
        mockMvc.perform(get("/subjects/info/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetSubjectByBadIdThenStatus404() throws Exception {
        mockMvc.perform(get("/subjects/info/-5"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetSubjectGroupByIdThenStatus200() throws Exception {
        mockMvc.perform(get("/subjects/info/1/groups"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetSubjectTemplateByIdThenStatus200() throws Exception {
        mockMvc.perform(get("/subjects/info/1/templates"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetSubjectCertificateByIdThenStatus200() throws Exception {
        mockMvc.perform(get("/subjects/info/1/certificates"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetSubjectCertificateByIdAndFilterThenStatus200() throws Exception {
        mockMvc.perform(get("/subjects/info/1/certificates/CERT_ACTIVE"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenCreateSubjectThenStatus200() throws Exception {
        mockMvc.perform(post("/subjects/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"groupIds\":[1], \"subjectAltName\":\"string\", \"subjectDN\":\"string\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenEditSubjectThenStatus200() throws Exception {
        mockMvc.perform(put("/subjects/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"subjectAltName\":\"string\", \"subjectDN\":\"string\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenAddSubjectToGroupThenStatus200() throws Exception {
        mockMvc.perform(put("/subjects/addgroup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"groupId\":1, \"subjectId\":1}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenRemoveSubjectToGroupThenStatus200() throws Exception {
        mockMvc.perform(delete("/subjects/removegroup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"groupId\":1, \"subjectId\":1}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenSetGroupAddToSubjectThenStatus200() throws Exception {
        mockMvc.perform(put("/subjects/setgroups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"groupIds\":[1], \"subjectId\":1}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenSubjectDeleteThenStatus200() throws Exception {
        mockMvc.perform(delete("/subjects/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}