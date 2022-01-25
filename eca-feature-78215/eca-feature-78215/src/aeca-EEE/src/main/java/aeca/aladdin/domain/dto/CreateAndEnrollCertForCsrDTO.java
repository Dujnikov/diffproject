package aeca.aladdin.domain.dto;

public class CreateAndEnrollCertForCsrDTO {

    private String file;

    private int groupId;

    private int templateId;

    private String certFormat;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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
        return "CreateAndEnrollCertForCsrDTO{" +
                "file='" + file + '\'' +
                ", groupId=" + groupId +
                ", templateId=" + templateId +
                ", certFormat='" + certFormat + '\'' +
                '}';
    }
}