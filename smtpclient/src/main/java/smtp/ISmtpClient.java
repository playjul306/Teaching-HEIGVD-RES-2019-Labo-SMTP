package smtp;

import model.mail.Message;
import java.io.IOException;
import java.net.InetAddress;

public interface ISmtpClient {

    /*
    l'interface contient le port par défaut utilisé, ainsi que toute les commandes utilisées par le client SMTP.
     */

    int DEFAULT_PORT = 25;

    String EHLO = "EHLO ";
    String MAIL_FROM = "MAIL FROM: ";
    String RCPT_TO = "RCPT TO: ";
    String DATA = "DATA";
    String QUIT = "quit";
    String FROM = "From: ";
    String TO = "To: ";
    String CC = "Cc: ";
    String SUBJECT = "Subject: ";
    String RETURN = "\r\n";
    String END_MESSAGE = "\r\n.\r\n";
    String ENCODAGE = "Content-Type: text/plain; charset=utf-8";

    /**
     * permet d'envoyer un email par un socket a un serveur SMTP qui utilise Reader et Writer.
     * @param message l'email a envoyé au serveur SMTP
     * @throws IOException si l'on ne peut pas créer de socket ou si le Reader ou le Writer plante.
     */
    void sendMessage(Message message) throws IOException;

}
