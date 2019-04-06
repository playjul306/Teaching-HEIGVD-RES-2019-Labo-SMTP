package model.mail;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private List<Person> group = new ArrayList<Person>();

    public Group(){}

    public Group(List<Person> person){
        group.addAll(person);
    }

    public void addPersons(Person person){
        group.add(person);
    }

    public List<Person> getMember(){
        List<Person> group = new ArrayList<Person>();
        group.addAll(this.group);
        return group;
    }

    public void setGroup(List<Person> group){
        this.group.clear();
        this.group.addAll(group);
    }


}
