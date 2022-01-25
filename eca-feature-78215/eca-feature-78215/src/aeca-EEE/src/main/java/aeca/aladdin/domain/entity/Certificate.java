package aeca.aladdin.domain.entity;

public class Certificate {

    private int id;

    private String name;

    private String serialNumber;

    private String fingerPrint;

    private Template template;

    private AuthSubject subject;

    private int type;

    private int status;
//    CERT_ACTIVE(1),
//    CERT_UNASSIGNED(2),
//    CERT_INACTIVE(3),
//    CERT_ROLLOVERPENDING(4),
//    CERT_NOTIFIEDABOUTEXPIRATION(5),
//    CERT_REVOKED(6),
//    CERT_ARCHIVED(7),
//    CERT_WAIT_RESPONSE(8);

    private int revocationReason;
//    CERTIFICATE_ACTIVE(-1),
//    CERTIFICATE_HOLD(1),
//    UNSPECIFIED(2),
//    KEY_COMPROMISE(3),
//    CA_COMPROMISE(4),
//    AFFILIATION_CHANGED(5),
//    SUPERSEDED(6),
//    CESSATION_OF_OPERATION(7),
//    PRIVILEGES_WITH_DRAWN(8),
//    AA_COMPROMISE(9);

    private String certBase64;

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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public AuthSubject getSubject() {
        return subject;
    }

    public void setSubject(AuthSubject subject) {
        this.subject = subject;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRevocationReason() {
        return revocationReason;
    }

    public void setRevocationReason(int revocationReason) {
        this.revocationReason = revocationReason;
    }

    public String getCertBase64() {
        return certBase64;
    }

    public void setCertBase64(String certBase64) {
        this.certBase64 = certBase64;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Certificate() {
    }

    public Certificate(int id, String name, String serialNumber, String fingerPrint, Template template, AuthSubject subject, int status, int revocationReason, int type) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.fingerPrint = fingerPrint;
        this.template = template;
        this.subject = subject;
        this.status = status;
        this.revocationReason = revocationReason;
        this.type = type;
    }
}