package aeca.aladdin.domain.dto;

import java.util.Set;

public class AuthSubjectWOIdDTO {

    private String subjectDN;

    private String subjectAltName;

    private Set<Integer> groupIds;

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

    public Set<Integer> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(Set<Integer> groupIds) {
        this.groupIds = groupIds;
    }

    @Override
    public String toString() {
        return "AuthSubjectWOIdDTO{" +
                "subjectDN='" + subjectDN + '\'' +
                ", subjectAltName='" + subjectAltName + '\'' +
                ", groupIds=" + groupIds +
                '}';
    }
}