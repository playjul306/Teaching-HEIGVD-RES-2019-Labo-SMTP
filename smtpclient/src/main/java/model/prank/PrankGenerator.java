package model.prank;

import config.IConfigurationManager;
import model.mail.Group;
import model.mail.Person;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrankGenerator {
    private IConfigurationManager config;
    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());
    private static final int NB_PERSONS_MIN = 3;

    public PrankGenerator(IConfigurationManager config){
        this.config = config;
    }

    public List<Prank> generatePranks() {
        List<Prank> listOfPranks = new ArrayList<Prank>();

        int nbGroups = config.getNumberOfGroups();
        int nbVictims = config.getVictims().getSize();

        // Permet de réadapter le nombre de groupe en cas de nombre insuffisant de personnes
        if(nbVictims/nbGroups < nbGroups){
            if(nbVictims/nbGroups < NB_PERSONS_MIN){
                LOG.log(Level.SEVERE, "Attention, nombre de personne par groupe inférieur à {0}. Veuillez modifier cela", NB_PERSONS_MIN);
                return listOfPranks;
            }
            nbGroups = nbVictims / nbGroups;
        }
        List<Group> generatedGroups = generateGroups(config.getVictims(), nbGroups);

        List<String> messages = config.getMessages();

        for(Group group : generatedGroups){
            Random r = new Random();
            int choice = r.nextInt(group.getSize());

            Prank prank = new Prank();
            prank.setSender(group.removePersons(choice));
            prank.setReceivers(group);
            prank.setCopyCarbon(config.getWitnessesToCC());

            choice = r.nextInt(messages.size());
            prank.setMessage(messages.get(choice));

            listOfPranks.add(prank);
        }

        return listOfPranks;
    }

    public List<Group> generateGroups(Group victims, int nbGroups){
        List<Group> listOfGroups = new ArrayList<Group>(nbGroups);
        List<Group> groups = new ArrayList<Group>(nbGroups);

        int tmpNbGroups = nbGroups;
        List<Person> listOfPerson = victims.getMember();

        for(int i = 0; i < nbGroups; ++i){
            groups.add(new Group());
        }


        for(Person pers : listOfPerson){
            Random r = new Random();
            int choice = r.nextInt(groups.size());

            while(groups.get(choice).getSize() >= (listOfPerson.size()/tmpNbGroups)) {
                listOfGroups.add(groups.get(choice));
                groups.remove(choice);
                choice = r.nextInt(groups.size());
            }
            groups.get(choice).addPersons(pers);
        }
        listOfGroups.addAll(groups);

        return listOfGroups;
    }
}
