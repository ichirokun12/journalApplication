package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailSrvice emailSrvice;

    @Test
    void testSendMail() {
        emailSrvice.sendEmail("testingacc9756@gmail.com", "testing java mail sender", "bla bls bla bls");
    }

}
