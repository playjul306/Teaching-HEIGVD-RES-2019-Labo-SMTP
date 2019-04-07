import config.*;
import model.prank.*;
import smtp.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.*;

public class MailRobot {
    private static final Logger LOG = Logger.getLogger(MailRobot.class.getName());

    public static void main(String[] args){
        IConfigurationManager config;
        PrankGenerator prankGenerator;
        List<Prank> prankList;
        ISmtpClient client;

        try {
            config = new ConfigurationManager();
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
