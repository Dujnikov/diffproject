package aeca.aladdin.domain.dto;

import java.util.Set;

public class GroupDTO {

    private int id;

    private String name;

    private Set<Integer> templateIds;

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

    public Set<Integer> getTemplateIds() {
        return templateIds;
    }

    public void setTemplateIds(Set<Integer> templateIds) {
        this.templateIds = templateIds;
    }

    public GroupDTO() {
    }

    public GroupDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public GroupDTO(int id, String name, Set<Integer> templateIds) {
        this.id = id;
        this.name = name;
        this.templateIds = templateIds;
    }

    public String toString() {
        return "GroupImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", certificateTemplateIds=" + templateIds +
                '}';
    }
}