package model.mail;

/**
 * Classe représentants une personne dans le cadre d'un envoie d'email.
 *
 * @author Julien benoit, Volkan Sutcu
 */

public class Person {

    private String lastName;
    private String firstName;
    private String email;

    /**
     * constructeur par defaut
     */
    public Person(){}

    /**
     * permet de créer une personne à partir d'un email, c'est à dire d'extraire son prénom et son nom et d'y mettre la
     * première lettre en majuscule. Ainsi que de llui attribuer un email.
     * @param email l'email de la personne.
     */
    public Person(String email){
        String completeName = email.substring(0, email.indexOf('@'));
        lastName = completeName.substring(completeName.indexOf('.') + 1);
        lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1);
        firstName = completeName.substring(0, completeName.indexOf('.'));
        firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1);
        this.email = email;
    }

    //--------------Getter---------------

    /**
     * permet de récuperer le nom de famille d'une personne.
     * @return le nom de famille d'une personne.
     */
    public String getLastName(){
        return lastName;
    }

    /**
     * permet de récuperer le prénom d'une personne.
     * @return le prénom d'une personne.
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     * permet de récuperer l'email d'une personne.
     * @return l'email d'une personne.
     */
    public String getEmail(){
        return email;
    }


    //--------------Setter---------------

    /**
     * permet de setter le nom de famille d'une personne.
     * @param lastName le nom de famille d'une personne.
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * permet de setter le prénom d'une personne.
     * @param firstName le prénom d'une personne.
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
     * permet de setter l'email d'une personne.
     * @param email l'email d'une personne.
     */
    public void setEmail(String email){
        this.email = email;
    }

}
