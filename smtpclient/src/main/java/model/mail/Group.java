package model.mail;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private List<Person> group;

    public Group(){
        group = new ArrayList<Person>();
    }

    public Group(List<Person> person){
        group.addAll(person);
    }

    public void addPersons(Person person){
        group.add(person);
    }

    public List<Person> getMember(){
        //List<Person> group = new ArrayList<Person>();
        //group.addAll(this.group);
        return group;
    }

    public void setGroup(Group newGroup){
        this.group.clear();
        this.group.addAll(newGroup.group);
    }


}
