package smtp;

import config.IConfigurationManager;
import model.mail.Message;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmtpClient implements ISmtpClient{

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private String smtpServerAddress;
    private int smtpServerPort = DEFAULT_PORT;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public SmtpClient(String smtpServerAddress){
        this(smtpServerAddress, DEFAULT_PORT);
    }

    public SmtpClient(String smtpServerAddress, int smtpServerPort){
        this.smtpServerAddress = smtpServerAddress;
        this.smtpServerPort = smtpServerPort;
    }

    public SmtpClient(IConfigurationManager configuration){
        this.smtpServerAddress = configuration.getSmtpServerAdress();
        this.smtpServerPort = configuration.getSmtpServerPort();
    }

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
        String line = reader.readLine();
        if (!line.startsWith("250")){
            throw new IOException("Erreur SMTP : " + line);
        }
        while (line.startsWith("250")){
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
        writer.print(TO + message.getCopyCarbon().get(0));
        for (int i = 1; i < message.getCopyCarbon().size(); ++i){
            writer.print(", " + message.getCopyCarbon().get(i));
        }
        writer.print(RETURN);
        writer.flush();

        LOG.info("---------- CORPS ----------");

        LOG.log(Level.INFO, "BODY");
        String[] subjectAndMessage = message.getData().split("\r\n|\r|\n", 2);
        message.setSubject(subjectAndMessage[0]);
        message.setData(subjectAndMessage[1]);
        writer.print(SUBJECT + message.getSubject() + RETURN);
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
