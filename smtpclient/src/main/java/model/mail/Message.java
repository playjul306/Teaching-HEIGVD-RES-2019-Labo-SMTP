package model.mail;

import java.util.List;

/**
 * Classe représentants des messages a envoyer.
 *
 * @author Julien benoit, Volkan Sutcu
 */

public class Message {

    private String from;
    private List<String> rcptTo;
    private List<String> copyCarbon;
    private String subject;
    private String data;

    //--------------Getter---------------

    /**
     * permet de récuperer l'email de l'envoyeur.
     * @return l'email de l'envoyeur.
     */
    public String getFrom(){
        return from;
    }

    /**
     * permet de récupérer le sujet de l'email.
     * @return le sujet de l'email.
     */
    public String getSubject(){
        return subject;
    }

    /**
     * permet de récupérer le corps de l'email.
     * @return le corps de l'email.
     */
    public String getData(){
        return data;
    }

    /**
     * permet de récuperer la liste contenant les destinataires.
     * @return la liste contenant les destinataires.
     */
    public List<String> getRcptTo(){
        return rcptTo;
    }

    /**
     * permet de récuperer la liste contenant les destinataires en copie.
     * @return la liste contenant les destinataires en copie.
     */
    public List<String> getCopyCarbon(){
        return copyCarbon;
    }

    //--------------Setter---------------

    /**
     * permet de setter l'email de l'envoyeur.
     * @param from l'email de l'envoyeur.
     */
    public void setFrom(String from){
        this.from = from;
    }

    /**
     * permet de setter le sujet de l'email.
     * @param subject le sujet de l'email.
     */
    public void setSubject(String subject){
        this.subject = subject;
    }

    /**
     * permet de setter le corps de l'email.
     * @param data le corps de l'email.
     */
    public void setData(String data){
        this.data = data;
    }

    /**
     * permet de setter la liste contenant les destinataires.
     * @param rcptTo la liste contenant les destinataires.
     */
    public void setRcptTo(List<String> rcptTo){
        this.rcptTo = rcptTo;
    }

    /**
     * permet de setter la liste contenant les destinataires en copie.
     * @param copyCarbon liste contenant les destinataires en copie.
     */
    public  void setCopyCarbon(List<String> copyCarbon){
        this.copyCarbon = copyCarbon;
    }
}
