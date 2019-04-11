import config.*;
import model.prank.*;
import smtp.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.*;


/**
 * Cette application permet de créer des "pranks" à partir d'un fichier de config afin de les envoyer par email via
 * un client SMTP.
 * Ces emails sont envoyés par une "victime" choisie au hasard a un groupe de victimes créé préalablement,
 * au hasard aussi.
 *
 * @author Julien benoit, Volkan Sutcu
 */

public class MailRobot {
    private static final Logger LOG = Logger.getLogger(MailRobot.class.getName());

    public static void main(String[] args){
        IConfigurationManager config;
        PrankGenerator prankGenerator;
        List<Prank> prankList;
        ISmtpClient client;

        try {
            config = new ConfigurationManager(args[0]);
            prankGenerator = new PrankGenerator(config);
            prankList = prankGenerator.generatePranks();
            client = new SmtpClient(config);

            for (Prank p : prankList){
                client.sendMessage(p.generateMail());
            }

        } catch (IOException e){
            LOG.log(Level.SEVERE, "Impossible d'envoyer le prank : {0}", e.getMessage());
            e.printStackTrace();
        }
        
    }
}
