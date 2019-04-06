package smtp;

import model.mail.Message;
import java.io.IOException;
import java.net.InetAddress;

public interface ISmtpClient {

    int DEFAULT_PORT = 25;

    String EHLO = "EHLO";
    String MAIL_FROM = "MAIL FROM:";
    String RCPT_TO = "RCPT TO:";
    String DATA = "DATA";
    String QUIT = "quit";
    String FROM = "From:";
    String TO = "To:";
    String CC = "Cc:";
    String SUBJECT = "Subject:";
    String RETURN = "\r\n";
    String END_MESSAGE = "\r\n.\r\n";


    public void sendMessage(Message message) throws IOException;

}
