package com.example.emploidutemps.controllers;

import com.example.emploidutemps.helpers.DbConnect;
import com.example.emploidutemps.models.Cours;
import com.example.emploidutemps.models.Enseignant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoursController implements Initializable {
    @FXML
    private Label alertText;
    @FXML
    private TextField idCours;
    @FXML
    private ComboBox classComboBox1;
    @FXML
    private ComboBox classComboBox2;
    @FXML
    private TextField subjectField;
    @FXML
    private TableColumn<Cours, String> classeCol;
    @FXML
    private TableColumn<Cours, String> idCol;
    @FXML
    private TableColumn<Cours, String> subjectCol;
    @FXML
    private TableColumn<Cours, String> nom;
    @FXML
    private TableColumn<Cours, String> teacherContactCol;
    @FXML
    private TableColumn<Cours, String> hourCol;
    @FXML
    private TableColumn<Cours, String> dayCol;
    @FXML
    private TableView<Cours> courseTable;
    ObservableList<Cours> cours = FXCollections.observableArrayList();

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    @FXML
    void loadData() {
        refreshCoursTable();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        classeCol.setCellValueFactory(new PropertyValueFactory<>("classe"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("matiere"));
        dayCol.setCellValueFactory(new PropertyValueFactory<>("jour"));
        hourCol.setCellValueFactory(new PropertyValueFactory<>("heure"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        teacherContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }
@FXML
    private void refreshCoursTable() {
        connection = DbConnect.getConnect();
        cours.clear();
        classComboBox2.setValue(null);
        classComboBox1.setValue(null);
        subjectField.setText(null);
        try {


            query = "SELECT `cours`.id,`cours`.classe,`cours`.matiere,`cours`.Jour,`cours`.heure,`ens`.nom `ens_contact` ,`ens`.contact  FROM `tb_cours` as `cours` join `tb_enseignant` as `ens` on `ens`.`matricule` = `cours`.`matricule_ens` ";

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                cours.add( new Cours(
                        resultSet.getInt("id"),
                        resultSet.getString("classe"),
                        resultSet.getString("matiere"),
                        resultSet.getString("jour"),
                        resultSet.getString("heure"),

                        resultSet.getString("ens_contact"),
                        resultSet.getString("contact")


                ));
                courseTable.setItems(cours);


            }


        } catch (SQLException ex) {
            System.out.println("error here" + ex);
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    public void removeAlert(MouseEvent mouseEvent) {
        alertText.setText("");
    }



    public  void  searchCours1() {

        if ( classComboBox1.getValue() == null) {
            alertText.setText("Classe est obligatoire X");
        }else if (subjectField.getText().isEmpty()) {

            alertText.setText("Matiere est obligatoire X");
        }
        else {
            try {
                query = "SELECT `cours`.id,`cours`.classe,`cours`.matiere,`cours`.Jour,`cours`.heure,`ens`.nom `ens_contact` ,`ens`.contact  FROM `tb_cours` as `cours` join `tb_enseignant` as `ens` on `ens`.`matricule` = `cours`.`matricule_ens` where `classe`=? and `matiere`=?";
                connection = DbConnect.getConnect();
                cours.clear();

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, classComboBox1.getValue().toString());
                preparedStatement.setString(2, subjectField.getText().toUpperCase());


                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {

                    cours.add( new Cours(
                            resultSet.getInt("id"),
                            resultSet.getString("classe"),
                            resultSet.getString("matiere"),
                            resultSet.getString("jour"),
                            resultSet.getString("heure"),

                            resultSet.getString("ens_contact"),
                            resultSet.getString("contact")


                    ));
                    courseTable.setItems(cours);


                }





            } catch (SQLException ex) {
                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    public  void  searchCours2() {

        if ( classComboBox2.getValue() == null) {
            alertText.setText("Classe est obligatoire X");
        }
        else {
            try {
                query = "SELECT `cours`.id,`cours`.classe,`cours`.matiere,`cours`.Jour,`cours`.heure,`ens`.nom `ens_contact` ,`ens`.contact  FROM `tb_cours` as `cours` join `tb_enseignant` as `ens` on `ens`.`matricule` = `cours`.`matricule_ens` where `classe`=?";
                connection = DbConnect.getConnect();
                cours.clear();

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, classComboBox2.getValue().toString());


                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {

                    cours.add( new Cours(
                            resultSet.getInt("id"),
                            resultSet.getString("classe"),
                            resultSet.getString("matiere"),
                            resultSet.getString("jour"),
                            resultSet.getString("heure"),

                            resultSet.getString("ens_contact"),
                            resultSet.getString("contact")


                    ));
                    courseTable.setItems(cours);


                }





            } catch (SQLException ex) {
                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    public void deleteCours(MouseEvent mouseEvent) {
        if (idCours.getText().isEmpty()) {
            alertText.setText("ID est obligatoire X");
        }
        else{
            try {
                int id = Integer.parseInt( idCours.getText());  // Parse the string to an integer
                System.out.println("Parsed number: " + id);
                try {
                    query = "SELECT * FROM `tb_cours` WHERE `id`=?";
                    connection = DbConnect.getConnect();


                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, idCours.getText());

                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        query = "DELETE  FROM `tb_cours` WHERE `id` = ? ";
                        connection = DbConnect.getConnect();


                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1,id);
                        preparedStatement.execute();
                        idCours.setText("");

                        refreshCoursTable();
                        alertText.setText("Cours supprimé X ");

                    }else{

                        alertText.setText("Cours introuvable X");
                    }






                } catch (SQLException ex) {
                    Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (NumberFormatException e) {
                alertText.setText("Format invalide d'ID X");
                System.out.println("Invalid number format");
            }

        }


    }
}
