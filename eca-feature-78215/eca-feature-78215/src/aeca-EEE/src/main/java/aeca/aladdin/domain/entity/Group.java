package aeca.aladdin.domain.entity;

import java.util.Set;

public class Group {

    private int id;

    private String name;

    private Set<Template> templates;

    private Set<AuthSubject> subjects;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }

    public Set<AuthSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<AuthSubject> subjects) {
        this.subjects = subjects;
    }

    public Group() {
    }

    public Group(int id, String name, Set<Template> templates, Set<AuthSubject> subjects) {
        this.id = id;
        this.name = name;
        this.templates = templates;
        this.subjects = subjects;
    }
}