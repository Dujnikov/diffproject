package aeca.aladdin.domain.dto;

public enum StatusCAEnum {

    ACTIVE(1),
    INACTIVE(2),
    WAIT_RESPONSE(3);

    private final int code;

    StatusCAEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static StatusCAEnum getStatus(int code) {
        if (code == 1) {
            return StatusCAEnum.ACTIVE;
        }
        if (code == 2) {
            return StatusCAEnum.INACTIVE;
        }
        if (code == 3) {
            return StatusCAEnum.WAIT_RESPONSE;
        } else {
            return null;
        }
    }
}