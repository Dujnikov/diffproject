package aeca.aladdin.domain.dto;

import java.util.Set;

public class IdSubjectAndSetGroupIds {

    private int subjectId;

    private Set<Integer> groupIds;

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public Set<Integer> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(Set<Integer> groupIds) {
        this.groupIds = groupIds;
    }

    @Override
    public String toString() {
        return "IdSubjectAndSetGroupIds{" +
                "subjectId=" + subjectId +
                ", groupIds=" + groupIds +
                '}';
    }
}