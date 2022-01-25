package aeca.aladdin.domain.dto;

public class EnrollCertForCsrDTO {

    private String file;

    private int authSubjectId;

    private int templateId;

    private String certFormat;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getAuthSubjectId() {
        return authSubjectId;
    }

    public void setAuthSubjectId(int authSubjectId) {
        this.authSubjectId = authSubjectId;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getCertFormat() {
        return certFormat;
    }

    public void setCertFormat(String certFormat) {
        this.certFormat = certFormat;
    }

    @Override
    public String toString() {
        return "EnrollCertForCsrDTO{" +
                "file='" + file + '\'' +
                ", authSubjectId=" + authSubjectId +
                ", templateId=" + templateId +
                ", certFormat='" + certFormat + '\'' +
                '}';
    }
}