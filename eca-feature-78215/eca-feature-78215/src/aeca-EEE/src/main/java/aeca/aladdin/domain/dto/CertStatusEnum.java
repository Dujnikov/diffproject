package aeca.aladdin.domain.dto;

public enum CertStatusEnum {

    CERT_ACTIVE(1),
    CERT_UNASSIGNED(2),
    CERT_INACTIVE(3),
    CERT_ROLLOVERPENDING(4),
    CERT_NOTIFIEDABOUTEXPIRATION(5),
    CERT_REVOKED(6),
    CERT_ARCHIVED(7),
    CERT_WAIT_RESPONSE(8);

    private final int code;

    CertStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CertStatusEnum getStatus(int status) {
        switch (status) {
            case (1):
                return CertStatusEnum.CERT_ACTIVE;
            case (2):
                return CertStatusEnum.CERT_UNASSIGNED;
            case (3):
                return CertStatusEnum.CERT_INACTIVE;
            case (4):
                return CertStatusEnum.CERT_ROLLOVERPENDING;
            case (5):
                return CertStatusEnum.CERT_NOTIFIEDABOUTEXPIRATION;
            case (6):
                return CertStatusEnum.CERT_REVOKED;
            case (7):
                return CertStatusEnum.CERT_ARCHIVED;
            case (8):
                return CertStatusEnum.CERT_WAIT_RESPONSE;
            default:
                return null;
        }
    }
}