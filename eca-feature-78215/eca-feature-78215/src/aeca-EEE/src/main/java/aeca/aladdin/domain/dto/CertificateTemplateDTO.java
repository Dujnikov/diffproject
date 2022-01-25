package aeca.aladdin.domain.dto;

import java.util.List;
import java.util.Map;

public class CertificateTemplateDTO {

    private int id;

    private int certificateProfileId;

    private String name;

    private boolean useSubjectDnSubset;

    private List<Integer> subjectDnSubset;

    private boolean useBasicConstraints;

    private boolean basicConstraintsCritical;

    private boolean useAuthorityKeyIdentifier;

    private boolean authorityKeyIdentifierCritical;

    private boolean useSubjectKeyIdentifier;

    private boolean subjectKeyIdentifierCritical;

    private boolean useQcetSignatureDevice;

    private boolean useCertificatePolicies;

    private boolean certificatePoliciesCritical;

    private List<String> certificatePolicies;

    private boolean useSubjectAlternativeName;

    private boolean subjectAlternativeNameCritical;

    private boolean useIssuerAlternativeName;

    private boolean issuerAlternativeNameCritical;

    private boolean useSubjectDirAttributes;

    private boolean useNameConstraints;

    private boolean useCrlDistributionPoint;

    private boolean useDefaultCrlDistributionPoint;

    private boolean crlDistributionPointCritical;

    private String crlDistributionPointUri;

    private String crlIssuer;

    private boolean useFreshestCrl;

    private boolean useCaDefinedFreshestCrl;

    private String freshestCrlUri;

    private boolean useAuthorityInformationAccess;

    private boolean useDefaultOcspServiceLocator;

    private String ocspServiceLocatorUri;

    private List<String> caIssuers;

    private boolean useDefaultCaIssuer;

    private boolean usePrivKeyUsagePeriodNotBefore;

    private boolean usePrivKeyUsagePeriod;

    private boolean usePrivKeyUsagePeriodNotAfter;

    private long privKeyUsagePeriodStartOffset;

    private long privKeyUsagePeriodLengthInSeconds;

    private boolean useExtendedKeyUsage;

    private List<String> extendedKeyUsage;

    private List<Integer> availableCA;

    //далее поля EndEntityProfile

    private int endEntityProfileId;

    private String profileName;

//    private Serializable data;

    private int rowVersion = 0;

    private String rowProtection;

    private List<Integer> endEntityAvailableCA;

    private int defaultCA;

    private List<String> availableCertificateProfile;

    private String numberOfAllowedRequestsString;

    private int numberOfAllowedRequestsInt;

    private Map<String, Integer> dnsName;

    private Map<String, Integer> GUID;

    private Map<String, Integer> RFC822Name;

    private Map<String, Integer> msUPN;

//    private Set<AuthSubjectLocal> authSubjectLocals;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCertificateProfileId() {
        return certificateProfileId;
    }

    public void setCertificateProfileId(int certificateProfileId) {
        this.certificateProfileId = certificateProfileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUseSubjectDnSubset() {
        return useSubjectDnSubset;
    }

    public void setUseSubjectDnSubset(boolean useSubjectDnSubset) {
        this.useSubjectDnSubset = useSubjectDnSubset;
    }

    public List<Integer> getSubjectDnSubset() {
        return subjectDnSubset;
    }

    public void setSubjectDnSubset(List<Integer> subjectDnSubset) {
        this.subjectDnSubset = subjectDnSubset;
    }

    public boolean isUseBasicConstraints() {
        return useBasicConstraints;
    }

    public void setUseBasicConstraints(boolean useBasicConstraints) {
        this.useBasicConstraints = useBasicConstraints;
    }

    public boolean isBasicConstraintsCritical() {
        return basicConstraintsCritical;
    }

    public void setBasicConstraintsCritical(boolean basicConstraintsCritical) {
        this.basicConstraintsCritical = basicConstraintsCritical;
    }

    public boolean isUseAuthorityKeyIdentifier() {
        return useAuthorityKeyIdentifier;
    }

    public void setUseAuthorityKeyIdentifier(boolean useAuthorityKeyIdentifier) {
        this.useAuthorityKeyIdentifier = useAuthorityKeyIdentifier;
    }

    public boolean isAuthorityKeyIdentifierCritical() {
        return authorityKeyIdentifierCritical;
    }

    public void setAuthorityKeyIdentifierCritical(boolean authorityKeyIdentifierCritical) {
        this.authorityKeyIdentifierCritical = authorityKeyIdentifierCritical;
    }

    public boolean isUseSubjectKeyIdentifier() {
        return useSubjectKeyIdentifier;
    }

    public void setUseSubjectKeyIdentifier(boolean useSubjectKeyIdentifier) {
        this.useSubjectKeyIdentifier = useSubjectKeyIdentifier;
    }

    public boolean isSubjectKeyIdentifierCritical() {
        return subjectKeyIdentifierCritical;
    }

    public void setSubjectKeyIdentifierCritical(boolean subjectKeyIdentifierCritical) {
        this.subjectKeyIdentifierCritical = subjectKeyIdentifierCritical;
    }

    public boolean isUseQcetSignatureDevice() {
        return useQcetSignatureDevice;
    }

    public void setUseQcetSignatureDevice(boolean useQcetSignatureDevice) {
        this.useQcetSignatureDevice = useQcetSignatureDevice;
    }

    public boolean isUseCertificatePolicies() {
        return useCertificatePolicies;
    }

    public void setUseCertificatePolicies(boolean useCertificatePolicies) {
        this.useCertificatePolicies = useCertificatePolicies;
    }

    public boolean isCertificatePoliciesCritical() {
        return certificatePoliciesCritical;
    }

    public void setCertificatePoliciesCritical(boolean certificatePoliciesCritical) {
        this.certificatePoliciesCritical = certificatePoliciesCritical;
    }

    public List<String> getCertificatePolicies() {
        return certificatePolicies;
    }

    public void setCertificatePolicies(List<String> certificatePolicies) {
        this.certificatePolicies = certificatePolicies;
    }

    public boolean isUseSubjectAlternativeName() {
        return useSubjectAlternativeName;
    }

    public void setUseSubjectAlternativeName(boolean useSubjectAlternativeName) {
        this.useSubjectAlternativeName = useSubjectAlternativeName;
    }

    public boolean isSubjectAlternativeNameCritical() {
        return subjectAlternativeNameCritical;
    }

    public void setSubjectAlternativeNameCritical(boolean subjectAlternativeNameCritical) {
        this.subjectAlternativeNameCritical = subjectAlternativeNameCritical;
    }

    public boolean isUseIssuerAlternativeName() {
        return useIssuerAlternativeName;
    }

    public void setUseIssuerAlternativeName(boolean useIssuerAlternativeName) {
        this.useIssuerAlternativeName = useIssuerAlternativeName;
    }

    public boolean isIssuerAlternativeNameCritical() {
        return issuerAlternativeNameCritical;
    }

    public void setIssuerAlternativeNameCritical(boolean issuerAlternativeNameCritical) {
        this.issuerAlternativeNameCritical = issuerAlternativeNameCritical;
    }

    public boolean isUseSubjectDirAttributes() {
        return useSubjectDirAttributes;
    }

    public void setUseSubjectDirAttributes(boolean useSubjectDirAttributes) {
        this.useSubjectDirAttributes = useSubjectDirAttributes;
    }

    public boolean isUseNameConstraints() {
        return useNameConstraints;
    }

    public void setUseNameConstraints(boolean useNameConstraints) {
        this.useNameConstraints = useNameConstraints;
    }

    public boolean isUseCrlDistributionPoint() {
        return useCrlDistributionPoint;
    }

    public void setUseCrlDistributionPoint(boolean useCrlDistributionPoint) {
        this.useCrlDistributionPoint = useCrlDistributionPoint;
    }

    public boolean isUseDefaultCrlDistributionPoint() {
        return useDefaultCrlDistributionPoint;
    }

    public void setUseDefaultCrlDistributionPoint(boolean useDefaultCrlDistributionPoint) {
        this.useDefaultCrlDistributionPoint = useDefaultCrlDistributionPoint;
    }

    public boolean isCrlDistributionPointCritical() {
        return crlDistributionPointCritical;
    }

    public void setCrlDistributionPointCritical(boolean crlDistributionPointCritical) {
        this.crlDistributionPointCritical = crlDistributionPointCritical;
    }

    public String getCrlDistributionPointUri() {
        return crlDistributionPointUri;
    }

    public void setCrlDistributionPointUri(String crlDistributionPointUri) {
        this.crlDistributionPointUri = crlDistributionPointUri;
    }

    public String getCrlIssuer() {
        return crlIssuer;
    }

    public void setCrlIssuer(String crlIssuer) {
        this.crlIssuer = crlIssuer;
    }

    public boolean isUseFreshestCrl() {
        return useFreshestCrl;
    }

    public void setUseFreshestCrl(boolean useFreshestCrl) {
        this.useFreshestCrl = useFreshestCrl;
    }

    public boolean isUseCaDefinedFreshestCrl() {
        return useCaDefinedFreshestCrl;
    }

    public void setUseCaDefinedFreshestCrl(boolean useCaDefinedFreshestCrl) {
        this.useCaDefinedFreshestCrl = useCaDefinedFreshestCrl;
    }

    public String getFreshestCrlUri() {
        return freshestCrlUri;
    }

    public void setFreshestCrlUri(String freshestCrlUri) {
        this.freshestCrlUri = freshestCrlUri;
    }

    public boolean isUseAuthorityInformationAccess() {
        return useAuthorityInformationAccess;
    }

    public void setUseAuthorityInformationAccess(boolean useAuthorityInformationAccess) {
        this.useAuthorityInformationAccess = useAuthorityInformationAccess;
    }

    public boolean isUseDefaultOcspServiceLocator() {
        return useDefaultOcspServiceLocator;
    }

    public void setUseDefaultOcspServiceLocator(boolean useDefaultOcspServiceLocator) {
        this.useDefaultOcspServiceLocator = useDefaultOcspServiceLocator;
    }

    public String getOcspServiceLocatorUri() {
        return ocspServiceLocatorUri;
    }

    public void setOcspServiceLocatorUri(String ocspServiceLocatorUri) {
        this.ocspServiceLocatorUri = ocspServiceLocatorUri;
    }

    public List<String> getCaIssuers() {
        return caIssuers;
    }

    public void setCaIssuers(List<String> caIssuers) {
        this.caIssuers = caIssuers;
    }

    public boolean isUseDefaultCaIssuer() {
        return useDefaultCaIssuer;
    }

    public void setUseDefaultCaIssuer(boolean useDefaultCaIssuer) {
        this.useDefaultCaIssuer = useDefaultCaIssuer;
    }

    public boolean isUsePrivKeyUsagePeriodNotBefore() {
        return usePrivKeyUsagePeriodNotBefore;
    }

    public void setUsePrivKeyUsagePeriodNotBefore(boolean usePrivKeyUsagePeriodNotBefore) {
        this.usePrivKeyUsagePeriodNotBefore = usePrivKeyUsagePeriodNotBefore;
    }

    public boolean isUsePrivKeyUsagePeriod() {
        return usePrivKeyUsagePeriod;
    }


    public void setUsePrivKeyUsagePeriod(boolean usePrivKeyUsagePeriod) {
        this.usePrivKeyUsagePeriod = usePrivKeyUsagePeriod;
    }


    public boolean isUsePrivKeyUsagePeriodNotAfter() {
        return usePrivKeyUsagePeriodNotAfter;
    }


    public void setUsePrivKeyUsagePeriodNotAfter(boolean usePrivKeyUsagePeriodNotAfter) {
        this.usePrivKeyUsagePeriodNotAfter = usePrivKeyUsagePeriodNotAfter;
    }


    public long getPrivKeyUsagePeriodStartOffset() {
        return privKeyUsagePeriodStartOffset;
    }


    public void setPrivKeyUsagePeriodStartOffset(long privKeyUsagePeriodStartOffset) {
        this.privKeyUsagePeriodStartOffset = privKeyUsagePeriodStartOffset;
    }


    public long getPrivKeyUsagePeriodLengthInSeconds() {
        return privKeyUsagePeriodLengthInSeconds;
    }


    public void setPrivKeyUsagePeriodLengthInSeconds(long privKeyUsagePeriodLengthInSeconds) {
        this.privKeyUsagePeriodLengthInSeconds = privKeyUsagePeriodLengthInSeconds;
    }


    public boolean isUseExtendedKeyUsage() {
        return useExtendedKeyUsage;
    }


    public void setUseExtendedKeyUsage(boolean useExtendedKeyUsage) {
        this.useExtendedKeyUsage = useExtendedKeyUsage;
    }


    public List<String> getExtendedKeyUsage() {
        return extendedKeyUsage;
    }


    public void setExtendedKeyUsage(List<String> extendedKeyUsage) {
        this.extendedKeyUsage = extendedKeyUsage;
    }


    public int getEndEntityProfileId() {
        return endEntityProfileId;
    }


    public void setEndEntityProfileId(int endEntityProfileId) {
        this.endEntityProfileId = endEntityProfileId;
    }


    public String getProfileName() {
        return profileName;
    }


    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

//    
//    public Serializable getData() {
//        return data;
//    }
//
//    
//    public void setData(Serializable data) {
//        this.data = data;
//    }


    public int getRowVersion() {
        return rowVersion;
    }


    public void setRowVersion(int rowVersion) {
        this.rowVersion = rowVersion;
    }


    public String getRowProtection() {
        return rowProtection;
    }


    public void setRowProtection(String rowProtection) {
        this.rowProtection = rowProtection;
    }


    public List<Integer> getAvailableCA() {
        return availableCA;
    }


    public void setAvailableCA(List<Integer> availableCA) {
        this.availableCA = availableCA;
    }


    public int getDefaultCA() {
        return defaultCA;
    }


    public void setDefaultCA(int defaultCA) {
        this.defaultCA = defaultCA;
    }


    public List<Integer> getEndEntityAvailableCA() {
        return endEntityAvailableCA;
    }


    public void setEndEntityAvailableCA(List<Integer> endEntityAvailableCA) {
        this.endEntityAvailableCA = endEntityAvailableCA;
    }


    public List<String> getAvailableCertificateProfile() {
        return availableCertificateProfile;
    }


    public void setAvailableCertificateProfile(List<String> availableCertificateProfile) {
        this.availableCertificateProfile = availableCertificateProfile;
    }


    public String getNumberOfAllowedRequestsString() {
        return numberOfAllowedRequestsString;
    }


    public void setNumberOfAllowedRequestsString(String numberOfAllowedRequestsString) {
        this.numberOfAllowedRequestsString = numberOfAllowedRequestsString;
    }


    public int getNumberOfAllowedRequestsInt() {
        return numberOfAllowedRequestsInt;
    }


    public void setNumberOfAllowedRequestsInt(int numberOfAllowedRequestsInt) {
        this.numberOfAllowedRequestsInt = numberOfAllowedRequestsInt;
    }


    public Map<String, Integer> getDnsName() {
        return dnsName;
    }


    public void setDnsName(Map<String, Integer> dnsName) {
        this.dnsName = dnsName;
    }


    public Map<String, Integer> getGUID() {
        return GUID;
    }


    public void setGUID(Map<String, Integer> GUID) {
        this.GUID = GUID;
    }

    public Map<String, Integer> getRFC822Name() {
        return RFC822Name;
    }

    public void setRFC822Name(Map<String, Integer> RFC822Name) {
        this.RFC822Name = RFC822Name;
    }

    public Map<String, Integer> getMsUPN() {
        return msUPN;
    }

    public void setMsUPN(Map<String, Integer> msUPN) {
        this.msUPN = msUPN;
    }

    public String toString() {
        return "CertificateTemplateImpl{" +
                "id=" + id +
                ", certificateProfileId=" + certificateProfileId +
                ", name='" + name + '\'' +
                ", useSubjectDnSubset=" + useSubjectDnSubset +
                ", subjectDnSubset=" + subjectDnSubset +
                ", useBasicConstraints=" + useBasicConstraints +
                ", basicConstraintsCritical=" + basicConstraintsCritical +
                ", useAuthorityKeyIdentifier=" + useAuthorityKeyIdentifier +
                ", authorityKeyIdentifierCritical=" + authorityKeyIdentifierCritical +
                ", useSubjectKeyIdentifier=" + useSubjectKeyIdentifier +
                ", subjectKeyIdentifierCritical=" + subjectKeyIdentifierCritical +
                ", useQcetSignatureDevice=" + useQcetSignatureDevice +
                ", useCertificatePolicies=" + useCertificatePolicies +
                ", certificatePoliciesCritical=" + certificatePoliciesCritical +
                ", certificatePolicies=" + certificatePolicies +
                ", useSubjectAlternativeName=" + useSubjectAlternativeName +
                ", subjectAlternativeNameCritical=" + subjectAlternativeNameCritical +
                ", useIssuerAlternativeName=" + useIssuerAlternativeName +
                ", issuerAlternativeNameCritical=" + issuerAlternativeNameCritical +
                ", useSubjectDirAttributes=" + useSubjectDirAttributes +
                ", useNameConstraints=" + useNameConstraints +
                ", useCrlDistributionPoint=" + useCrlDistributionPoint +
                ", useDefaultCrlDistributionPoint=" + useDefaultCrlDistributionPoint +
                ", crlDistributionPointCritical=" + crlDistributionPointCritical +
                ", crlDistributionPointUri='" + crlDistributionPointUri + '\'' +
                ", crlIssuer='" + crlIssuer + '\'' +
                ", useFreshestCrl=" + useFreshestCrl +
                ", useCaDefinedFreshestCrl=" + useCaDefinedFreshestCrl +
                ", freshestCrlUri='" + freshestCrlUri + '\'' +
                ", useAuthorityInformationAccess=" + useAuthorityInformationAccess +
                ", useDefaultOcspServiceLocator=" + useDefaultOcspServiceLocator +
                ", ocspServiceLocatorUri='" + ocspServiceLocatorUri + '\'' +
                ", caIssuers=" + caIssuers +
                ", useDefaultCaIssuer=" + useDefaultCaIssuer +
                ", usePrivKeyUsagePeriodNotBefore=" + usePrivKeyUsagePeriodNotBefore +
                ", usePrivKeyUsagePeriod=" + usePrivKeyUsagePeriod +
                ", usePrivKeyUsagePeriodNotAfter=" + usePrivKeyUsagePeriodNotAfter +
                ", privKeyUsagePeriodStartOffset=" + privKeyUsagePeriodStartOffset +
                ", privKeyUsagePeriodLengthInSeconds=" + privKeyUsagePeriodLengthInSeconds +
                ", useExtendedKeyUsage=" + useExtendedKeyUsage +
                ", extendedKeyUsage=" + extendedKeyUsage +
                ", availableCA=" + availableCA +
                ", endEntityProfileId=" + endEntityProfileId +
                ", profileName='" + profileName + '\'' +
                ", rowVersion=" + rowVersion +
                ", rowProtection='" + rowProtection + '\'' +
                ", endEntityAvailableCA=" + endEntityAvailableCA +
                ", defaultCA=" + defaultCA +
                ", availableCertificateProfile=" + availableCertificateProfile +
                ", numberOfAllowedRequestsString='" + numberOfAllowedRequestsString + '\'' +
                ", numberOfAllowedRequestsInt=" + numberOfAllowedRequestsInt +
                ", dnsName=" + dnsName +
                ", GUID=" + GUID +
                ", RFC822Name=" + RFC822Name +
                ", msUPN=" + msUPN +
                '}';
    }

    public CertificateTemplateDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}