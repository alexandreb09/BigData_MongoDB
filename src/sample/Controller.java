package sample;

import com.mongodb.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class Controller {
    // Interet MONGO DB
    //  - Duppliquer l'info pour des accès immédiats
    //  - Modification de la structure de la BD

    @FXML
    TextField auteur_prenom;
    @FXML
    TextField auteur_nom;
    @FXML
    TextField auteur_domicile;
    @FXML
    TextField auteur_numero;

    @FXML
    TextField livre_numero;
    @FXML
    TextField livre_titre;
    @FXML
    TextField livre_prix;

    @FXML
    TextField relation_titre_livre;
    @FXML
    TextField relation_nom_auteur;

    @FXML
    TextArea logs;

    @FXML
    private void ajout_auteur() throws UnknownHostException {
        BasicDBObject myObject = new BasicDBObject();
        myObject.put("numero", auteur_numero.getText());
        myObject.put("nom", auteur_nom.getText());
        myObject.put("prenom", auteur_prenom.getText());
        myObject.put("domicile", auteur_domicile.getText());

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("myDb");
        DBCollection coll_auteur = db.getCollection("Auteur");

        coll_auteur.insert(myObject);

        clearAuteur();

        String txt = logs.getText();
        logs.setText(txt + "\n" + myObject.toString());
    }

    @FXML
    private void ajout_livre() throws UnknownHostException {
        BasicDBObject myObject = new BasicDBObject();
        myObject.put("numero", livre_numero.getText());
        myObject.put("titre", livre_titre.getText());
        myObject.put("prix", livre_prix.getText());

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("myDb");
        DBCollection coll_livre = db.getCollection("Livre");

        coll_livre.insert(myObject);

        clearLivre();

        String txt = logs.getText();
        logs.setText(txt + "\n" + myObject.toString());
    }

    @FXML
    public void ajoutRelation() throws UnknownHostException {
        String nom_auteur = relation_nom_auteur.getText();
        String titre_livre = relation_titre_livre.getText();

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("myDb");
        DBCollection coll_livre = db.getCollection("A ECRIT");

        DBObject auteur = getAuteur(nom_auteur, db);
        DBObject livre = getLivre(titre_livre, db);

        String txt;

        if (auteur == null){
            txt = logs.getText() + "\n" + "AUCUN AUTEUR TROUVE";
        }else if (livre == null){
            txt = logs.getText() + "\n" +  "AUCUN LIVRE TROUVE";
        }else{
            BasicDBObject myRelation = new BasicDBObject();
            myRelation.put("numero_livre", livre.get("numero"));
            myRelation.put("numero_auteur", auteur.get("numero"));

            coll_livre.insert(myRelation);

            clearRelation();

            txt = logs.getText() + "\n" + myRelation.toString();
        }

        logs.setText(txt);
    }

    private void clearAuteur(){
        auteur_nom.clear();
        auteur_prenom.clear();
        auteur_domicile.clear();
        auteur_numero.clear();
    }

    private void clearLivre(){
        livre_numero.clear();
        livre_prix.clear();
        livre_titre.clear();
    }

    private void clearRelation(){
        relation_nom_auteur.clear();
        relation_titre_livre.clear();
    }

    /**
     * Return one auteur from a given name
     * @param nom: String
     * @param db: database
     * @return auteur: DBObject
     */
    private DBObject getAuteur(String nom, DB db){
        DBCollection coll_auteur = db.getCollection("Auteur");

        Pattern nom_a_rechercher = Pattern.compile(nom, Pattern.CASE_INSENSITIVE);
        BasicDBObject query = new BasicDBObject("nom", nom_a_rechercher);

        return coll_auteur.findOne(query);
    }

    /**
     * Return one libre from a given title
     * @param titre: String
     * @param db: database
     * @return auteur: DBObject
     */
    private DBObject getLivre(String titre, DB db){
        DBCollection coll_auteur = db.getCollection("Livre");

        Pattern nom_a_rechercher = Pattern.compile(titre, Pattern.CASE_INSENSITIVE);
        BasicDBObject query = new BasicDBObject("titre", nom_a_rechercher);
        return coll_auteur.findOne(query);
    }
}
