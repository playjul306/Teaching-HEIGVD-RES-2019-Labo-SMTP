package smtp;

import config.IConfigurationManager;
import model.mail.Message;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Base64;

/**
 * Classe représentants un client SMTP afin d'envoyer des mail à travers un serveur SMTP.
 *
 * @author Julien benoit, Volkan Sutcu
 */

public class SmtpClient implements ISmtpClient{

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private String smtpServerAddress;
    private int smtpServerPort = DEFAULT_PORT;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    /**
     * Constructeur pour un client SMTP dont on ne veut pas spécifier le port et donc utilisé le port par défaut.
     * On ne spécifie que l'adresse IP.
     * @param smtpServerAddress l'adresse IP du serveur SMTP
     */
    public SmtpClient(String smtpServerAddress){
        this(smtpServerAddress, DEFAULT_PORT);
    }

    /**
     * Constructeur pour un client SMTP dont on donne l'adresse IP et le port du serveur SMTP
     * @param smtpServerAddress l'adresse IP du serveur SMTP
     * @param smtpServerPort le port du serveur SMTP
     */
    public SmtpClient(String smtpServerAddress, int smtpServerPort){
        this.smtpServerAddress = smtpServerAddress;
        this.smtpServerPort = smtpServerPort;
    }

    /**
     * Constructeur pour un client SMTP plus avancé disposant d'une configuration spécifique
     * @param configuration la IConfigurationManager permettant de créer un client SMTP
     */
    public SmtpClient(IConfigurationManager configuration){
        this.smtpServerAddress = configuration.getSmtpServerAdress();
        this.smtpServerPort = configuration.getSmtpServerPort();
    }

    /**
     * permet d'envoyer un email par un socket a un serveur SMTP qui utilise Reader et Writer.
     * @param message l'email a envoyé au serveur SMTP
     * @throws IOException si l'on ne peut pas créer de socket ou si le Reader ou le Writer plante.
     */
    public void sendMessage(Message message) throws IOException{
        LOG.info("Envoi de mail par SMTP");

        socket = new Socket(smtpServerAddress,smtpServerPort);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


        LOG.log(Level.INFO, reader.readLine());
        LOG.info("---------- TEST EHLO ----------");
        writer.print(EHLO + "local" + RETURN);
        writer.flush();

        LOG.log(Level.INFO, reader.readLine());

        if (!reader.readLine().startsWith("250")){
            throw new IOException("Erreur SMTP : " + reader.readLine());
        }
        String line;
        while (!(line = reader.readLine()).startsWith("250 ")){
            LOG.log(Level.INFO, line);
        }

        LOG.info("---------- MAIL FROM ----------");
        writer.print(MAIL_FROM + message.getFrom() + RETURN);
        writer.flush();
        LOG.log(Level.INFO, reader.readLine());

        LOG.info("---------- RCPT TO ----------");

        for (String to : message.getRcptTo()){
            writer.print(RCPT_TO + to + RETURN);
            writer.flush();
            LOG.log(Level.INFO, reader.readLine());
        }

        for (String cc : message.getCopyCarbon()){
            writer.print(RCPT_TO + cc + RETURN);
            writer.flush();
            LOG.log(Level.INFO, reader.readLine());
        }

        LOG.info("---------- DATA ----------");
        writer.print(DATA + RETURN);
        writer.flush();
        LOG.log(Level.INFO, reader.readLine());

        // set l'encodage défini dans l'interface pour le corps du mail.
        writer.print(ENCODAGE + RETURN);
        writer.flush();

        LOG.info("---------- EN-TETE ----------");

        LOG.log(Level.INFO, "FROM");
        writer.print(FROM + message.getFrom() + RETURN);
        writer.flush();

        LOG.log(Level.INFO, "TO");
        writer.print(TO + message.getRcptTo().get(0));
        for (int i = 1; i < message.getRcptTo().size(); ++i){
            writer.print(", " + message.getRcptTo().get(i));
        }
        writer.print(RETURN);
        writer.flush();

        LOG.log(Level.INFO, "CC");
        writer.print(CC + message.getCopyCarbon().get(0));
        for (int i = 1; i < message.getCopyCarbon().size(); ++i){
            writer.print(", " + message.getCopyCarbon().get(i));
        }
        writer.print(RETURN);
        writer.flush();

        LOG.info("---------- CORPS ----------");

        LOG.log(Level.INFO, "SUBJECT");
        String[] subjectAndMessage = message.getData().split("\r\n|\r|\n", 2);
        // permet de ne pas avoir deux fois "subject" écrit dans le cas ou le fichier de config des messages
        // comporterait déjà ce terme au début du message.
        if(subjectAndMessage[0].startsWith(SUBJECT))
            subjectAndMessage[0] = subjectAndMessage[0].substring(SUBJECT.length());
        // permet d'afficher le sujet avec les bon caractère en UTF-8 (exemple : les accents).
        message.setSubject("=?utf-8?B?" + Base64.getEncoder().encodeToString(subjectAndMessage[0].getBytes()) + "?=");
        message.setData(subjectAndMessage[1]);
        writer.print(SUBJECT + message.getSubject() + RETURN);
        writer.flush();

        LOG.log(Level.INFO, "BODY");
        writer.print(RETURN + message.getData() + RETURN);
        writer.flush();

        LOG.log(Level.INFO, "END");
        writer.print(END_MESSAGE);
        writer.flush();
        LOG.log(Level.INFO, reader.readLine());

        LOG.info("---------- QUIT ----------");

        writer.print(QUIT + RETURN);
        writer.flush();
        LOG.log(Level.INFO, reader.readLine());

        writer.close();
        reader.close();
        socket.close();






    }
}
