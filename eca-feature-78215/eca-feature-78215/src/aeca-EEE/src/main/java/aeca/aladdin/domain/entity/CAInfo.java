package aeca.aladdin.domain.entity;

public class CAInfo {

    private int id;

    private String name;

    private String subjectDN;

    private String subjectAltName;

    private int status;
//    ACTIVE(1),
//    INACTIVE(2);

    private int type;
//    ROOTCA(1),
//    SUBCA(2);

    private Long validity;

    private String encryptionType;

    private String hash;

    private Integer keyLength;

    private Long publicationPeriod;

    private String crlPublicationPoint;

    private String aiaPublicationPoint;

    private String crlDistributionPoint;

    private String aiaDistributionPoint;

    private Long lastPublication;

    private Long nextPublication;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getValidity() {
        return validity;
    }

    public void setValidity(Long validity) {
        this.validity = validity;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getKeyLength() {
        return keyLength;
    }

    public void setKeyLength(Integer keyLength) {
        this.keyLength = keyLength;
    }

    public Long getPublicationPeriod() {
        return publicationPeriod;
    }

    public void setPublicationPeriod(Long publicationPeriod) {
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

    public Long getLastPublication() {
        return lastPublication;
    }

    public void setLastPublication(Long lastPublication) {
        this.lastPublication = lastPublication;
    }

    public Long getNextPublication() {
        return nextPublication;
    }

    public void setNextPublication(Long nextPublication) {
        this.nextPublication = nextPublication;
    }

    public CAInfo() {
    }

    public CAInfo(int id, String name, String subjectDN, String subjectAltName, int status, int type,
                  Long validity, String encryptionType, String hash, Integer keyLength, Long publicationPeriod,
                  String crlPublicationPoint, String aiaPublicationPoint, String crlDistributionPoint,
                  String aiaDistributionPoint, Long lastPublication, Long nextPublication) {
        this.id = id;
        this.name = name;
        this.subjectDN = subjectDN;
        this.subjectAltName = subjectAltName;
        this.status = status;
        this.type = type;
        this.validity = validity;
        this.encryptionType = encryptionType;
        this.hash = hash;
        this.keyLength = keyLength;
        this.publicationPeriod = publicationPeriod;
        this.crlPublicationPoint = crlPublicationPoint;
        this.aiaPublicationPoint = aiaPublicationPoint;
        this.crlDistributionPoint = crlDistributionPoint;
        this.aiaDistributionPoint = aiaDistributionPoint;
        this.lastPublication = lastPublication;
        this.nextPublication = nextPublication;
    }

    //    public CAInfo(int id, String name, int status, int type, Long validity) {
//        this.id = id;
//        this.name = name;
//        this.status = status;
//        this.type = type;
//        this.validity = validity;
//    }
}
