#ifndef _OPENSSLFASAD_H
#define _OPENSSLFASAD_H

#include <string>
#include <openssl/x509.h>

class openSSLException : public std::exception
{
public:
    openSSLException(const std::string & errStr);
    openSSLException(const std::string & errStr, unsigned long errSSL);
    virtual const char * what () const noexcept override;

private:
    void create(const std::string & errStr, unsigned long errSSL);
    std::string errString;
};

/// \brief Класс для создания сертификата по запросу
/// \details Класс для создания сертификата по запросу, выполняет загрузку приватного ключа из файла, запроса на создание сертификата из файла и создание сертификата
class openSSL_X509_Create_Sertificate
{
public:
    openSSL_X509_Create_Sertificate ():pkey(NULL), x509(NULL), x509_REQ(NULL){}
    ~openSSL_X509_Create_Sertificate ();

    /*!
    Загрузка приватного ключа из файла
    \param[in] fileNamePrivateKey - путь до файла с приватным ключом
    */
    void read_private_key (const char * fileNamePrivateKey);

    /*!
    Загрузка запроса на создание сертификата из файла
    \param[in] fileNameCSR - путь до файла с приватным ключом
    */
    void read_csr (const char * fileNameCSR);

    /*!
    Формирование сертификата
    */
    void generate_x509(unsigned long periodsec, const char * country, const char * company, const char * commName);

    /*!
    Запись сертификата в файл
    \param[in] fileNameCert - путь до файла сертификата
    */
    void write_pem_sertificate (const char * fileNameCert);

private:
    EVP_PKEY * pkey;
    X509 * x509;
    X509_REQ * x509_REQ;
};

int CreateSertX509PEM(const char * nameCSR, const char * clientCertOut);

#endif
