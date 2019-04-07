package config;

import model.mail.Group;
import model.mail.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigurationManager implements IConfigurationManager {
    private Properties properties;
    private List<String> messages;
    private Group victims;

    public ConfigurationManager() throws IOException {
        this(defaultConfigPath);
    }

    public ConfigurationManager(String configFilesPath) throws IOException {
        properties = loadProperties(configFilesPath);
        messages = loadMessages(configFilesPath);
        victims = loadVictims(configFilesPath);
    }

    private Properties loadProperties(String configFilesPath) throws IOException{
        properties = new Properties();
        properties.load(new FileInputStream(configFilesPath + configFileName));
        return properties;
    }

    private List<String> loadMessages(String configFilesPath) throws IOException{
        messages = new ArrayList<String>();
        FileInputStream loadFile = new FileInputStream(configFilesPath + messagesFileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(loadFile, "UTF-8"));
        String completeMessage = "", line = "";
        while((line = reader.readLine()) != null){
            if(line.equals("==")){
                messages.add(completeMessage);
            }else{
                completeMessage += line + "\r\n";
            }
        }
        loadFile.close();
        reader.close();
        return messages;
    }

    private Group loadVictims(String configFilesPath) throws IOException{
        victims = new Group();
        FileInputStream loadFile = new FileInputStream(configFilesPath + victimsFileName);
        String line = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(loadFile, "UTF-8"));
        while((line = reader.readLine()) != null){
            victims.addPersons(new Person(line));
        }
        loadFile.close();
        reader.close();
        return victims;
    }

    public String getSmtpServerAdress(){
        return properties.getProperty(field_smtpServerAddress);
    }

    public int getSmtpServerPort(){
        return Integer.parseInt(properties.getProperty(field_smtpServerPort));
    }

    public int getNumberOfGroups(){
        return Integer.parseInt(properties.getProperty(field_numberOfGroups));
    }

    public Group getWitnessesToCC(){
        String[] witnesses = properties.getProperty(field_witnessesToCC).split(",");
        Group groupOfWitnesses = new Group();
        for(String eachWitness : witnesses){
            groupOfWitnesses.addPersons(new Person(eachWitness));
        }
        return groupOfWitnesses;
    }

    public List<String> getMessages(){
        return messages;
    }

    public Group getVictims(){
        return victims;
    }

}
