package config;

import model.mail.Group;
import model.mail.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Cette classe permet d'instancier un objet contenant les fichiers externes fournis et qui seront utilisés lors de
 * l'envoi des mails pour les différentes blagues
 */
public class ConfigurationManager implements IConfigurationManager {
    private Properties properties;
    private List<String> messages;
    private Group victims;

    /**
     * Permet d'instancier un objet contenant les informations fournies pour le serveur via le chemin par défaut
     * @throws IOException levée en cas d'erreur lors du chargement des fichiers
     */
    public ConfigurationManager() throws IOException {
        this(defaultConfigPath);
    }

    /**
     * Permet d'instancier un objet contenant les informations fournies pour le serveur via un chemin
     * @param configFilesPath chemin où se trouve les différents fichiers
     * @throws IOException levée en cas d'erreur lors du chargement des fichiers
     */
    public ConfigurationManager(String configFilesPath) throws IOException {
        properties = loadProperties(configFilesPath);
        messages = loadMessages(configFilesPath);
        victims = loadVictims(configFilesPath);
    }

    /**
     * Permet de charger les informations de configuration
     * @param configFilesPath chemin où se trouve les différents fichiers
     * @return une instance contenant les informations de configuration
     * @throws IOException levée en cas d'erreur lors du chargement du fichier de configuration
     */
    private Properties loadProperties(String configFilesPath) throws IOException{
        properties = new Properties();
        properties.load(new FileInputStream(configFilesPath + configFileName));
        return properties;
    }

    /**
     * Permet de charger les blagues fournies
     * @param configFilesPath chemin où se trouve les différents fichiers
     * @return une liste des différentes blague récupérées
     * @throws IOException levée en cas d'erreur lors du chargement du fichier de blagues
     */
    private List<String> loadMessages(String configFilesPath) throws IOException{
        messages = new ArrayList<String>();
        FileInputStream loadFile = new FileInputStream(configFilesPath + messagesFileName);
        // Instancie un lecteur sur le fichier
        BufferedReader reader = new BufferedReader(new InputStreamReader(loadFile, "UTF-8"));
        String completeMessage = "", line = "";
        // On récupère les blagues séparées par un "==" tant que la ligne lue contient quelque chose
        while((line = reader.readLine()) != null){
            if(line.equals("==")){
                messages.add(completeMessage);
                completeMessage = "";
            }else{
                completeMessage += line + "\r\n";
            }
        }
        loadFile.close();
        reader.close();
        return messages;
    }

    /**
     * Permet de charger les victimes des blagues
     * @param configFilesPath chemin où se trouve les différents fichiers
     * @return un groupe où se trouve toutes les personnes du fichier
     * @throws IOException levée en cas d'erreur lors du chargement du fichier de victimes
     */
    private Group loadVictims(String configFilesPath) throws IOException{
        victims = new Group();
        FileInputStream loadFile = new FileInputStream(configFilesPath + victimsFileName);
        String line = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(loadFile, "UTF-8"));
        // Chaque ligne lu et contenant une victime est ajouté à la liste
        while((line = reader.readLine()) != null){
            victims.addPersons(new Person(line));
        }
        loadFile.close();
        reader.close();
        return victims;
    }

    /**
     * Récupère l'adresse du serveur smtp
     * @return la chaîne de caractère représentant le serveur smtp
     */
    public String getSmtpServerAdress(){
        return properties.getProperty(field_smtpServerAddress);
    }

    /**
     * Récupère le port du serveur smtp
     * @return le numéro de port du serveur smtp
     */
    public int getSmtpServerPort(){
        return Integer.parseInt(properties.getProperty(field_smtpServerPort));
    }

    /**
     * Récupère le nombre de groupe à former pour les blagues
     * @return le nombre de groupe à former
     */
    public int getNumberOfGroups(){
        return Integer.parseInt(properties.getProperty(field_numberOfGroups));
    }

    /**
     * Récupère le groupe de personne à mettre en copie cachée dans le mail
     * @return le groupe de personnes pour la copie cachée
     */
    public Group getWitnessesToCC(){
        String[] witnesses = properties.getProperty(field_witnessesToCC).split(",");
        Group groupOfWitnesses = new Group();
        // Chaque personne mentionnée pour la copie cachée est ajouté dans la liste pour ce rôle
        for(String eachWitness : witnesses){
            groupOfWitnesses.addPersons(new Person(eachWitness));
        }
        return groupOfWitnesses;
    }

    /**
     * Récupère les messages sous forme d'une liste
     * @return la liste des messages
     */
    public List<String> getMessages(){
        return messages;
    }

    /**
     * Récupère les victimes du mail
     * @return un groupe de personnes représentant les victimes de la blague
     */
    public Group getVictims(){
        return victims;
    }

}
