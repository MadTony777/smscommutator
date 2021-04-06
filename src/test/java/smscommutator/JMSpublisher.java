package smscommutator;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JMSpublisher  extends BaseClass {
    public void JMSpublisher(String environment, String CID, String phoneNumber, String filename) throws JMSException, IOException {
        Logger log = LoggerFactory.getLogger(UnitTests.class);
        String url = "";
        switch (environment) {
            case "stage":
                url = urllanstage;
                break;
            case "test":
                url = urllantest;
                break;
        }
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("karaf", "karaf", url);
        ActiveMQQueue destination = new ActiveMQQueue(sourcequeue);
        Connection connection;
        connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(destination);
        TextMessage textMessage = session.createTextMessage();
        textMessage.setJMSCorrelationID(CID);
//        textMessage.setJMSExpiration(Long.parseLong("1589484524575"));
//        textMessage.setJMSMessageID("ID:sms-commutator-stage-40805-1589455955032-1:1:1:1:3");
//        textMessage.setStringProperty("X_VSK_TARGET_BROKER", targetBroker);
        String rightString = new String(Files.readAllBytes(Paths.get(paths + filename)), StandardCharsets.UTF_8);
        String finalBody = rightString.replace("${phoneNumber}", phoneNumber);
        log.info("Message send with CID: " + CID);
        textMessage.setText(finalBody);
        producer.send(textMessage);
        connection.close();
        session.close();
        producer.close();
    }
}
