
public class OpenSSLFasadTest
{
    static
    {
        System.loadLibrary("opensslfasad");
    }

    // обращаем внимание на слово native
    public native int CreateSert(String filenameCSR, String filenameSertOut);
}
