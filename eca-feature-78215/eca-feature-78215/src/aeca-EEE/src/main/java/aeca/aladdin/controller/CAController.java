package aeca.aladdin.controller;

import aeca.aladdin.domain.dto.*;
import aeca.aladdin.domain.entity.CAInfo;
import aeca.aladdin.service.CAService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/ui")
@Api(tags = "CA API")
@CrossOrigin(origins = "${CrossOrigin}")
public class CAController {

    private static Logger log = Logger.getLogger(CAController.class.getName());

    private final static String CA_PARAM = "параметры, необходимые для создания УЦ";
    private final static String CA_ID = "id УЦ";
    private final static String CA_ID_AND_RESPONSE_FILE = "Файл ответ и id УЦ";
    private final static String CA_PUBLICATION_AND_DISTRIBUTION_POINT = "информация о точках распространения и публикации";

    private final CAService caService;

    public CAController(CAService caService) {
        this.caService = caService;
    }

    @ApiOperation(value = "Получить информацию о текущем активном УЦ")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @GetMapping(value = "/get-active-ca")
    public ResponseEntity<CADTO> getActiveCA() {
        log.log(Level.INFO, "Вызов метода - getActiveCA");
        Set<CAInfo> set = caService.getAllCA();
        if (set.isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<CAInfo> caInfo = set.stream().filter(c -> c.getStatus() == 1).findFirst();
        if (!caInfo.isPresent()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CADTO caDto = new CADTO(caInfo.get());
        ResponseEntity<CADTO> response = new ResponseEntity<>(caDto, HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody().toString());
        return response;
    }

    @ApiOperation(value = "Получить информацию о всех существующих УЦ")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @GetMapping(value = "/get-all-ca")
    public ResponseEntity<Set<CADTO>> getAllCA() {
        log.log(Level.INFO, "Вызов метода - getAllCA");
        Set<CAInfo> set = caService.getAllCA();
        if (set.isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<CADTO> caDtos = new HashSet<>();
        set.forEach(caInfo -> caDtos.add(new CADTO(caInfo)));
        ResponseEntity<Set<CADTO>> response = new ResponseEntity<>(caDtos, HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody().toString());
        return response;
    }

    @ApiOperation(value = "Создать центр сертификации")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
    })
    @PostMapping(value = "/create-ca")
    public ResponseEntity<Integer> createCA(@ApiParam(value = CA_PARAM, required = true) @RequestBody @NonNull CreateCADTO createCADTO) {
        log.log(Level.INFO, "Вызов метода - createCA, c данными: " + createCADTO.toString());
        CAInfo caInfo = caService.createCA(createCADTO.getEncryptionType(), createCADTO.getType(), createCADTO.getName(),
                createCADTO.getSubjectDN(), createCADTO.getSubjectAltName(), createCADTO.getHash(), createCADTO.getKeyLength(),
                createCADTO.getValidity());
        if (caInfo == null) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<Integer> response = new ResponseEntity<>(caInfo.getId(), HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody());
        return response;
    }

    @ApiOperation(value = "Получение (скачивание) запроса на создание ЦС")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
    })
    @PutMapping(value = "/create-csr")
    public ResponseEntity<String> createCSR(@ApiParam(value = CA_ID, required = true) @RequestBody @NonNull IdDTO id) {
        log.log(Level.INFO, "Вызов метода - createCSR, c id: " + id.getId());
        String csr = caService.getFile(id.getId(), "csr");
        if (csr == null || csr.isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<String> response = new ResponseEntity<>(csr, HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody());
        return response;
    }

    @ApiOperation(value = "Активация выбранного ЦС")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
    })
    @PostMapping(value = "/activate-ca")
    public ResponseEntity<Void> activateCA(@ApiParam(value = CA_ID, required = true) @RequestBody @NonNull IdDTO id) {
        log.log(Level.INFO, "Вызов метода - activateCA, c id: " + id.getId());
        boolean isActivate = caService.activateCA(id.getId());
        if (!isActivate) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + HttpStatus.OK);
        return response;
    }

    @ApiOperation(value = "Скачивание (формирование) списка отзыва сертификатов для выбранного ЦС")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
    })
    @PostMapping(value = "/create-and-get-crl")
    public ResponseEntity<String> createAndGetCRL(@ApiParam(value = CA_ID, required = true) @RequestBody @NonNull IdDTO id) {
        log.log(Level.INFO, "Вызов метода - createAndGetCRL, c id: " + id.getId());
        String crl = caService.getFile(id.getId(), "crl");
        if (crl == null || crl.isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<String> response = new ResponseEntity<>(crl, HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody());
        return response;
    }

    @ApiOperation(value = "Получение (скачивание) сертификата для выбранного ЦС")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
    })
    @GetMapping(value = "/get-cert/{id}")
    public ResponseEntity<String> getCert(@ApiParam(value = CA_ID, required = true) @PathVariable @NonNull int id) {
        log.log(Level.INFO, "Вызов метода - getCert, c id: " + id);
        String crt = caService.getFile(id, "crt");
        if (crt == null || crt.isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<String> response = new ResponseEntity<>(crt, HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody());
        return response;
    }

    @ApiOperation(value = "Импорт сертификата для центра сертификации (при статусе не подписан)")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
    })
    @PutMapping(value = "/import-response")
    public ResponseEntity<Void> importResponse(@ApiParam(value = CA_ID_AND_RESPONSE_FILE, required = true) @RequestBody @NonNull CsrResponseDTO csrResponseDTO) {
        log.log(Level.INFO, "Вызов метода - importResponse, c данными: " + csrResponseDTO.toString());
        boolean isImportCorrectly = caService.importResponse(csrResponseDTO.getId(), csrResponseDTO.getFile());
        if (!isImportCorrectly) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode());
        return response;
    }

    @ApiOperation(value = "Загрузка текущих настроек точки распространения")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
    })
    @GetMapping(value = "/get-publication-point/{id}")
    public ResponseEntity<PublicationAndDistributionPointDTO> getPublicationPoint(@ApiParam(value = CA_ID, required = true) @PathVariable @NonNull int id) {
        log.log(Level.INFO, "Вызов метода - getPublicationPoint, c id: " + id);
        CAInfo caInfo = caService.getCA(id);
        if (caInfo == null) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PublicationAndDistributionPointDTO publicationPointDTO = new PublicationAndDistributionPointDTO(caInfo);
        ResponseEntity<PublicationAndDistributionPointDTO> response = new ResponseEntity<>(publicationPointDTO, HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody().toString());
        return response;
    }

    @ApiOperation(value = "Изменение и сохранение настроек точки распространения и публикации")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
    })
    @PutMapping(value = "/add-publication-point")
    public ResponseEntity<Void> addPublicationPoint(@ApiParam(value = CA_PUBLICATION_AND_DISTRIBUTION_POINT, required = true)
                                                    @RequestBody @NonNull PublicationAndDistributionPointDTO publicationAndDistributionPointDTO) {
        log.log(Level.INFO, "Вызов метода - addPublicationPoint, c данными: " + publicationAndDistributionPointDTO.toString());
        boolean isAdd = caService.editPublicationPoint(publicationAndDistributionPointDTO.getCaId(),
                publicationAndDistributionPointDTO.getAiaPublicationPoint(), publicationAndDistributionPointDTO.getAiaDistributionPoint(),
                publicationAndDistributionPointDTO.getCrlPublicationPoint(), publicationAndDistributionPointDTO.getCrlDistributionPoint());
        if (!isAdd) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode());
        return response;
    }

    @ApiOperation(value = "Публикация CRL по заданным параметрам")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
    })
    @PostMapping(value = "/publication-crl")
    public ResponseEntity<Void> publicationCRL(@ApiParam(value = CA_ID, required = true) @RequestBody @NonNull IdDTO id) {
        log.log(Level.INFO, "Вызов метода - publicationCRL, c id: " + id.getId());
        int isPublicationCompleted = caService.publication(id.getId(), "crl");
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.resolve(isPublicationCompleted));
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode());
        return response;
    }

    @ApiOperation(value = "Публикация AIA по заданным параметрам")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
    })
    @PostMapping(value = "/publication-aia")
    public ResponseEntity<Void> publicationAIA(@ApiParam(value = CA_ID, required = true) @RequestBody @NonNull IdDTO id) {
        log.log(Level.INFO, "Вызов метода - publicationAIA, c id: " + id.getId());
        int isPublicationCompleted = caService.publication(id.getId(), "aia");
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.resolve(isPublicationCompleted));
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode());
        return response;
    }


    @ApiOperation(value = "Получить сертификаты для Активного УЦ")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @GetMapping(value = "/all/{id}")
    public ResponseEntity<Set<CertificateDTO>> getCertificatesByActiveCA(@ApiParam(value = CA_ID, required = true) @PathVariable("id") @NonNull int id) {
        log.log(Level.INFO, "Вызов метода - getCertificatesByActiveCA, c id: " + id);
        //todo добавить в дто владельца и издателя, так как это поля есть в WebUI
        Set<CertificateDTO> result = caService.getAllCertificateByActiveCA(id).stream()
                .map(certificate -> new CertificateDTO(certificate, LocalDateTime.now(), LocalDateTime.now().plusYears(5), "SHA256RSA", "2048"))
                .collect(Collectors.toSet());
        if (result.isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ResponseEntity<Set<CertificateDTO>> response = new ResponseEntity<>(result, HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody().toString());
        return response;
    }
}