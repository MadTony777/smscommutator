package smscommutator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.io.IOException;

public class Template {
    public static String reporter(String environment, String CID, String phoneNumber, String filename) throws IOException, JMSException {
        Logger log = LoggerFactory.getLogger(UnitTests.class);
        JMSpublisher publisher = new JMSpublisher();
        publisher.JMSpublisher(environment , CID, phoneNumber, filename);
        JMSconsumer consumer = new JMSconsumer();
        String result = consumer.JMSconsumer(environment , CID);
        log.info(result);
        return result;
    }
}
