public class ShowJniTest
{
    public static void main(String[] args)
    {
        OpenSSLFasadTest jt = new OpenSSLFasadTest();
        jt.CreateSert("/etc/alladinca/csr/client.csr", "/etc/alladinca/out/client.pem");
    }
}
