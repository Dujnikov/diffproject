package aeca.aladdin.domain.dto;

public enum RevocationReasonEnum {

    CERTIFICATE_ACTIVE(-1),
    CERTIFICATE_HOLD(1),
    UNSPECIFIED(2),
    KEY_COMPROMISE(3),
    CA_COMPROMISE(4),
    AFFILIATION_CHANGED(5),
    SUPERSEDED(6),
    CESSATION_OF_OPERATION(7),
    PRIVILEGES_WITH_DRAWN(8),
    AA_COMPROMISE(9);

    private final int code;

    RevocationReasonEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}