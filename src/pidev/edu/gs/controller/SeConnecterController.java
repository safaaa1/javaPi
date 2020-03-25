/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.edu.gs.controller;


import pidev.edu.gs.controller.PanelClientController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pidev.edu.gs.entites.Utilisateur;
import pidev.edu.gs.services.UtilisateurService;
import pidev.edu.gs.utils.Password;
/**
 *
 * @author safa
 */
public class SeConnecterController implements Initializable {

    @FXML
    private TextField nomUtilisateur;
    @FXML
    private PasswordField mdp;
    @FXML
    private Button seConnecter;

    public static int idUtilisateur;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("se connecter controller");
    }

    @FXML
    public void connecter(ActionEvent actionEvent) throws SQLException, IOException {
        System.out.println("se connecter action");

        UtilisateurService utilisateurService = new UtilisateurService();
        Utilisateur utilisateur = new Utilisateur();

        utilisateur = utilisateurService.seConnecter(nomUtilisateur.getText());

        Password md = new Password();
        Boolean mdpCrypte = md.checkPassword(mdp.getText(), utilisateur.getPassword());

        if (mdpCrypte == true) {
            System.out.println("authentification reussite");
            idUtilisateur = utilisateur.getId();
            if (utilisateur.getRoles().equals("a:1:{i:0;s:11:\"ROLE_CLIENT\";}")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/pidev/edu/gs/gui/panelClient.fxml"));
                Parent root = loader.load();
                PanelClientController panelClientController = loader.getController();
                mdp.getScene().setRoot(root);
            }else if(utilisateur.getRoles().equals("a:1:{i:0;s:16:\"ROLE_RESPONSABLE\";}")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/pidev/edu/gs/gui/panelAdmin.fxml"));
                Parent root = loader.load();
                PanelAdminController panelAdminController = loader.getController();
                mdp.getScene().setRoot(root);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Warning");
            alert.setContentText("veuillez verifier votre login ou mot de passe ..");
            alert.show();
            System.out.println("veuillez verifier votre login ou mot de passe ..");
        }

    }
    //ddddaaa

    @FXML
    public void sinscrire(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pidev/edu/gs/gui/sinscrire.fxml"));
        Parent root = loader.load();
        SinscrireController sinscrireController = loader.getController();
        nomUtilisateur.getScene().setRoot(root);
    }

}
