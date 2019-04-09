package model.prank;

import config.IConfigurationManager;
import model.mail.Group;
import model.mail.Person;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe permet de générer des blagues en gérant l'attribution des personnes dans les groupes ainsi que les blagues
 * pour chaque groupe
 */
public class PrankGenerator {
    private IConfigurationManager config;
    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());
    private static final int NB_PERSONS_MIN = 3;

    /**
     * Permet de créer une instance du générateur de blagues
     * @param config instance représentant le fichier de configuration
     */
    public PrankGenerator(IConfigurationManager config){
        this.config = config;
    }

    /**
     * Génère une liste de blagues
     * @return la liste de blgues à envoyer
     */
    public List<Prank> generatePranks() {
        List<Prank> listOfPranks = new ArrayList<Prank>();

        // Informations récupérées depuis le fichier de configuration
        int nbGroups = config.getNumberOfGroups();
        int nbVictims = config.getVictims().getSize();

        // Permet de réadapter le nombre de groupe en cas de nombre insuffisant de personnes
        if(nbVictims/nbGroups < nbGroups){
            // Test que le minimum de personnes dans un groupe soit respecté
            if(nbVictims/nbGroups < NB_PERSONS_MIN){
                LOG.log(Level.SEVERE, "Attention, le nombre de personne par groupe est inférieur à {0}. Veuillez modifier cela !", NB_PERSONS_MIN);
                return listOfPranks;
            }
            nbGroups = nbVictims / nbGroups;
        }
        // Une liste de groupes avec les différentes personnes à l'intérieur est créé
        List<Group> generatedGroups = generateGroups(config.getVictims(), nbGroups);

        List<String> messages = config.getMessages();

        // Chaque groupe est parcouru en définissant l'envoyeur, les receveurs ainsi que la blague attribuées
        for(Group group : generatedGroups){
            // Une personne est choisi et sera l'envoyeur
            Random r = new Random();
            int choice = r.nextInt(group.getSize());

            Prank prank = new Prank();
            prank.setSender(group.removePersons(choice));
            // Le reste des personnes du groupes sont les receveurs
            prank.setReceivers(group);
            prank.setCopyCarbon(config.getWitnessesToCC());

            // Un message est attribué au groupe
            choice = r.nextInt(messages.size());
            prank.setMessage(messages.get(choice));

            // la blague est complètement construite et prête à l'envoi
            listOfPranks.add(prank);
        }

        return listOfPranks;
    }

    /**
     * Génère les groupes de personnes qui vont être utilisés pour l'envoi des blagues
     * @param victims correspond aux personnes victimes pour la blague
     * @param nbGroups le nombre de groupe défini dans le fichier de configuration
     * @return une liste des groupes de personnes qui y sont ajoutées
     */
    public List<Group> generateGroups(Group victims, int nbGroups){
        // Liste des groupes à retourner ainsi qu'une liste temporaire permettant les attributions de groupes
        List<Group> listOfGroups = new ArrayList<Group>(nbGroups);
        List<Group> groups = new ArrayList<Group>(nbGroups);

        int tmpNbGroups = nbGroups;
        List<Person> listOfPerson = victims.getMember();

        // La liste des groupes est initialisée
        for(int i = 0; i < nbGroups; ++i){
            groups.add(new Group());
        }

        // On parcourt chaque personne et l'attribue à un groupe aléatoirement
        for(Person pers : listOfPerson){
            Random r = new Random();
            int choice = r.nextInt(groups.size());

            // Permet de vérifier si un groupe est plein ou non
            while(groups.get(choice).getSize() >= (listOfPerson.size()/tmpNbGroups)) {
                // Le groupe plein est ajouté à la liste puis supprimé de la liste temporaire
                listOfGroups.add(groups.get(choice));
                groups.remove(choice);
                // Choisi un autre groupe disponible
                choice = r.nextInt(groups.size());
            }
            // Ajoute la personne au group choisi
            groups.get(choice).addPersons(pers);
        }
        // Ajoute les groupes restants
        listOfGroups.addAll(groups);

        return listOfGroups;
    }
}
