package aeca.aladdin.domain.dto;

public class CertSerialNumberDTO {

    private String certSerialNumber;

    public String getCertSerialNumber() {
        return certSerialNumber;
    }

    public void setCertSerialNumber(String certSerialNumber) {
        this.certSerialNumber = certSerialNumber;
    }

    @Override
    public String toString() {
        return "CertSerialNumberDTO{" +
                "certSerialNumber=" + certSerialNumber +
                '}';
    }
}