package aeca.aladdin.controller;

import aeca.aladdin.domain.dto.CertificateTemplateDTO;
import aeca.aladdin.domain.entity.Template;
import aeca.aladdin.service.TemplateService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/templates")
@Api(tags = "Template API")
@CrossOrigin(origins = "${CrossOrigin}")
public class TemplateController {

    private static Logger log = Logger.getLogger(TemplateController.class.getName());

    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @ApiOperation(value = "Получить список шаблонов сертификата, доступных текущему активному центру сертификации")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @GetMapping(value = "/all")
    public ResponseEntity<Set<Integer>> getCertificateTemplates() {
        log.log(Level.INFO, "Вызов метода - getCertificateTemplates");
        Set<Template> set = templateService.getAllTemplates();
        if (set.isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ResponseEntity<Set<Integer>> response = new ResponseEntity<>(set.stream().map(Template::getId).collect(Collectors.toSet()), HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody());
        return response;
    }

    @ApiOperation(value = "Получить детальное описание шаблона сертификата субъекта аутентификации")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
    })
    @GetMapping(value = "/info/{id}")
    public ResponseEntity<CertificateTemplateDTO> getDetailsCertificateTemplate(@ApiParam(value = "id шаблона сертификата АЕСА", required = true) @PathVariable("id") @NonNull int id) {
        log.log(Level.INFO, "Вызов метода - getCertStatus, c id: " + id);
        Template template = templateService.getTemplate(id);
        if (template == null) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CertificateTemplateDTO result = new CertificateTemplateDTO(template.getId(), template.getName());
        ResponseEntity<CertificateTemplateDTO> response = new ResponseEntity<>(result, HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody().toString());
        return response;
    }
}