package aeca.aladdin.domain.dto;

public class IdSubjectAndIdGroup {

    private int subjectId;

    private int groupId;

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "IdSubjectAndIdGroup{" +
                "subjectId=" + subjectId +
                ", groupId=" + groupId +
                '}';
    }
}