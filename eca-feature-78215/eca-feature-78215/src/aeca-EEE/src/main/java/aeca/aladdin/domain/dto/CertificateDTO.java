package aeca.aladdin.domain.dto;

import aeca.aladdin.domain.entity.Certificate;

import java.time.LocalDateTime;

public class CertificateDTO {

    //todo добавить в дто владельца и издателя

    private int id;

    private String name;

    private String serialNumber;

    private String fingerPrint;

    private CertTypeEnum certType;

    private CertStatusEnum status;

    private LocalDateTime begin;

    private LocalDateTime end;

    private String algorithmKey;

    private String longKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public CertTypeEnum getCertType() {
        return certType;
    }

    public void setCertType(CertTypeEnum certType) {
        this.certType = certType;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public void setBegin(LocalDateTime begin) {
        this.begin = begin;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getAlgorithmKey() {
        return algorithmKey;
    }

    public void setAlgorithmKey(String algorithmKey) {
        this.algorithmKey = algorithmKey;
    }

    public String getLongKey() {
        return longKey;
    }

    public void setLongKey(String longKey) {
        this.longKey = longKey;
    }

    public CertStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CertStatusEnum status) {
        this.status = status;
    }

    public CertificateDTO() {
    }

    //требуется редакция после переработки бд
    public CertificateDTO(Certificate certificate, LocalDateTime begin, LocalDateTime end, String algorithmKey, String longKey) {
        this.id = certificate.getId();
        this.name = certificate.getName();
        this.serialNumber = certificate.getSerialNumber();
        this.fingerPrint = certificate.getFingerPrint();
        this.certType = CertTypeEnum.getType(certificate.getType());
        this.status = CertStatusEnum.getStatus(certificate.getStatus());
        this.begin = begin;
        this.end = end;
        this.algorithmKey = algorithmKey;
        this.longKey = longKey;
    }

    @Override
    public String toString() {
        return "CertificateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", fingerPrint='" + fingerPrint + '\'' +
                ", certType=" + certType +
                ", status=" + status +
                ", begin=" + begin +
                ", end=" + end +
                ", algorithmKey='" + algorithmKey + '\'' +
                ", longKey='" + longKey + '\'' +
                '}';
    }
}