#include <iostream>
#include "../common/opensslx509tools.h"

int main()
{
    CreateSertX509PEM("/etc/alladinca/csr/client.csr", "/etc/alladinca/out/client.pem");
    CreateSertX509PEM("/etc/alladinca/csr/client.csr", "/etc/alladinca/out/client1.pem");
    CreateSertX509PEM("/etc/alladinca/csr/client.csr", "/etc/alladinca/out/client2.pem");
    return 0;
}
