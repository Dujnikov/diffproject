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
class CAControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CAController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void whenGetCAInfoThenStatus200() throws Exception {
        mockMvc.perform(get("/ui/get-active-ca"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetAllCAThenStatus200() throws Exception {
        mockMvc.perform(get("/ui/get-all-ca"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenCreateCAThenStatus200() throws Exception {
        mockMvc.perform(post("/ui/create-ca")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "        \"encryptionType\": \"string\",\n" +
                                "        \"hash\": \"string\",\n" +
                                "        \"keyLength\": 0,\n" +
                                "        \"name\": \"string\",\n" +
                                "        \"subjectAltName\": \"string\",\n" +
                                "        \"subjectDN\": \"string\",\n" +
                                "        \"type\": \"ROOTCA\",\n" +
                                "        \"validity\": 0\n" +
                                "        }"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenCreateAndGetCSRByIdThenStatus200() throws Exception {
        mockMvc.perform(put("/ui/create-csr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\": 1\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenActivateCAByIdThenStatus200() throws Exception {
        mockMvc.perform(post("/ui/activate-ca")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\": 1\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetCRLByIdThenStatus200() throws Exception {
        mockMvc.perform(post("/ui/create-and-get-crl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\": 1\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetCRTByIdThenStatus200() throws Exception {
        mockMvc.perform(get("/ui/get-cert/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenImportCertThenStatus200() throws Exception {
        mockMvc.perform(put("/ui/import-response")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"file\": \"string\",\n" +
                                "  \"id\": 2\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetPublishPointThenStatus200() throws Exception {
        mockMvc.perform(get("/ui/get-publication-point/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenEditPublishPointThenStatus200() throws Exception {
        mockMvc.perform(put("/ui/add-publication-point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"aiaDistributionPoint\": \"string\",\n" +
                                "  \"aiaPublicationPoint\": \"string\",\n" +
                                "  \"caId\": 1,\n" +
                                "  \"crlDistributionPoint\": \"string\",\n" +
                                "  \"crlPublicationPoint\": \"string\",\n" +
                                "  \"publicationPeriod\": 0\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenPublicCRLThenStatus200() throws Exception {
        mockMvc.perform(post("/ui/publication-crl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\": 1\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenPublicAIAThenStatus200() throws Exception {
        mockMvc.perform(post("/ui/publication-aia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\": 1\n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}