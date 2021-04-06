package smscommutator;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class JMSconsumer {
    public String JMSconsumer(String environment, String CID) throws JMSException {
        Logger log = LoggerFactory.getLogger(UnitTests.class);
        String url = "";
        switch (environment) {
            case "stage":
                url = BaseClass.urllanstage;
                break;
            case "test":
                url = BaseClass.urllantest;
                break;
        }
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("karaf", "karaf", url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        Queue destination = session.createQueue(BaseClass.targetqueue);
        MessageConsumer consumer = session.createConsumer(destination);
        Message message = consumer.receive();
        String cid = CID;
        String result = "";
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
//            System.out.println("Received message '" + textMessage.getText() + "'" + " CID:" + textMessage.getJMSCorrelationID());
            if (cid.equals(textMessage.getJMSCorrelationID())) {
                log.info("Received message '" + textMessage.getText());
                result = String.valueOf(textMessage.getText());
            }
        }
        connection.close();
        return result;
    }
}
