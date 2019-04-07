package config;

import model.mail.Group;

import java.util.List;

public interface IConfigurationManager {

    String defaultConfigPath = "../smtpclient/config/";

    String field_smtpServerAddress = "smtpServerAddress";
    String field_smtpServerPort = "smtpServerPort";
    String field_numberOfGroups=  "numberOfGroups";
    String field_witnessesToCC = "witnessesToCC";

    String configFileName = "config.properties";
    String victimsFileName = "victims.utf8";
    String messagesFileName = "messages.utf8";


    String getSmtpServerAdress();

    int getSmtpServerPort();

    int getNumberOfGroups();

    Group getWitnessesToCC();

    List<String> getMessages();

    Group getVictims();
}
