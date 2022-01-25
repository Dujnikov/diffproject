package aeca.aladdin.domain.dto;

import aeca.aladdin.domain.entity.CAInfo;

public class PublicationAndDistributionPointDTO {

    private int caId;

    private Long publicationPeriod;

    private String crlPublicationPoint;

    private String aiaPublicationPoint;

    private String crlDistributionPoint;

    private String aiaDistributionPoint;

    private Long lastPublication;

    private Long nextPublication;

    public int getCaId() {
        return caId;
    }

    public void setCaId(int caId) {
        this.caId = caId;
    }

    public long getPublicationPeriod() {
        return publicationPeriod;
    }

    public void setPublicationPeriod(long publicationPeriod) {
        this.publicationPeriod = publicationPeriod;
    }

    public String getCrlPublicationPoint() {
        return crlPublicationPoint;
    }

    public void setCrlPublicationPoint(String crlPublicationPoint) {
        this.crlPublicationPoint = crlPublicationPoint;
    }

    public String getAiaPublicationPoint() {
        return aiaPublicationPoint;
    }

    public void setAiaPublicationPoint(String aiaPublicationPoint) {
        this.aiaPublicationPoint = aiaPublicationPoint;
    }

    public String getCrlDistributionPoint() {
        return crlDistributionPoint;
    }

    public void setCrlDistributionPoint(String crlDistributionPoint) {
        this.crlDistributionPoint = crlDistributionPoint;
    }

    public String getAiaDistributionPoint() {
        return aiaDistributionPoint;
    }

    public void setAiaDistributionPoint(String aiaDistributionPoint) {
        this.aiaDistributionPoint = aiaDistributionPoint;
    }

    public PublicationAndDistributionPointDTO() {
    }

    public PublicationAndDistributionPointDTO(CAInfo caInfo) {
        this.caId = caInfo.getId();
        this.publicationPeriod = caInfo.getPublicationPeriod();
        this.crlPublicationPoint = caInfo.getCrlPublicationPoint();
        this.aiaPublicationPoint = caInfo.getAiaPublicationPoint();
        this.crlDistributionPoint = caInfo.getCrlDistributionPoint();
        this.aiaDistributionPoint = caInfo.getAiaDistributionPoint();
        this.lastPublication = caInfo.getLastPublication();
        this.nextPublication = caInfo.getNextPublication();
    }

    @Override
    public String toString() {
        return "PublicationAndDistributionPointDTO{" +
                "caId=" + caId +
                ", publicationPeriod=" + publicationPeriod +
                ", crlPublicationPoint='" + crlPublicationPoint + '\'' +
                ", aiaPublicationPoint='" + aiaPublicationPoint + '\'' +
                ", crlDistributionPoint='" + crlDistributionPoint + '\'' +
                ", aiaDistributionPoint='" + aiaDistributionPoint + '\'' +
                ", lastPublication=" + lastPublication +
                ", nextPublication=" + nextPublication +
                '}';
    }
}