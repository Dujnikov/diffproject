package aeca.aladdin.domain.dto;

public class IdAndStatusCertDTO {

    private String serialNumber;

    private CertStatusEnum status;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public CertStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CertStatusEnum status) {
        this.status = status;
    }

    public IdAndStatusCertDTO() {
    }

    public IdAndStatusCertDTO(String serialNumber, CertStatusEnum status) {
        this.serialNumber = serialNumber;
        this.status = status;
    }

    @Override
    public String toString() {
        return "{IdAndStatusCertDTO"
                + ", serialNumber=" + serialNumber
                + ", status=" + status
                + "}";
    }
}