#include "opensslx509tools.h"
#include <iostream>
#include <openssl/pem.h>
#include <openssl/err.h>
#include <memory>
#include <cstring>
#include <sstream>

openSSLException::openSSLException(const std::string & errStr)
{
    unsigned long errSSL = ERR_peek_last_error();

// clear all error in queue
    while (ERR_get_error() != 0);

    create(errStr, errSSL);
}
openSSLException::openSSLException(const std::string & errStr, unsigned long errSSL)
{
    create (errStr, errSSL);
}

void openSSLException::create(const std::string & errStr, unsigned long errSSL)
{
    char tempBuf[256];
    tempBuf[0] = 0;

    errString = errStr;
    if (errSSL == 0)
        return;

    ERR_error_string_n(errSSL, tempBuf, sizeof(tempBuf) - 1);
    if (tempBuf[0] != 0) {
        errString += ". OpenSSl error: ";
        errString += tempBuf;
    }
}

const char * openSSLException::what () const noexcept
{
    return errString.c_str();
}

openSSL_X509_Create_Sertificate::~openSSL_X509_Create_Sertificate ()
{
    if (pkey) {
        EVP_PKEY_free(pkey);
        pkey = NULL;
    }

    if (x509) {
        X509_free(x509);
        x509 = NULL;
    }

    if (x509_REQ) {
        X509_REQ_free(x509_REQ);
        x509_REQ = NULL;
    }
}

void openSSL_X509_Create_Sertificate::read_private_key (const char * fileNamePrivateKey)
{
    std::stringstream errStr;

    FILE * pkey_file = fopen(fileNamePrivateKey, "rb");
    if(! pkey_file) {
        errStr << "Read private key. Unable to open " << fileNamePrivateKey << " for reading. " << std::strerror(errno);
        throw std::logic_error(errStr.str());
    }

    if (fseek (pkey_file, 0L, SEEK_END) != 0) {
        errStr << "Read private key. Error fseek in " << fileNamePrivateKey << ". " << std::strerror(errno);
        fclose (pkey_file);
        throw std::logic_error(errStr.str());
    }

    long lenFile;
    if ((lenFile = ftell (pkey_file)) == -1L) {
        errStr << "Read private key. Error ftell in " << fileNamePrivateKey << ". " << std::strerror(errno);
        fclose (pkey_file);
        throw std::logic_error(errStr.str());
    }

    if (fseek (pkey_file, 0, SEEK_SET) != 0) {
        errStr << "Read private key. Error fseek in " << fileNamePrivateKey << ". " << std::strerror(errno);
        fclose (pkey_file);
        throw std::logic_error(errStr.str());
    }

    std::unique_ptr<char[]> pMem (new char[lenFile]);

    unsigned int res = fread (pMem.get(), 1, lenFile, pkey_file);
    if (ferror (pkey_file) != 0 || res != lenFile) {
        errStr << "Read private key. Error read file " << fileNamePrivateKey << ". " << std::strerror(errno);
        fclose (pkey_file);
        throw std::logic_error(errStr.str());
    }
    fclose (pkey_file);

    BIO * bo = NULL;
    if ((bo = BIO_new( BIO_s_mem() )) == NULL) {
        throw openSSLException ("Read private key. Error allocate Bio_new");
    }

    if (BIO_write(bo, pMem.get(), lenFile) < 1) {
        BIO_free(bo);
        throw openSSLException ("Read private key. BIO_write error");
    }

    if (! PEM_read_bio_PrivateKey( bo, &pkey, 0, 0 )) {
        BIO_free(bo);
        throw openSSLException ("Read private key. PEM_read_bio_PrivateKey error");
    }

    BIO_free(bo);
}

void openSSL_X509_Create_Sertificate::read_csr (const char * fileNameCSR)
{
    std::stringstream errStr;

    FILE * pkey_file = fopen(fileNameCSR, "rb");
    if(! pkey_file) {
        errStr << "Read CSR. Unable to open " << fileNameCSR << " for reading. " << std::strerror(errno);
        throw std::logic_error(errStr.str());
    }

    if (fseek (pkey_file, 0L, SEEK_END) != 0) {
        errStr << "Read CSR. Error fseek in " << fileNameCSR << ". " << std::strerror(errno);
        fclose (pkey_file);
        throw std::logic_error(errStr.str());
    }

    long lenFile;
    if ((lenFile = ftell (pkey_file)) == -1L) {
        errStr << "Read CSR. Error ftell in " << fileNameCSR << ". " << std::strerror(errno);
        fclose (pkey_file);
        throw std::logic_error(errStr.str());
    }

    if (fseek (pkey_file, 0, SEEK_SET) != 0) {
        errStr << "Read CSR. Error fseek in " << fileNameCSR << ". " << std::strerror(errno);
        fclose (pkey_file);
        throw std::logic_error(errStr.str());
    }

    std::unique_ptr<char[]> pMem (new char[lenFile]);

    unsigned int res = fread (pMem.get(), 1, lenFile, pkey_file);
    if (ferror (pkey_file) != 0 || res != lenFile) {
        errStr << "Read CSR. Error read file " << fileNameCSR << ". " << std::strerror(errno);
        fclose (pkey_file);
        throw std::logic_error(errStr.str());
    }
    fclose (pkey_file);

    BIO * bo = NULL;
    if ((bo = BIO_new( BIO_s_mem() )) == NULL) {
        throw openSSLException ("Read CSR. Error allocate Bio_new");
    }

    if (BIO_write(bo, pMem.get(), lenFile) < 1) {
        BIO_free(bo);
        throw openSSLException ("Read CSR. BIO_write error");
    }

    if (! PEM_read_bio_X509_REQ( bo, & x509_REQ, 0, 0)) {
        BIO_free(bo);
        throw openSSLException ("Read CSR. PEM_read_bio_X509_REQ error");
    }

    BIO_free(bo);
}

void openSSL_X509_Create_Sertificate::generate_x509(unsigned long periodsec, const char * country, const char * company, const char * commName)
{
    x509 = X509_new();
    if(! x509) {
        throw openSSLException ("Generate x509. Error allocate X509_new");
    }

// Set the serial number.
    if (! ASN1_INTEGER_set( X509_get_serialNumber(x509), 1)) {
        throw openSSLException ("Generate x509, set serial number. Error ASN1_INTEGER_set, error alocate memory");
    }

// This certificate is valid from now until exactly period sec.
    if (! X509_gmtime_adj(X509_get_notBefore(x509), 0)) {
        throw openSSLException ("Generate x509, set sertificate begin data. Error X509_gmtime_adj");
    }
    if (! X509_gmtime_adj(X509_get_notAfter(x509), periodsec)) {
        throw openSSLException ("Generate x509, set sertificate end data. Error X509_gmtime_adj");
    }

// Set the public key for our certificate.
    EVP_PKEY * ppubKey = X509_REQ_get_pubkey(x509_REQ);
    if (! ppubKey) {
        throw openSSLException ("Generate x509. Error X509_REQ_get_pubkey");
    }
    if (! X509_set_pubkey(x509, ppubKey)) {
        throw openSSLException ("Generate x509. Error X509_set_pubkey");
    }

// We want to copy the subject name to the issuer name.
    X509_NAME * name = X509_get_subject_name(x509);
    if (! name) {
        throw openSSLException ("Generate x509. Error X509_get_subject_name");
    }

// Set the country code and common name.
    if (! X509_NAME_add_entry_by_txt(name, "C",  MBSTRING_ASC, (unsigned char *)country, -1, -1, 0)) {
        throw openSSLException ("Generate x509. Error X509_NAME_add_entry_by_txt");
    }
    if (! X509_NAME_add_entry_by_txt(name, "O",  MBSTRING_ASC, (unsigned char *)company, -1, -1, 0)) {
        throw openSSLException ("Generate x509. Error X509_NAME_add_entry_by_txt");
    }
    if (! X509_NAME_add_entry_by_txt(name, "CN", MBSTRING_ASC, (unsigned char *)commName, -1, -1, 0)) {
        throw openSSLException ("Generate x509. Error X509_NAME_add_entry_by_txt");
    }

// Now set the issuer name.
    if (! X509_set_issuer_name(x509, name)) {
        throw openSSLException ("Generate x509. Error X509_set_issuer_name");
    }

// Actually sign the certificate with our key.
    if(! X509_sign(x509, pkey, EVP_sha1())) {
        throw openSSLException ("Generate x509, signing certificate. Error X509_sign");
    }
}


void openSSL_X509_Create_Sertificate::write_pem_sertificate (const char * fileNameCert)
{
    std::stringstream errStr;

    FILE * x509_file = fopen(fileNameCert, "wb");
    if(! x509_file) {
        errStr << "Write pem sertificate. Unable to open " << fileNameCert << " for write. " << std::strerror(errno);
        throw std::logic_error(errStr.str());
    }

    bool ret = PEM_write_X509( x509_file, x509 );
    fclose(x509_file);

    if(!ret) {
        throw openSSLException ("Write pem sertificate. Error PEM_write_X509");
    }
}

int CreateSertX509PEM(const char * nameCSR, const char * clientCertOut)
{
    try{
        openSSL_X509_Create_Sertificate sertif;
        std::cout << "Create sertificate, file CSR - " << nameCSR << ", file Certification - " << clientCertOut << std::endl;

        sertif.read_private_key ("/etc/alladinca/ca/ca.key");
        sertif.read_csr(nameCSR);

        sertif.generate_x509 (3600000, "RU", "Open Solutions", "ddn");
        sertif.write_pem_sertificate (clientCertOut);

        std::cout << "Create sertificate succesfull" << std::endl;

        return 0;
    }
    catch (std::exception & ex) {
        std::cerr << ex.what() << std::endl;
        return -1;
    }
}
