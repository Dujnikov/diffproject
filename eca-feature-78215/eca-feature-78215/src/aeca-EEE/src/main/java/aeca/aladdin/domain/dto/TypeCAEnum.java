package aeca.aladdin.domain.dto;

public enum TypeCAEnum {

    ROOTCA(1),
    SUBCA(2);

    private final int code;

    TypeCAEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TypeCAEnum getType(int code) {
        if (code == 1) {
            return TypeCAEnum.ROOTCA;
        }
        if (code == 2) {
            return TypeCAEnum.SUBCA;
        } else {
            return null;
        }
    }
}