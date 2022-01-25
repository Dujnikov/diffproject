package aeca.aladdin.domain.dto;

public class RevocationDTO {

    private String certSerialNumber;

    private RevocationReasonEnum revocationReason;

    public String getCertSerialNumber() {
        return certSerialNumber;
    }

    public void setCertSerialNumber(String certSerialNumber) {
        this.certSerialNumber = certSerialNumber;
    }

    public RevocationReasonEnum getRevocationReason() {
        return revocationReason;
    }

    public void setRevocationReason(RevocationReasonEnum revocationReason) {
        this.revocationReason = revocationReason;
    }

    @Override
    public String toString() {
        return "RevocationDTO{" +
                "certSerialNumber=" + certSerialNumber +
                ", revocationReasonEnum='" + revocationReason +
                '}';
    }
}