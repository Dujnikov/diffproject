package aeca.aladdin.domain.entity;

import java.util.Set;

public class AuthSubject {

    private int id;

    private String subjectDN;

    private String subjectAltName;

    private Set<Template> templates;

    private Set<Group> groups;

    private Set<Certificate> certificates;

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

    public Set<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(Set<Certificate> certificates) {
        this.certificates = certificates;
    }

    public AuthSubject() {
    }

    public AuthSubject(int id, String subjectDN, String subjectAltName, Set<Template> templates, Set<Group> groups, Set<Certificate> certificates) {
        this.id = id;
        this.subjectDN = subjectDN;
        this.subjectAltName = subjectAltName;
        this.templates = templates;
        this.groups = groups;
        this.certificates = certificates;
    }
}