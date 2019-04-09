package model.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant des groupes de personnes pour le cas de notre serveur SMTP, afin d'envoyer des
 * "prank" par email a des groupes. Chaque groupe est une liste de personnes.
 *
 * @author Julien benoit, Volkan Sutcu
 */

public class Group {
    private List<Person> group;

    /**
     * Constructeur permettant d'initialiser un groupe en liste de personne.
     */
    public Group(){
        group = new ArrayList<Person>();
    }

    /**
     * constructeur permettant d'ajouter toute la liste de personne au groupe.
     * @param person la liste de personne à ajouter au groupe.
     */
    public Group(List<Person> person){
        group.addAll(person);
    }

    /**
     * fonction permettant d'ajouter une personne à un groupe.
     * @param person la pesronne à ajouter au groupe.
     */
    public void addPersons(Person person){
        group.add(person);
    }

    /**
     * Fonction permettant de supprimer une personne d'un groupe.
     * @param index l'index de la personne à supprimer dans le groupe.
     * @return la personne qui a été supprimée ou null s'il n'y en avait pas.
     */
    public Person removePersons(int index){
        return getSize() > 0 && index >= 0 && index < getSize() ? group.remove(index) : null;
    }

    /**
     * fonction permettant d'obtenir la liste de personnes membres du groupe.
     * @return la liste de personnes
     */
    public List<Person> getMember(){
        return group;
    }

    /**
     * fonction permettant d'effacer un groupe et de le setter avec un nouveau groupe.
     * @param newGroup le nouveau groupe a setter.
     */
    public void setGroup(Group newGroup){
        this.group.clear();
        this.group.addAll(newGroup.group);
    }

    /**
     * fonction permettant d'obtenir la nombre de personnes dans un groupe.
     * @return le nombre de personne d'un groupe.
     */
    public int getSize(){
        return group.size();
    }


}
