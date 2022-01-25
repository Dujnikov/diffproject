package aeca.aladdin.controller;

import aeca.aladdin.domain.dto.*;
import aeca.aladdin.domain.entity.Certificate;
import aeca.aladdin.service.CertificateService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/certificates")
@Api(tags = "Certificate API")
@CrossOrigin(origins = "${CrossOrigin}")
public class CertificateController {

    private static Logger log = Logger.getLogger(CertificateController.class.getName());

    private final static String CERT_ID = "Серийный номер сертификата";
    private final static String ENROLL_CERT = "Файл запрос в формате Base64, id сущности аутентификации, id шаблона сертификата АЕСА, формат сертификата(опционально)";
    private final static String ENROLL_AND_CREATE_CERT = "Файл запрос в формате Base64, id группы аутентификации, id шаблона сертификата АЕСА, формат сертификата(опционально)";
    private final static String CERT_AND_REASON = "Серийный номер сертификата и причина отзыва";

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @ApiOperation(value = "Получить состояние сертификата")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @GetMapping(value = "/status/{certSerialNumber}")
    public ResponseEntity<CertStatusEnum> getCertStatus(@ApiParam(value = CERT_ID, required = true) @PathVariable("certSerialNumber") @NonNull String certSerialNumber) {
        log.log(Level.INFO, "Вызов метода - getCertStatus, c certSerialNumber: " + certSerialNumber);
        Certificate certificate = certificateService.getCertificate(certSerialNumber);
        if (certificate == null) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        int certStatus = certificate.getStatus();
        ResponseEntity<CertStatusEnum> response;
        switch (certStatus) {
            case (1):
                response = new ResponseEntity<>(CertStatusEnum.CERT_ACTIVE, HttpStatus.OK);
                break;
            case (2):
                response = new ResponseEntity<>(CertStatusEnum.CERT_UNASSIGNED, HttpStatus.OK);
                break;
            case (3):
                response = new ResponseEntity<>(CertStatusEnum.CERT_INACTIVE, HttpStatus.OK);
                break;
            case (4):
                response = new ResponseEntity<>(CertStatusEnum.CERT_ROLLOVERPENDING, HttpStatus.OK);
                break;
            case (5):
                response = new ResponseEntity<>(CertStatusEnum.CERT_NOTIFIEDABOUTEXPIRATION, HttpStatus.OK);
                break;
            case (6):
                response = new ResponseEntity<>(CertStatusEnum.CERT_REVOKED, HttpStatus.OK);
                break;
            case (7):
                response = new ResponseEntity<>(CertStatusEnum.CERT_ARCHIVED, HttpStatus.OK);
                break;
            default:
                log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", статус сертификата " + response.getBody());
        return response;
    }

    @ApiOperation(value = "Отозвать сертификат")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @PutMapping(value = "/revoke")
    public ResponseEntity<Void> postRevokeCertificate(@ApiParam(value = CERT_AND_REASON, required = true) @RequestBody @NonNull RevocationDTO revocationDTO) {
        log.log(Level.INFO, "Вызов метода - postRevokeCertificate, c данными: " + revocationDTO.toString());
        if (revocationDTO.getRevocationReason() == null) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean result = certificateService.certificateRevoke(revocationDTO.getCertSerialNumber(), revocationDTO.getRevocationReason().getCode());
        if (!result) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.log(Level.INFO, "Статус ответа: " + HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Приостановить действие сертификата")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @PutMapping(value = "/suspend")
    public ResponseEntity<CertSerialNumberDTO> suspendCertificate(@ApiParam(value = CERT_ID, required = true) @RequestBody @NonNull CertSerialNumberDTO certSerialNumberDTO) {
        log.log(Level.INFO, "Вызов метода - suspendCertificate, c данными: " + certSerialNumberDTO.toString());
        boolean result = certificateService.certificateRevoke(certSerialNumberDTO.getCertSerialNumber(), 6);
        if (!result) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.log(Level.INFO, "Статус ответа: " + HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Возобновить действие сертификата")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @PutMapping(value = "/reactivate")
    public ResponseEntity<Void> reactivateCertificate(@ApiParam(value = CERT_ID, required = true) @RequestBody @NonNull CertSerialNumberDTO certSerialNumberDTO) {
        log.log(Level.INFO, "Вызов метода - reactivateCertificate, c данными: " + certSerialNumberDTO.toString());
        boolean result = certificateService.certificateReactivate(certSerialNumberDTO.getCertSerialNumber());
        if (!result) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.log(Level.INFO, "Статус ответа: " + HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Получить сертификат по CSR для заданного субъекта аутентификации")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @PostMapping(value = "/enroll")
    public ResponseEntity<String> enrolCertificate(@ApiParam(value = ENROLL_CERT, required = true) @RequestBody @NonNull EnrollCertForCsrDTO enrollCertForCsrDTO) {
        log.log(Level.INFO, "Вызов метода - enrolCertificate, c данными: " + enrollCertForCsrDTO.toString());
        Certificate certificate = certificateService.createCertToCsr(enrollCertForCsrDTO.getCertFormat(),
                enrollCertForCsrDTO.getTemplateId(), enrollCertForCsrDTO.getAuthSubjectId(), enrollCertForCsrDTO.getFile());
        if (certificate == null || certificate.getCertBase64() == null || certificate.getCertBase64().isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.log(Level.INFO, "Статус ответа: " + HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(certificate.getCertBase64(), HttpStatus.OK);
    }

    @ApiOperation(value = "Получить сертификат по CSR для нового субъекта аутентификации")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @PostMapping(value = "/create-and-enroll")
    public ResponseEntity<CsrResponseDTO> createAndEnrollCertificate(@ApiParam(value = ENROLL_AND_CREATE_CERT, required = true) @RequestBody @NonNull CreateAndEnrollCertForCsrDTO enrollCertForCsrDTO) {
        log.log(Level.INFO, "Вызов метода - createAndEnrollCertificate, c данными: " + enrollCertForCsrDTO.toString());
        Certificate certificate = certificateService.createSubjectAndCertToCsr(enrollCertForCsrDTO.getCertFormat(),
                enrollCertForCsrDTO.getTemplateId(), enrollCertForCsrDTO.getGroupId(), enrollCertForCsrDTO.getFile());
        if (certificate == null || certificate.getCertBase64() == null || certificate.getCertBase64().isEmpty()) {
            log.log(Level.WARNING, "Статус ответа: " + HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<CsrResponseDTO> response = new ResponseEntity<>(new CsrResponseDTO(certificate.getCertBase64(), certificate.getSubject().getId()), HttpStatus.OK);
        log.log(Level.INFO, "Статус ответа: " + response.getStatusCode() + ", данные: " + response.getBody().toString());
        return response;
    }
}