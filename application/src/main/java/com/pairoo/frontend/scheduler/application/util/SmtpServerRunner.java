package com.pairoo.frontend.scheduler.application.util;

import java.util.List;
import java.util.logging.Level;
import javax.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

/**
 *
 * @author klaus
 */
public class SmtpServerRunner {

    private final Logger logger = LoggerFactory.getLogger(SmtpServerRunner.class);
    private int port;
    private Wiser smtpServer;
    private boolean stopMonitoring = false;
    private boolean enableMonitoring = true;

    public void start() {

        smtpServer = new Wiser();
        smtpServer.setPort(port);
        smtpServer.start();
        logger.info("Demo SMPT-Server started on port " + port);

        if (!enableMonitoring) {
            return;
        }

        Runnable messageMonitor = new Runnable() {
            public void run() {
                while (!stopMonitoring) {
                    if (smtpServer.getMessages().isEmpty()) {
                        try {
                            Thread.sleep(2 * 1000);
                        } catch (InterruptedException ex) {
                            java.util.logging.Logger.getLogger(SmtpServerRunner.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        continue;
                    }
                    for (WiserMessage mail : smtpServer.getMessages()) {
                        try {
                            mail.dumpMessage(System.out);
                        } catch (MessagingException ex) {
                            java.util.logging.Logger.getLogger(SmtpServerRunner.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    smtpServer.getMessages().clear();

                }
            }
        };
        Thread monitorThread = new Thread(messageMonitor);
        monitorThread.start();

    }

    public void setPort(int port) {
        this.port = port;
    }

    public void stop() {
        smtpServer.stop();
        stopMonitoring = true;
    }

    public List<WiserMessage> getMessages() {
        return smtpServer == null ? null : smtpServer.getMessages();
    }

    public int getPort() {
        return port;
    }
}
