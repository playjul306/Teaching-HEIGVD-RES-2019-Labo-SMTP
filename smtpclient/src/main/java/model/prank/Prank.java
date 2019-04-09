package model.prank;

import model.mail.Group;
import model.mail.Message;
import model.mail.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet d'instancier une blague à l'aide des différents informations comme l'envoyeur, les receveurs,
 * les copies cachées ou encore le message
 *
 * @author Julien benoit, Volkan Sutcu
 */
public class Prank {

    private Person sender = new Person();
    private Group receivers = new Group();
    private Group copyCarbon = new Group();
    private String message;

    //------------------ Setter --------------

    /**
     * Applique un envoyeur pour de la blague
     * @param sender la personne faisant office d'envoyeur
     */
    public void setSender(Person sender){
        this.sender = sender;
    }

    /**
     * Applique des receveurs de la blague
     * @param receivers les personnes faisant office de receveurs
     */
    public void setReceivers(Group receivers){
        this.receivers.setGroup(receivers);
    }

    /**
     * Applique des personnes en copies cachées
     * @param copyCarbon les personnes mentionnées en copies cachées
     */
    public void setCopyCarbon(Group copyCarbon){
        this.copyCarbon.setGroup(copyCarbon);
    }

    /**
     * Applique le message fourni pour le mail à construire
     * @param message le message contenu dans le mail
     */
    public void setMessage(String message){
        this.message = message;
    }

    //------------------ Getter --------------

    /**
     * Récupère l'envoyeur de la blague
     * @return retourne la personne qui envoit le mail
     */
    public Person getSender(){
        return sender;
    }

    /**
     * Récupère les receveurs de la blague
     * @return le groupe de personnes recevant la blague
     */
    public Group getReceivers(){
        return receivers;
    }

    /**
     * Récupère les personnes mentionnées en copies cachées
     * @return le group de personnes en copies cachées
     */
    public Group getCopyCarbon(){
        return copyCarbon;
    }

    /**
     * Récupère le message du mail
     * @return une chaîne de caractère représentant le message dans le mail
     */
    public String getMessage(){
        return message;
    }

    /**
     * Génère le mail complet avec toutes les informations nécessaires
     * @return le mail construit et prêt à l'envoi
     */
    public Message generateMail(){
        Message mail = new Message();

        // L'envoyeur est récupéré et mentionné
        mail.setFrom(sender.getEmail());

        // Les personnes allant recevoir le mail sont récupérées et ajoutées
        List<String> recptTo = new ArrayList<String>();
        List<Person> memberOfReceivers = receivers.getMember();
        for(Person person : memberOfReceivers){
            recptTo.add(person.getEmail());
        }
        mail.setRcptTo(recptTo);

        // Les personnes en copies cachées sont parcourues et ajoutées
        List<String> cc = new ArrayList<String>();
        List<Person> memberOfCopyCarbon = copyCarbon.getMember();
        for(Person person : memberOfCopyCarbon){
            cc.add(person.getEmail());
        }
        mail.setCopyCarbon(cc);

        // En plus du message complet dans le mail, une signature est ajoutée
        mail.setData(message + "\r\n" + sender.getLastName() + " " + sender.getFirstName());

        return mail;
    }

}
