package com.example.emploidutemps.controllers;

import com.example.emploidutemps.helpers.DbConnect;
import com.example.emploidutemps.models.Cours;
import com.example.emploidutemps.models.CoursDAO;
import com.example.emploidutemps.models.CoursDAOImpl;
import com.example.emploidutemps.models.Enseignant;
import com.example.emploidutemps.models.EnseignantDAO;
import com.example.emploidutemps.models.EnseignantDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableViewController implements Initializable {

    @FXML private TableColumn<Cours, String> classeCol;
    @FXML private TableColumn<Cours, String> subjectCol;
    @FXML private TableColumn<Cours, String> teacherCol;
    @FXML private TableColumn<Cours, String> hourCol;
    @FXML private TableColumn<Cours, String> dayCol;

    @FXML private ComboBox<String> classComboBox;
    @FXML private ComboBox<String> subjectComboBox;
    @FXML private ComboBox<String> dayComboBox;
    @FXML private ComboBox<String> hourComboBox;

    @FXML private TextField teacherIdSessionField;
    @FXML private TextField teacherNameField;
    @FXML private TextField teacherContactField;
    @FXML private TextField teacherIdField;

    @FXML private Label alertText;

    @FXML private TableView<Enseignant> enseignantTable;
    @FXML private TableView<Cours> courseTable;
    @FXML private TableColumn<Enseignant, String> matriculeCol;
    @FXML private TableColumn<Enseignant, String> nameCol;
    @FXML private TableColumn<Enseignant, String> contactCol;

    private final ObservableList<Enseignant> enseignants = FXCollections.observableArrayList();
    private final ObservableList<Cours> cours = FXCollections.observableArrayList();

    private final CoursDAO coursDAO = new CoursDAOImpl();
    private final EnseignantDAO enseignantDAO = new EnseignantDAOImpl();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void refreshTable() throws SQLException {
        enseignants.clear();
        enseignants.addAll(enseignantDAO.getAllEnseignants());
        enseignantTable.setItems(enseignants);
    }

    private void refreshCoursTable() {
        cours.clear();
        cours.addAll(coursDAO.getAllCours());
        courseTable.setItems(cours);
    }

    @FXML
    void loadData() throws SQLException {
        refreshTable();
        refreshCoursTable();

        nameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        matriculeCol.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        classeCol.setCellValueFactory(new PropertyValueFactory<>("classe"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("matiere"));
        dayCol.setCellValueFactory(new PropertyValueFactory<>("jour"));
        hourCol.setCellValueFactory(new PropertyValueFactory<>("heure"));
        teacherCol.setCellValueFactory(new PropertyValueFactory<>("matricule_ens"));
    }

    @FXML
    void clearFields() {
        teacherNameField.setText("");
        teacherIdField.setText("");
        teacherContactField.setText("");
    }

    @FXML
    void addEnsen() {
        String alertMessage = "";

        // Validate fields
        if (teacherNameField.getText().isEmpty()) {
            alertMessage = "Nom est obligatoire";
        } else if (teacherContactField.getText().isEmpty()) {
            alertMessage = "Contact est obligatoire";
        } else if (teacherIdField.getText().isEmpty()) {
            alertMessage = "Matricule est obligatoire";
        }

        if (!alertMessage.isEmpty()) {
            alertText.setText(alertMessage + " X ");
        } else {
            Enseignant enseignant = new Enseignant(
                    teacherNameField.getText().toUpperCase(),
                    teacherContactField.getText(),
                    teacherIdField.getText()
            );

            try {
                enseignantDAO.addEnseignant(enseignant);
                clearFields();
                alertText.setText("Ajouté avec succès");
                refreshTable();
            } catch (SQLException ex) {
                alertText.setText("Erreur: " + ex.getMessage());
            }
        }
    }

    public void removeAlert(MouseEvent mouseEvent) {
        alertText.setText("");
    }

    public void searchEnsen(MouseEvent mouseEvent) throws SQLException {
        if (teacherNameField.getText().isEmpty()) {
            alertText.setText("Nom est obligatoire X");
        } else {
            Enseignant enseignant = enseignantDAO.getEnseignantByNom(teacherNameField.getText());

            if (enseignant != null) {
                alertText.setText("Enseignant trouvé");
                teacherIdField.setText(enseignant.getMatricule());
                teacherContactField.setText(enseignant.getContact());
            } else {
                alertText.setText("Aucun résultat");
            }
        }
    }

    public void updateEnsen(MouseEvent mouseEvent) {
        if (teacherNameField.getText().isEmpty() || teacherContactField.getText().isEmpty() || teacherIdField.getText().isEmpty()) {
            alertText.setText("Tous les champs sont obligatoires X");
            return;
        }

        Enseignant enseignant = new Enseignant(
                teacherNameField.getText().toUpperCase(),
                teacherContactField.getText(),
                teacherIdField.getText()
        );

        try {
            enseignantDAO.updateEnseignant(enseignant);
            clearFields();
            alertText.setText("Mise à jour réussie X");
            refreshTable();
        } catch (SQLException ex) {
            alertText.setText("Erreur: " + ex.getMessage());
        }
    }

    public void deleteEnsen(MouseEvent mouseEvent) {
        if (teacherIdField.getText().isEmpty()) {
            alertText.setText("Matricule est obligatoire X");
            return;
        }

        try {
            enseignantDAO.deleteEnseignant(teacherIdField.getText());
            clearFields();
            refreshTable();
            alertText.setText("Enseignant supprimé X");
        } catch (SQLException ex) {
            alertText.setText("Erreur: " + ex.getMessage());
        }
    }

    public void addCours() {
        String alertMessage = "";

        if (classComboBox.getValue() == null) {
            alertMessage = "Classe obligatoire";
        } else if (subjectComboBox.getValue() == null) {
            alertMessage = "Matière obligatoire";
        } else if (dayComboBox.getValue() == null) {
            alertMessage = "Jour obligatoire";
        } else if (hourComboBox.getValue() == null) {
            alertMessage = "Heure obligatoire";
        } else if (teacherIdSessionField.getText().isEmpty()) {
            alertMessage = "Matricule obligatoire";
        }

        if (!alertMessage.isEmpty()) {
            alertText.setText(alertMessage + " X ");
            return;
        }

        try {
            Enseignant enseignant = enseignantDAO.getEnseignantByMtaricule(teacherIdSessionField.getText());
            if (enseignant != null) {
                Cours coursObj = new Cours(
                        0,
                        classComboBox.getValue(),
                        subjectComboBox.getValue(),
                        dayComboBox.getValue(),
                        hourComboBox.getValue(),
                        teacherIdSessionField.getText()
                );
                coursDAO.addCours(coursObj);
                classComboBox.setValue(null);
                subjectComboBox.setValue(null);
                dayComboBox.setValue(null);
                hourComboBox.setValue(null);
                teacherIdSessionField.setText("");
                alertText.setText("Cours ajouté X");
                refreshCoursTable();
            } else {
                alertText.setText("Enseignant introuvable X");
            }
        } catch (SQLException ex) {
            alertText.setText("Erreur: " + ex.getMessage());
        }
    }
}
