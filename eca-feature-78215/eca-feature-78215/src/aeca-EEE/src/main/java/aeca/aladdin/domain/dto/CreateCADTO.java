package aeca.aladdin.domain.dto;

public class CreateCADTO {

    private TypeCAEnum type;

    private String name;

    private String subjectDN;

    private String subjectAltName;

    private Long validity;

    private String encryptionType;

    private String hash;

    private Integer keyLength;

    public TypeCAEnum getType() {
        return type;
    }

    public void setType(TypeCAEnum type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectDN() {
        return subjectDN;
    }

    public void setSubjectDN(String subjectDN) {
        this.subjectDN = subjectDN;
    }

    public String getSubjectAltName() {
        return subjectAltName;
    }

    public void setSubjectAltName(String subjectAltName) {
        this.subjectAltName = subjectAltName;
    }

    public Long getValidity() {
        return validity;
    }

    public void setValidity(Long validity) {
        this.validity = validity;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getKeyLength() {
        return keyLength;
    }

    public void setKeyLength(Integer keyLength) {
        this.keyLength = keyLength;
    }

    @Override
    public String toString() {
        return "CreateCADTO{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", subjectDN='" + subjectDN + '\'' +
                ", subjectAltName='" + subjectAltName + '\'' +
                ", validity=" + validity +
                ", encryptionType='" + encryptionType + '\'' +
                ", hash='" + hash + '\'' +
                ", keyLength=" + keyLength +
                '}';
    }
}