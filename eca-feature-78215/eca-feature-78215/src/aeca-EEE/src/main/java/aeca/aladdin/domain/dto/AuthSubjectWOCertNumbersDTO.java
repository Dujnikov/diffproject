package aeca.aladdin.domain.dto;

import java.util.Set;

public class AuthSubjectWOCertNumbersDTO {

    private int id;

    private String subjectDN;

    private String subjectAltName;

    private Set<Integer> templateIds;

    private Set<Integer> groupsId;

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

    public Set<Integer> getTemplateIds() {
        return templateIds;
    }

    public void setTemplateIds(Set<Integer> templateIds) {
        this.templateIds = templateIds;
    }

    public Set<Integer> getGroupsId() {
        return groupsId;
    }

    public void setGroupsId(Set<Integer> groupsId) {
        this.groupsId = groupsId;
    }

    public AuthSubjectWOCertNumbersDTO(int id, String subjectDN, String subjectAltName, Set<Integer> templateIds, Set<Integer> groupsId) {
        this.id = id;
        this.subjectDN = subjectDN;
        this.subjectAltName = subjectAltName;
        this.templateIds = templateIds;
        this.groupsId = groupsId;
    }

    public AuthSubjectWOCertNumbersDTO(int id, String subjectDN, String subjectAltName) {
        this.id = id;
        this.subjectDN = subjectDN;
        this.subjectAltName = subjectAltName;
    }

    @Override
    public String toString() {
        return "AuthSubjectWOCertNumbersDTO{"
                + "id=" + id
                + ", subjectDN=" + subjectDN
                + ", subjectAltName=" + subjectAltName
                + ", templateIds=" + templateIds
                + ", groupsId=" + groupsId
                + '}';
    }
}