package aeca.aladdin.domain.entity;

import java.util.Set;

public class Template {

    private int id;

    private String name;

    private Set<Group> groups;

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

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Template() {
    }

    public Template(int id, String name, Set<Group> groups) {
        this.id = id;
        this.name = name;
        this.groups = groups;
    }
}
