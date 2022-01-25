#include <stdio.h>
#include "OpenSSLFasadTest.h"
#include "./common/opensslx509tools.h"

JNIEXPORT jint JNICALL Java_OpenSSLFasadTest_CreateSert (JNIEnv * jenv, jobject, jstring nameFileCSR, jstring nameFileCertOut)
{
    const char *pnameFileCSR = jenv->GetStringUTFChars(nameFileCSR, 0);
    const char *pnameFileCertOut = jenv->GetStringUTFChars(nameFileCertOut, 0);

    CreateSertX509PEM(pnameFileCSR, pnameFileCertOut);

    jenv->ReleaseStringUTFChars(nameFileCSR, pnameFileCSR);
    jenv->ReleaseStringUTFChars(nameFileCertOut, pnameFileCertOut);

    return 0;

}

