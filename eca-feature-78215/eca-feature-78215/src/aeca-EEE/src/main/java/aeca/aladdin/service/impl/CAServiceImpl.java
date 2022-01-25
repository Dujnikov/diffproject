package aeca.aladdin.service.impl;

import aeca.aladdin.domain.dto.TypeCAEnum;
import aeca.aladdin.domain.entity.CAInfo;
import aeca.aladdin.domain.entity.Certificate;
import aeca.aladdin.service.CAService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CAServiceImpl implements CAService {

    @Override
    public CAInfo getCA(int id) {
        Optional<CAInfo> contains = TestData.caInfos.stream().filter(caInfo -> caInfo.getId() == id).findFirst();
        return contains.orElse(null);
    }

    @Override
    public Set<CAInfo> getAllCA() {
        return TestData.caInfos;
    }

    @Override
    public Set<Certificate> getAllCertificateByActiveCA(int id) {
        Set<Certificate> result = TestData.certificates.stream().filter(certificate -> certificate.getType() == 1).collect(Collectors.toSet());
        return result;
    }

    @Override
    public CAInfo createCA(String encryptionType, TypeCAEnum type, String name, String subjectDN,
                           String subjectAltName, String hash, Integer keyLength, Long validity) {
        return TestData.caInfos.stream().findFirst().get();
    }

    @Override
    public boolean activateCA(int id) {
        CAInfo caInfo = getCA(id);
        if (caInfo == null) {
            return false;
        }
        if (caInfo.getStatus() != 2) {
            return false;
        }
        return true;
    }

    @Override
    public String getFile(int id, String fileFormat) {
        CAInfo caInfo = getCA(id);
        if (caInfo == null) {
            return null;
        }
        switch (fileFormat) {
            case ("crt"):
                return "U3ViamVjdDogQ049dGVzdFJvb3QxLE9VPURlcGFydG1lbnQsTz1Pcmdhbml6YXRpb24sTD1DaXR5LERDPUNvbXBvbmVudCxDPVJVCklzc3VlcjogQ049dGVzdFJvb3QxLE9VPURlcGFydG1lbnQsTz1Pcmdhbml6YXRpb24sTD1DaXR5LERDPUNvbXBvbmVudCxDPVJVCi0tLS0tQkVHSU4gQ0VSVElGSUNBVEUtLS0tLQpNSUlDVkRDQ0FmbWdBd0lCQWdJVUNwR1RtMTl2dldtbDFqTnpUL3d6dkNUaG5wVXdDZ1lJS29aSXpqMEVBd0l3CmR6RVNNQkFHQTFVRUF3d0pkR1Z6ZEZKdmIzUXhNUk13RVFZRFZRUUxEQXBFWlhCaGNuUnRaVzUwTVJVd0V3WUQKVlFRS0RBeFBjbWRoYm1sNllYUnBiMjR4RFRBTEJnTlZCQWNNQkVOcGRIa3hHVEFYQmdvSmtpYUprL0lzWkFFWgpGZ2xEYjIxd2IyNWxiblF4Q3pBSkJnTlZCQVlUQWxKVk1CNFhEVEl4TVRJd09UQTROREl3TkZvWERUTTJNVEl3Ck9UQXdNREF3TUZvd2R6RVNNQkFHQTFVRUF3d0pkR1Z6ZEZKdmIzUXhNUk13RVFZRFZRUUxEQXBFWlhCaGNuUnQKWlc1ME1SVXdFd1lEVlFRS0RBeFBjbWRoYm1sNllYUnBiMjR4RFRBTEJnTlZCQWNNQkVOcGRIa3hHVEFYQmdvSgpraWFKay9Jc1pBRVpGZ2xEYjIxd2IyNWxiblF4Q3pBSkJnTlZCQVlUQWxKVk1Ga3dFd1lIS29aSXpqMENBUVlJCktvWkl6ajBEQVFjRFFnQUVMd1BIdmVUZnN2UkZONVdQRm8zOTQ2aTkxOUlJTHh0MnF5TlJSQTMzRzg2S2lKOEEKcFlXd3hkcmxoN2dpdWNob2xXOW1IZW91dzloVlQ0WGR4RnZXcnFOak1HRXdEd1lEVlIwVEFRSC9CQVV3QXdFQgovekFmQmdOVkhTTUVHREFXZ0JUN1gyZVhmYVRzbk1lbFc0TDJlN3VzbWw5UmdUQWRCZ05WSFE0RUZnUVUrMTluCmwzMms3SnpIcFZ1QzludTdySnBmVVlFd0RnWURWUjBQQVFIL0JBUURBZ0dHTUFvR0NDcUdTTTQ5QkFNQ0Ewa0EKTUVZQ0lRRE9lVHpBRUtMdENoaHo4L0ZOZDBHWWZlQnVDZlI2RXAySDJIdktuK0l0K0FJaEFJamZTZ1dsYTBTeAp2bjBEdWxpblArYUM5M29YdVlpMjZkOCtHdjFwT1BHcwotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0t";
            case ("crl"):
                return "TUlJQm5qQ0NBVVVDQVFFd0NnWUlLb1pJemowRUF3SXdkekVTTUJBR0ExVUVBd3dKZEdWemRGSnZiM1F4TVJNdwpFUVlEVlFRTERBcEVaWEJoY25SdFpXNTBNUlV3RXdZRFZRUUtEQXhQY21kaGJtbDZZWFJwYjI0eERUQUxCZ05WCkJBY01CRU5wZEhreEdUQVhCZ29Ka2lhSmsvSXNaQUVaRmdsRGIyMXdiMjVsYm5ReEN6QUpCZ05WQkFZVEFsSlYKRncweU1qQXhNVE14TURJek1EbGFGdzB5TWpBeE1UTXhNVEl6TURsYW9JR2NNSUdaTUI4R0ExVWRJd1FZTUJhQQpGUHRmWjVkOXBPeWN4NlZiZ3ZaN3U2eWFYMUdCTUFvR0ExVWRGQVFEQWdFRE1Hb0dBMVVkSEFSak1HR2dYNkJkCmhsdG9kSFJ3T2k4dk1Ua3lMakUyT0M0eE1URXVNVGt5TDJkbGRFTlNUQzV3YUhBL1EyRk9ZVzFsUFhSbGMzUlMKYjI5ME1TWkRVa3hPWVcxbFUzVm1abWw0UFc1MWJHd21SR1ZzZEdGRFVreEJiR3h2ZDJWa1BXWmhiSE5sTUFvRwpDQ3FHU000OUJBTUNBMGNBTUVRQ0lDWkhWQVB0TTRjZGk1QlFTS3Z5c0ZQL3dhNlZsYjdtRW9FK0UwK2VQaUN4CkFpQnp6dWh0K0NVSCtpdkNPRTFnWUNFeUtWOFJMN2ZNcEF1VGlFSDJkTTZ5ZEE9PQ==";
            case ("csr"):
                return "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURSBSRVFVRVNULS0tLS0KTUlJQ1ZEQ0NBVHdDQVFBd0R6RU5NQXNHQTFVRUF3d0VZV1prYURDQ0FTSXdEUVlKS29aSWh2Y05BUUVCQlFBRApnZ0VQQURDQ0FRb0NnZ0VCQU5zMW9hZHUwVms2aitFYkVaOEEyamoxb2w1MDRPSUxKNk5vSU1RcENzU1hmK2lVCndCdldhdjllazV6dkpYa24rdlBqS3dhM1dmQVA0VVdNTHRwOTM0ekVVUkhjNGE2Q0JOM1FjUnNrNWJMRlF6eUkKaDh2R1VnZ2RWVWNYbHN5ajV1S2NneDRhSU5oNk04Rzl2Wk8wVUhITXlQU3ZVUXpGbTl5amJUZ3VzdnVKcXJvQwpUTCs3WGpiYUl5Y2s3MVErN2RBNVdwblFoQXNZd3ZXSzlqMElmaDcwVitOWmIxbVJxaW54c3lFaTNpZnVUV1cwCk52dXJNQ1dKcEQrc2kxaERiR0hvMHlvYUZoeW9USUNPV1FzMEhydExHd2RVS0RQcExoRHZPZmdPWUxSNmd6eUcKUFNTUVVUWHBRRThnSlg1NEdwWm1zMUhzVklaTmQydFZJNUtpRVBjQ0F3RUFBYUFBTUEwR0NTcUdTSWIzRFFFQgpCUVVBQTRJQkFRQ1lHZk9Gdy9BbzlRVDRLMXYvV2oyWUtYOVR4QWpIU2h4eSt1b0pVQ2ZWVEk1QVI2eDloM2dOCnoxQjVDeGpQZE1sd3ZzUzZ5eUs0elF1d00rdytXL2wzZ0VrMG9hUGNJUHdwZEkzM1RPWHplWmd5Ym00NWZCbWwKajkvQksrS3RVYnJ3eklIaWs3Skx1TlRtVkMrZXREeXVYYys3UzRUcVRyUkNDYWQ1T1J5WkFYVjZyWkZ1VzFaRApmakNvU0p4ZGsySkw4YXhKOHhaTFpRQW5yVSswcEY5ZUJTOU1EQkVKVG4vS01yN0xodklnWmloZHpNSmJJZDg1CjROSUxXL25kNUxONEprMzcyRnFkYTVOUGJ5aUZhS29lMWdWZmhtaXdJcDFqSU9ETUQ2dWFpdndGRzNCTG9sUVIKbGNxcFhyZHJjMzI3SW8vbENNUmh0REd3NGxRZW9NaHEKLS0tLS1FTkQgQ0VSVElGSUNBVEUgUkVRVUVTVC0tLS0t";
            default:
                return null;
        }
    }

    @Override
    public boolean importResponse(int id, String file) {
        CAInfo caInfo = getCA(id);
        if (caInfo == null) {
            return false;
        }
        if (caInfo.getStatus() != 3) {
            return false;
        }
        return true;
    }

    @Override
    public boolean editPublicationPoint(int id, String aiaPublicationPoint, String aiaDistributionPoint,
                                        String crlPublicationPoint, String crlDistributionPoint) {
        CAInfo caInfo = getCA(id);
        if (caInfo == null) {
            return false;
        }
        if (aiaDistributionPoint != null) caInfo.setAiaPublicationPoint(aiaPublicationPoint);
        if (aiaDistributionPoint != null) caInfo.setAiaDistributionPoint(aiaDistributionPoint);
        if (crlPublicationPoint != null) caInfo.setCrlPublicationPoint(crlPublicationPoint);
        if (crlDistributionPoint != null) caInfo.setCrlDistributionPoint(crlDistributionPoint);

        return true;
    }

    @Override
    public int publication(int id, String type) {
        CAInfo caInfo = getCA(id);
        if (caInfo == null) {
            return 400;
        }
        switch (type) {
            case ("crl"):
                return 200;
            case ("aia"):
                return 200;
            default:
                return 404;
        }
    }
}