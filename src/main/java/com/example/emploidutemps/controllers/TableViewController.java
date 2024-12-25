package com.example.emploidutemps.controllers;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.example.emploidutemps.helpers.DbConnect;
import com.example.emploidutemps.models.Enseignant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.IDN;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class TableViewController implements Initializable {


    @FXML
    private TextField teacherNameField;
    @FXML
    private TextField teacherContactField;
    @FXML
    private TextField teacherIdField;

    @FXML
    private Label alertText;
    @FXML
    private TableView<Enseignant> enseignantTable;
    @FXML
    private TableColumn<Enseignant, String> matriculeCol;
    @FXML
    private TableColumn<Enseignant, String> nameCol;
    @FXML
    private TableColumn<Enseignant, String> contactCol;

    String query = null;
    String selectedMatricule = "";
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatementMeet = null;
    ResultSet resultSet = null;

    ObservableList<Enseignant> enseignants = FXCollections.observableArrayList();
    private boolean check;
    private boolean foundRecord;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDate();
    }


    @FXML
    private void refreshTable() {
        connection = DbConnect.getConnect();
        enseignants.clear();
        //meets.clear();

        try {


            query = "SELECT * FROM `tb_enseignant` ORDER BY `matricule` DESC";

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                enseignants.add(new Enseignant(
                        resultSet.getString("nom"),
                        resultSet.getString("contact"),
                        resultSet.getString("matricule")


                      ));
                enseignantTable.setItems(enseignants);




            }


        } catch (SQLException ex) {
            System.out.println("error here" + ex);
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }


    @FXML
    void loadDate() {
        refreshTable();
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        matriculeCol.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));

    }

    @FXML
    void clearFields() {
teacherNameField.setText("");
teacherIdField.setText("");
teacherContactField.setText("");
    }

    @FXML
    void clearFieldsMeet() {

    }

   @FXML
    void addEnsen() throws IOException {
        String alertMessage = "";
        if (teacherNameField.getText().isEmpty()) {
            alertMessage = "Nom est obligatoire";
        } else if (teacherContactField.getText().isEmpty()) {
            alertMessage = "Contact est obligatoire";
        } else if (teacherIdField.getText().isEmpty()) {
            alertMessage = "Matricule est obligatoire";

        }
        if (!alertMessage.isEmpty()) {
            alertMessage += " X ";
            alertText.setText(alertMessage);


        } else {

            try {
                connection = DbConnect.getConnect();
                enseignants.clear();
                query = "INSERT INTO `tb_enseignant`( `matricule`, `nom`, `contact`) VALUES (?,?,?)";

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, teacherIdField.getText());
                preparedStatement.setString(2, teacherNameField.getText().toUpperCase());
                preparedStatement.setString(3, teacherContactField.getText());
                preparedStatement.execute();

                clearFields();

                alertText.setText("");
                refreshTable();
            } catch (SQLException ex) {
                alertText.setText(ex.getMessage());
                refreshTable();
            }
        }
    }



    public void removeAlert(MouseEvent mouseEvent) {
        alertText.setText("");
    }


    public void searchEnsen(MouseEvent mouseEvent) {

        if (teacherNameField.getText().isEmpty()) {
            alertText.setText("Contact est obligatoire X");
        }
        else {
            try {
                String query = "SELECT * FROM `tb_enseignant` WHERE `nom`=?";
                connection = DbConnect.getConnect();


                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, teacherNameField.getText());

                resultSet = preparedStatement.executeQuery();



                if (resultSet.next()) {

                    foundRecord=true;
                    alertText.setText("Ensemble trouvé => 1 ");
                    teacherIdField.setText(resultSet.getString("matricule"));
                    teacherContactField.setText(resultSet.getString("contact"));

                }else{

                 alertText.setText("Ensemble trouvé => 0");
                }


            } catch (SQLException ex) {
                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateEnsen(MouseEvent mouseEvent) {

        String alertMessage = "";
        selectedMatricule=teacherIdField.getText();
        if (teacherNameField.getText().isEmpty()) {
            alertMessage = "Nom est obligatoire";
        } else if (teacherContactField.getText().isEmpty()) {
            alertMessage = "Contact est obligatoire";
        } else if (teacherIdField.getText().isEmpty()) {
            alertMessage = "Matricule est obligatoire";
        }

        if (!alertMessage.isEmpty()) {
            alertMessage += " X ";
            alertText.setText(alertMessage);


        }else {
            try {
                connection = DbConnect.getConnect();
                query = "UPDATE  `tb_enseignant` SET  `nom`=?, `contact`=? WHERE `matricule` = ?  ";

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, teacherNameField.getText());
                preparedStatement.setString(2, teacherContactField.getText());
                preparedStatement.setString(3, teacherIdField.getText());
                preparedStatement.execute();

                clearFields();

                alertText.setText("Enseignant changé X");
                refreshTable();
            } catch (SQLException ex) {
                alertText.setText(ex.getMessage());
                refreshTable();
            }
        }



    }

    public void deleteEnsen(MouseEvent mouseEvent) {
        if (teacherIdField.getText().isEmpty()) {
            alertText.setText("Matricule est obligatoire X");
        }
        else{
            try {
                 query = "SELECT * FROM `tb_enseignant` WHERE `matricule`=?";
                connection = DbConnect.getConnect();


                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, teacherIdField.getText());

                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    query = "DELETE  FROM `tb_enseignant` WHERE `matricule` = ? ";
                    connection = DbConnect.getConnect();


                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, teacherIdField.getText());

                    check= preparedStatement.execute();
                    clearFields();

                    refreshTable();
                    alertText.setText("Enseignant supprimé X ");

                }else{

                    alertText.setText("Enseignant introuvable X");
                }






            } catch (SQLException ex) {
                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }
}