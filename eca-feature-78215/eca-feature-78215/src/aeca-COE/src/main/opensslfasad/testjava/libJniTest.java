
public class libJniTest
{
    static
    {
        System.loadLibrary("JniTest");
    }

    // обращаем внимание на слово native
    public native int showString(String message);
}