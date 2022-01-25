package aeca.aladdin.controller;

import aeca.aladdin.domain.dto.*;
import aeca.aladdin.domain.entity.AuthSubject;
import aeca.aladdin.domain.entity.Certificate;
import aeca.aladdin.domain.entity.Group;
import aeca.aladdin.domain.entity.Template;
import aeca.aladdin.service.AuthSubjectService;
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
@RequestMapping(value = "/subjects")
@Api(tags = "Subject API")
@CrossOrigin(origins = "${CrossOrigin}")
public class AuthSubjectController {

    private static Logger log = Logger.getLogger(AuthSubjectController.class.getName());

    private final static String CREATE_SUBJECT = "subjectDN, subjectAltName (опционально), список id групп, которые будут связаны с субъект аутентификации (опционально)";
    private final static String EDIT_SUBJECT = "id субъекта аутентификации, subjectDN, subjectAltName";
    private final static String BIND = "id субъекта аутентификации, id группы";
    private final static String BIND_SET = "id субъекта аутентификации, список id групп";
    private final static String ID = "id субъекта аутентификации";
    private final static String FILTER = "вариант фильтрации сертификатов";

    private final AuthSubjectService authSubjectService;

    public AuthSubjectController(AuthSubjectService authSubjectService) {
        this.authSubjectService = authSubjectService;
    }

    @ApiOperation("Получить список id всех существующих субъектов аутентификации")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
    })
    @GetMapping(value = "all")
    public ResponseEntity<Set<Integer>> getAllAuthSubject() {
        log.log(Level.INFO, "Вызов метода - getAllAuthSubject");
        Set<AuthSubject> set = authSubjectService.getAllAuthSubject();
        if (set.isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ResponseEntity<Set<Integer>> response = new ResponseEntity<>(set.stream().map(AuthSubject::getId).collect(Collectors.toSet()), HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody());
        return response;
    }

    @ApiOperation("Получить информацию о конкретном субъекте аутентификации по его id")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @GetMapping(value = "/info/{id}")
    public ResponseEntity<AuthSubjectWOCertNumbersDTO> getAuthSubject(@ApiParam(value = ID, required = true) @PathVariable("id") @NonNull int id) {
        log.log(Level.INFO, "Вызов метода - getAuthSubject, c id: " + id);
        AuthSubject authSubject = authSubjectService.getAuthSubject(id);
        if (authSubject == null) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthSubjectWOCertNumbersDTO result = new AuthSubjectWOCertNumbersDTO(authSubject.getId(),
                authSubject.getSubjectDN(), authSubject.getSubjectAltName());
        if (authSubject.getTemplates() != null && !authSubject.getTemplates().isEmpty()) {
            result.setTemplateIds(authSubject.getTemplates().stream().map(Template::getId).collect(Collectors.toSet()));
        }
        if (authSubject.getGroups() != null && !authSubject.getGroups().isEmpty()) {
            result.setTemplateIds(authSubject.getGroups().stream().map(Group::getId).collect(Collectors.toSet()));
        }
        ResponseEntity<AuthSubjectWOCertNumbersDTO> response = new ResponseEntity(result, HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody().toString());
        return response;
    }

    @ApiOperation(value = "Получить список Id групп субъекта аутентификации")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @GetMapping(value = "/info/{id}/groups")
    public ResponseEntity<Set<Integer>> getSubjectGroupForId(@ApiParam(value = ID, required = true) @PathVariable("id") @NonNull int id) {
        log.log(Level.INFO, "Вызов метода - getSubjectGroupForId, с id: " + id);
        AuthSubject authSubject = authSubjectService.getAuthSubject(id);
        if (authSubject == null || authSubject.getGroups() == null || authSubject.getGroups().isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<Integer> result = authSubject.getGroups().stream().map(Group::getId).collect(Collectors.toSet());
        ResponseEntity<Set<Integer>> response = new ResponseEntity(result, HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody());
        return response;
    }

    @ApiOperation(value = "Получить список шаблонов сертификатов активного УЦ, доступных субъекту аутентификации")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @GetMapping(value = "/info/{id}/templates")
    public ResponseEntity<Set<Integer>> getTemplatesForId(@ApiParam(value = ID, required = true) @PathVariable("id") @NonNull int id) {
        log.log(Level.INFO, "Вызов метода - getTemplatesForId, с id: " + id);
        AuthSubject authSubject = authSubjectService.getAuthSubject(id);
        if (authSubject == null || authSubject.getTemplates() == null || authSubject.getTemplates().isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //todo нет проверки на активный УЦ
        Set<Integer> result = authSubject.getTemplates().stream().map(Template::getId).collect(Collectors.toSet());
        ResponseEntity<Set<Integer>> response = new ResponseEntity(result, HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody());
        return response;
    }

    @ApiOperation(value = "Получить список серийных номеров действующих сертификатов, выпущенных для субъекта аутентификации по его id")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @GetMapping(value = "/info/{id}/certificates")
    public ResponseEntity<Set<String>> getCertificateForId(@ApiParam(value = ID, required = true) @PathVariable("id") @NonNull int id) {
        log.log(Level.INFO, "Вызов метода - getCertificateForId, с id: " + id);
        AuthSubject authSubject = authSubjectService.getAuthSubject(id);
        if (authSubject == null || authSubject.getCertificates() == null || authSubject.getCertificates().isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<String> result = authSubject.getCertificates().stream()
                .filter(certificate -> certificate.getStatus() == 1)
                .map(Certificate::getSerialNumber).collect(Collectors.toSet());
        ResponseEntity<Set<String>> response = new ResponseEntity(result, HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody());
        return response;
    }

    @ApiOperation(value = "Получить список серийных номеров сертификатов, выпущенных для субъекта аутентификации, отфильтрованных по статусу сертификатов")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @GetMapping(value = "/info/{id}/certificates/{filter}")
    public ResponseEntity<Set<IdAndStatusCertDTO>> getCertificateForIdFilter(@ApiParam(value = ID, required = true) @PathVariable("id") @NonNull int id,
                                                                             @ApiParam(value = FILTER, required = true) @PathVariable("filter") @NonNull CertStatusEnum filter) {
        log.log(Level.INFO, "Вызов метода - getCertificateForIdFilter, с id: " + id + " и Filter: " + filter);
        AuthSubject authSubject = authSubjectService.getAuthSubject(id);
        if (authSubject == null || authSubject.getCertificates() == null || authSubject.getCertificates().isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<IdAndStatusCertDTO> result = authSubject.getCertificates().stream()
                .filter(certificate -> certificate.getStatus() == filter.getCode())
                .map(certificate -> new IdAndStatusCertDTO(certificate.getSerialNumber(), filter)).collect(Collectors.toSet());
        if (result.isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ResponseEntity<Set<IdAndStatusCertDTO>> response = new ResponseEntity(result, HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody().toString());
        return response;
    }

    @ApiOperation("Создать субъект аутентификации")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @PostMapping(value = "/create")
    public ResponseEntity<Integer> createAuthSubject(@ApiParam(value = CREATE_SUBJECT, required = true) @RequestBody @NonNull AuthSubjectWOIdDTO input) {
        log.log(Level.INFO, "Вызов метода - createAuthSubject, с данными: " + input.toString());
        int resultId = authSubjectService.createAuthSubject(input.getSubjectDN(), input.getSubjectAltName(), input.getGroupIds());
        if (resultId == 0) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<Integer> response = new ResponseEntity(resultId, HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + " и id созданного субъекта: " + response.getBody().toString());
        return response;
    }

    @ApiOperation("Изменить субъект аутентификации")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @PutMapping(value = "/edit")
    public ResponseEntity<Void> editAuthSubject(@ApiParam(value = EDIT_SUBJECT, required = true) @RequestBody @NonNull AuthSubjectWOGroupDTO input) {
        log.log(Level.INFO, "Вызов метода - editAuthSubject, с данными: " + input.toString());
        boolean result = authSubjectService.editAuthSubject(input.getId(), input.getSubjectDN(), input.getSubjectAltName());
        if (!result) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.log(Level.INFO, "Статус ответа: " + HttpStatus.OK);
        ;
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("Добавить субъект в группу")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @PutMapping(value = "/addgroup")
    public ResponseEntity<Void> addGroupToAuthSubject(@ApiParam(value = BIND, required = true) @RequestBody @NonNull IdSubjectAndIdGroup input) {
        log.log(Level.INFO, "Вызов метода - addGroupToAuthSubject, с данными: " + input.toString());
        boolean result = authSubjectService.addGroupToSubject(input.getSubjectId(), input.getGroupId());
        if (!result) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.log(Level.INFO, "Статус ответа: " + HttpStatus.OK);
        ;
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @DeleteMapping(value = "/removegroup")
    @ApiOperation("Исключить субъект из заданной группы")
    public ResponseEntity<Void> removeGroupToAuthSubject(@ApiParam(value = BIND, required = true) @RequestBody @NonNull IdSubjectAndIdGroup input) {
        log.log(Level.INFO, "Вызов метода - removeGroupToAuthSubject, с данными: " + input.toString());
        boolean result = authSubjectService.removeSubjectToGroup(input.getSubjectId(), input.getGroupId());
        if (!result) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.log(Level.INFO, "Статус ответа: " + HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("Явно задать группы субъекта аутентификации")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @PutMapping(value = "/setgroups")
    public ResponseEntity<Void> setGroupToAuthSubject(@ApiParam(value = BIND_SET, required = true) @RequestBody @NonNull IdSubjectAndSetGroupIds input) {
        log.log(Level.INFO, "Вызов метода - setGroupToAuthSubject, с данными: " + input.toString());
        boolean result = authSubjectService.setGroupsToSubject(input.getSubjectId(), input.getGroupIds());
        if (!result) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.log(Level.INFO, "Статус ответа: " + HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("Удалить субъект аутентификации")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Void> deleteAuthSubject(@ApiParam(value = ID, required = true) @RequestBody @NonNull IdDTO id) {
        log.log(Level.INFO, "Вызов метода - deleteAuthSubject, с id: " + id.getId());
        boolean result = authSubjectService.deleteSubject(id.getId());
        if (!result) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.log(Level.INFO, "Статус ответа: " + HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}