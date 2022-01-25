package aeca.aladdin.domain.dto;

public class AuthSubjectWOGroupDTO {

    private int id;

    private String subjectDN;

    private String subjectAltName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public AuthSubjectWOGroupDTO() {
    }

    public AuthSubjectWOGroupDTO(int id, String subjectDN, String subjectAltName) {
        this.id = id;
        this.subjectDN = subjectDN;
        this.subjectAltName = subjectAltName;
    }

    @Override
    public String toString() {
        return "AuthSubjectWOGroupDTO{" +
                "id=" + id +
                ", subjectDN='" + subjectDN + '\'' +
                ", subjectAltName='" + subjectAltName + '\'' +
                '}';
    }
}