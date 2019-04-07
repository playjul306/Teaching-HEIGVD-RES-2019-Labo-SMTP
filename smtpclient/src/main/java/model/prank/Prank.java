package model.prank;

import model.mail.Group;
import model.mail.Message;
import model.mail.Person;

import java.util.ArrayList;
import java.util.List;

public class Prank {

    private Person sender = new Person();
    private Group receivers = new Group();
    private Group copyCarbon = new Group();
    private String message;

    //------------------ Setter --------------
    public void setSender(Person sender){
        this.sender = sender;
    }

    public void setReceivers(Group receivers){
        this.receivers.setGroup(receivers);
    }

    public void setCopyCarbon(Group copyCarbon){
        this.copyCarbon.setGroup(copyCarbon);
    }

    public void setMessage(String message){
        this.message = message;
    }

    //------------------ Getter --------------
    public Person getSender(){
        return sender;
    }

    public Group getReceivers(){
        return receivers;
    }

    public Group getCopyCarbon(){
        return copyCarbon;
    }

    public String getMessage(){
        return message;
    }

    public Message generateMail(){
        Message mail = new Message();

        mail.setFrom(sender.getEmail());

        // Récupère les informations pour le champ qui concerne les destinataires
        List<String> recptTo = new ArrayList<String>();
        List<Person> memberOfReceivers = receivers.getMember();
        for(Person person : memberOfReceivers){
            recptTo.add(person.getEmail());
        }
        mail.setRcptTo(recptTo);

        // Récupère les informations pour le champ qui concerne les copies cachées
        List<String> cc = new ArrayList<String>();
        List<Person> memberOfCopyCarbon = copyCarbon.getMember();
        for(Person person : memberOfCopyCarbon){
            cc.add(person.getEmail());
        }
        mail.setCopyCarbon(cc);

        mail.setData(message + "\r\n" + sender.getLastName() + " " + sender.getFirstName());

        return mail;
    }

}
