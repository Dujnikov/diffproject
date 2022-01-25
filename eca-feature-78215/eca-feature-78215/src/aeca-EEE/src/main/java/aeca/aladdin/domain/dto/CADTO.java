package aeca.aladdin.domain.dto;

import aeca.aladdin.domain.entity.CAInfo;

public class CADTO {

    private int id;

    private String name;

    private StatusCAEnum status;

    private TypeCAEnum type;

    private Long validity;

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

    public StatusCAEnum getStatus() {
        return status;
    }

    public void setStatus(StatusCAEnum status) {
        this.status = status;
    }

    public TypeCAEnum getType() {
        return type;
    }

    public void setType(TypeCAEnum type) {
        this.type = type;
    }

    public Long getValidity() {
        return validity;
    }

    public void setValidity(Long validity) {
        this.validity = validity;
    }

    public CADTO() {
    }

    public CADTO(CAInfo caInfo) {
        this.id = caInfo.getId();
        this.name = caInfo.getName();
        this.status = StatusCAEnum.getStatus(caInfo.getStatus());
        this.type = TypeCAEnum.getType(caInfo.getType());
        this.validity = caInfo.getValidity();
    }

    @Override
    public String toString() {
        return "CADTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", validity=" + validity +
                '}';
    }
}