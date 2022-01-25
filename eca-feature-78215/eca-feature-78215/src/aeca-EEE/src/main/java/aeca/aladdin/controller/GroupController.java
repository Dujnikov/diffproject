package aeca.aladdin.controller;

import aeca.aladdin.domain.dto.GroupDTO;
import aeca.aladdin.domain.entity.AuthSubject;
import aeca.aladdin.domain.entity.Group;
import aeca.aladdin.domain.entity.Template;
import aeca.aladdin.service.GroupService;
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
@RequestMapping(value = "/groups")
@Api(tags = "Group API")
@CrossOrigin(origins = "${CrossOrigin}")
public class GroupController {

    private static Logger log = Logger.getLogger(GroupController.class.getName());

    private final static String GROUP_ID = "id группы";

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @ApiOperation(value = "Получить информацию о списке доступных групп субъектов аутентификации")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
    })
    @GetMapping(value = "/all")
    public ResponseEntity<Set<Integer>> getAllGroup() {
        log.log(Level.INFO, "Вызов метода - getAllGroup");
        Set<Group> set = groupService.getAllGroups();
        if (set.isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ResponseEntity<Set<Integer>> response = new ResponseEntity<>(set.stream().map(Group::getId).collect(Collectors.toSet()), HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody());
        return response;
    }

    @ApiOperation(value = "Получить информацию о группе субъектов аутентификации по id")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
    })
    @GetMapping(value = "/info/{id}")
    public ResponseEntity<GroupDTO> getGroupForId(@ApiParam(value = GROUP_ID, required = true) @PathVariable("id") @NonNull int id) {
        log.log(Level.INFO, "Вызов метода - getGroupForId, c id: " + id);
        Group group = groupService.getGroup(id);
        if (group == null) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        GroupDTO result = new GroupDTO(group.getId(), group.getName());
        if (group.getTemplates() != null && !group.getTemplates().isEmpty()) {
            result.setTemplateIds(group.getTemplates().stream().map(Template::getId).collect(Collectors.toSet()));
        }
        ResponseEntity<GroupDTO> response = new ResponseEntity<>(result, HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody().toString());
        return response;
    }

    @ApiOperation(value = "Получить список субъектов, состоящих в группе субъектов аутентификации")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK", response = Integer.class, responseContainer = "Set"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
    })
    @GetMapping(value = "/info/{id}/subjects")
    public ResponseEntity<Set<Integer>> getSubjectForGroupId(@ApiParam(value = GROUP_ID, required = true) @PathVariable("id") @NonNull int id) {
        log.log(Level.INFO, "Вызов метода - getSubjectForGroupId, c id: " + id);
        Group group = groupService.getGroup(id);
        if (group == null || group.getSubjects() == null || group.getSubjects().isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ResponseEntity<Set<Integer>> response = new ResponseEntity<>(group.getSubjects().stream().map(AuthSubject::getId).collect(Collectors.toSet()), HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody());
        return response;
    }

    @ApiOperation(value = "Получить список шаблонов сертификатов, доступных группе субъектов аутентификации и текущему УЦ")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK", response = Integer.class, responseContainer = "Set"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
    })
    @GetMapping(value = "/info/{id}/template")
    public ResponseEntity<Set<Integer>> getTemplateForGroupId(@ApiParam(value = GROUP_ID, required = true) @PathVariable("id") @NonNull int id) {
        log.log(Level.INFO, "Вызов метода - getTemplateForGroupId, c id: " + id);
        Group group = groupService.getGroup(id);
        if (group == null || group.getTemplates() == null || group.getTemplates().isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ResponseEntity<Set<Integer>> response = new ResponseEntity<>(group.getTemplates().stream().map(Template::getId).collect(Collectors.toSet()), HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody());
        return response;
    }
}