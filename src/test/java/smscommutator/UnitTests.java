package smscommutator;//

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class UnitTests {
    String arg = System.getProperty("arg", "test");
    public String environment = arg;
    Logger log = LoggerFactory.getLogger(UnitTests.class);

    @BeforeEach
    public void executedBeforeEach(TestInfo testInfo) {
        log.info("Starting test: "+ testInfo.getDisplayName());
    }

    @AfterEach
    public void executedAfterEach() {
        log.info("End test\n");
    }

    @Test
    public void smscommutator_correctRequest1_COMPLITED() throws Exception {
        String CID = randomNumeric(10);
        String result = Template.reporter(environment, CID, "89521806945", "sms.xml");
        assertThat(result, containsString("destination="));
        assertThat(result, containsString("status=\"ACCEPTED"));
    }

    @Test
    public void smscommutator_correctRequest2_COMPLITED() throws Exception {
        String CID = randomNumeric(10);
        String result = Template.reporter(environment, CID, "79521806945", "sms.xml");
        assertThat(result, containsString("destination="));
        assertThat(result, containsString("status=\"ACCEPTED"));
    }


    @Test
    public void smscommutator_correctRequest3_COMPLITED() throws Exception {
        String CID = randomNumeric(10);
        String result = Template.reporter(environment, CID, "+79521806945","sms.xml");
        assertThat(result, containsString("destination="));
        assertThat(result, containsString("status=\"ACCEPTED"));
    }

    @Test
    public void smscommutator_incorrectRequest_COMPLITED() throws Exception {
        String CID = randomNumeric(10);
        String result = Template.reporter(environment, CID, "+99521806945", "smsIncorrectXML.xml");
        assertThat(result, containsString("status=\"ERROR"));
        assertThat(result, containsString("Transformation error"));
    }


    @Test
    public void smscommutator_incorrectForSchemaRequest_COMPLITED() throws Exception {
        String CID = randomNumeric(10);
        String result = Template.reporter(environment, CID, "+99521806945", "smsIncorrectReq.xml");
        assertThat(result, containsString("status=\"ERROR"));
        assertThat(result, containsString("validation error"));
    }
}