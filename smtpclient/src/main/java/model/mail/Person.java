package model.mail;

public class Person {

    private String lastName;
    private String firstName;
    private String email;

    public Person(){}

    public Person(String email){
        String completeName = email.substring(0, email.indexOf('@'));
        lastName = completeName.substring(completeName.indexOf('.'));
        firstName = completeName.substring(0, completeName.indexOf('.'));
        this.email = email;
        lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1);
        firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1);
    }

    //--------------Getter---------------

    public String getLastName(){
        return lastName;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getEmail(){
        return email;
    }


    //--------------Setter---------------

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setEmail(String email){
        this.email = email;
    }

}
