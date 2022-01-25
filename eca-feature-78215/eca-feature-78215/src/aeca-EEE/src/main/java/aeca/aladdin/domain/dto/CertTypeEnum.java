package aeca.aladdin.domain.dto;

public enum CertTypeEnum {

    TYPE_CA(1);

    private final int code;

    CertTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CertTypeEnum getType(int code) {
        switch (code) {
            case (1):
                return CertTypeEnum.TYPE_CA;
            default:
                return null;
        }
    }
}