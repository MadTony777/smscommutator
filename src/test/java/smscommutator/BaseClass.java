package smscommutator;

public class BaseClass {

    static String urllanstage = "tcp://192.168.66.196:61616";
    static String urllantest = "tcp://192.168.66.194:61616";
    static String sourcequeue = "sms.commutator.in.queue";
    static String targetqueue = "sms.commutator.out.queue";
    static String filename = "sms.xml";
    public static final String paths = "src/test/java/smscommutator/Examples/";
    public static void pauseMethod(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
