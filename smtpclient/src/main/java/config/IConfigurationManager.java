package config;

import model.mail.Group;

import java.util.List;

public interface IConfigurationManager {

    String defaultConfigPath = "../smtpclient/config/";

    // Informations venant du fichier de configuration
    String field_smtpServerAddress = "smtpServerAddress";
    String field_smtpServerPort = "smtpServerPort";
    String field_numberOfGroups=  "numberOfGroups";
    String field_witnessesToCC = "witnessesToCC";

    String configFileName = "config.properties";
    String victimsFileName = "victims.utf8";
    String messagesFileName = "messages.utf8";

    // Les méthodes suivantes ont été commentées dans le fichier associé
    String getSmtpServerAdress();

    int getSmtpServerPort();

    int getNumberOfGroups();

    Group getWitnessesToCC();

    List<String> getMessages();

    Group getVictims();
}
