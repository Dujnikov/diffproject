package aeca.aladdin.service.impl;

import aeca.aladdin.domain.entity.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TestData {

    protected static final Set<CAInfo> caInfos = new HashSet<>();
    protected static final Set<AuthSubject> subjects = new HashSet<>();
    protected static final Set<Template> templates = new HashSet<>();
    protected static final Set<Certificate> certificates = new HashSet<>();
    protected static final Set<Group> groups = new HashSet<>();

    static {
        caInfos.add(new CAInfo(1, "firstCA", "CN=firstCA, O=Organization,OU=Department,L=City,DC=Component,C=RU",
                "RFC822NAME=example@example.com,DNSNAME=www.example.com,IPADDRESS=10.10.10.110", 2, 1,
                LocalDateTime.now().plusYears(15).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                "RSA", "SHA1", 2048, 86400000L, null, null, null, null, 0L, 0L));
        caInfos.add(new CAInfo(2, "secondCA", "CN=firstCA, O=Organization,OU=Department,L=City,DC=Component,C=RU",
                "RFC822NAME=example@example.com,DNSNAME=www.example.com,IPADDRESS=10.10.10.110", 3, 2,
                LocalDateTime.now().plusYears(20).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                "RSA", "SHA1", 2048, 86400000L, null, null, null, null, 0L, 0L));
        caInfos.add(new CAInfo(3, "thirdCA", "CN=firstCA, O=Organization,OU=Department,L=City,DC=Component,C=RU",
                "RFC822NAME=example@example.com,DNSNAME=www.example.com,IPADDRESS=10.10.10.110", 2, 2,
                LocalDateTime.now().minusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                "RSA", "SHA1", 2048, 86400000L, null, null, null, null, 0L, 0L));
        caInfos.add(new CAInfo(4, "fourthCA", "CN=firstCA, O=Organization,OU=Department,L=City,DC=Component,C=RU",
                "RFC822NAME=example@example.com,DNSNAME=www.example.com,IPADDRESS=10.10.10.110", 1, 2,
                LocalDateTime.now().plusYears(25).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                "RSA", "SHA1", 2048, 86400000L, null, null, null, null, 0L, 0L));


        subjects.add(new AuthSubject(1, "subjectDN1", "subjectAltName1", null, null, null));
        subjects.add(new AuthSubject(2, "subjectDN2", "subjectAltName2", null, null, null));
        subjects.add(new AuthSubject(3, "subjectDN3", "subjectAltName3", null, null, null));

        templates.add(new Template(1, "testTemplate1", null));
        templates.add(new Template(2, "testTemplate2", null));
        templates.add(new Template(3, "testTemplate3", null));

        certificates.add(new Certificate(1, "certificate1", "iAmSerialNumber1", "iAmFingerPrint1", null,
                subjects.stream().filter(authSubject -> authSubject.getId() == 1).findFirst().get(), 1, -1, 0));
        certificates.add(new Certificate(2, "certificate2", "iAmSerialNumber2", "iAmFingerPrint2", null,
                subjects.stream().filter(authSubject -> authSubject.getId() == 2).findFirst().get(), 1, -1, 1));
        certificates.add(new Certificate(3, "certificate3", "iAmSerialNumber3", "iAmFingerPrint3", null,
                subjects.stream().filter(authSubject -> authSubject.getId() == 3).findFirst().get(), 2, 1, 1));
        certificates.add(new Certificate(4, "certificate4", "iAmSerialNumber4", "iAmFingerPrint5", null,
                subjects.stream().filter(authSubject -> authSubject.getId() == 3).findFirst().get(), 1, -1, 1));
        certificates.add(new Certificate(5, "certificate5", "iAmSerialNumber5", "iAmFingerPrint5", null,
                subjects.stream().filter(authSubject -> authSubject.getId() == 3).findFirst().get(), 1, -1, 1));

        groups.add(new Group(1, "testGroup", templates, subjects));

        templates.forEach(template -> template.setGroups(groups));

        subjects.forEach(authSubject -> authSubject.setGroups(groups));
        subjects.forEach(authSubject -> authSubject.setTemplates(templates));
        subjects.forEach(authSubject -> authSubject.setCertificates(certificates.stream()
                .filter(certificate -> certificate.getId() == authSubject.getId()).collect(Collectors.toSet())));
    }
}