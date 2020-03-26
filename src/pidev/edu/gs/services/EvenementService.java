/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.edu.gs.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pidev.edu.gs.entites.Evenement;
import pidev.edu.gs.utils.ConnectionBD;

/**
 *
 * @author safa
 */
public class EvenementService {
    
    Connection cnx = ConnectionBD.getInstance().getCnx();
    
    public void add(Evenement evenement){
        
        String req = "insert into evenement (nom, type, date, nbrPlace, dressCode,image) values(?, ?, ?, ?, ?, ?);";
        try {
            java.util.Date dateeve = new Date(evenement.getDate().getTime());
                PreparedStatement pst = cnx.prepareStatement(req);
                pst.setString(1, evenement.getNom());
                pst.setString(2, evenement.getType());
                pst.setDate(3, (Date)dateeve);
                pst.setInt(4, evenement.getNbrPlace());
                pst.setString(5, evenement.getDressCode());
                pst.setString(6, evenement.getImage());
                pst.executeUpdate();
                System.out.println("evenement crée!");
            } catch (SQLException ex) {
            }
    }
    
    public ObservableList<Evenement> afficher() {

        ObservableList<Evenement> list = FXCollections.observableArrayList();
        String req = "select id,nom,type,date,nbrPlace,dressCode,image from evenement;";

        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Evenement p = new Evenement(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getInt(4),rs.getString(5),rs.getString(6));
                //Personne p = new Personne(rs.getInt("id"), rs.getString(2), rs.getString(3));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("liste evenements recupere");
        return list;

    }

    
}
