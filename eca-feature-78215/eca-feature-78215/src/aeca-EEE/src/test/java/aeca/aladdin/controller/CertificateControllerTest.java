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
class CertificateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CertificateController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void whenGetCertBySeNoThenStatus200() throws Exception {
        mockMvc.perform(get("/certificates/status/iAmSerialNumber1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetCertByBadSeNoThenStatus404() throws Exception {
        mockMvc.perform(get("/certificates/status/iAmSerialNumber-1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenRevokeCertBySeNoThenStatus200() throws Exception {
        mockMvc.perform(put("/certificates/revoke")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"certSerialNumber\":\"iAmSerialNumber1\", \"revocationReason\":\"AA_COMPROMISE\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenSuspendCertBySeNoThenStatus200() throws Exception {
        mockMvc.perform(put("/certificates/suspend")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"certSerialNumber\":\"iAmSerialNumber2\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenReactivateCertBySeNoThenStatus200() throws Exception {
        mockMvc.perform(put("/certificates/reactivate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"certSerialNumber\":\"iAmSerialNumber3\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenEnrollCertThenStatus200() throws Exception {
        mockMvc.perform(post("/certificates/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"authSubjectId\": 1,\n" +
                                "  \"certFormat\": \"string\",\n" +
                                "  \"file\": \"string\",\n" +
                                "  \"templateId\": 1\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenCreateAndEnrollCertThenStatus200() throws Exception {
        mockMvc.perform(post("/certificates/create-and-enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"certFormat\": \"string\",\n" +
                                "  \"file\": \"string\",\n" +
                                "  \"groupId\": 1,\n" +
                                "  \"templateId\": 1\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}