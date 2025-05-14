package com.example.emploidutemps.models;

import com.example.emploidutemps.helpers.DbConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnseignantDAOImpl implements EnseignantDAO {

    private Connection connection;

    public EnseignantDAOImpl() {
        connection = DbConnect.getConnect();  // Assuming you have a DbConnect class for database connection
    }

    @Override
    public void addEnseignant(Enseignant enseignant) throws SQLException {
        String query = "INSERT INTO `tb_enseignant`(`matricule`, `nom`, `contact`) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, enseignant.getMatricule());
            stmt.setString(2, enseignant.getNom());
            stmt.setString(3, enseignant.getContact());
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateEnseignant(Enseignant enseignant) throws SQLException {
        String query = "UPDATE `tb_enseignant` SET `nom`=?, `contact`=? WHERE `matricule`=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, enseignant.getNom());
            stmt.setString(2, enseignant.getContact());
            stmt.setString(3, enseignant.getMatricule());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteEnseignant(String matricule) throws SQLException {
        String query = "DELETE FROM `tb_enseignant` WHERE `matricule`=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, matricule);
            stmt.executeUpdate();
        }
    }

    @Override
    public Enseignant getEnseignantByNom(String nom) throws SQLException {
        String query = "SELECT * FROM `tb_enseignant` WHERE `nom`=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Enseignant(
                        rs.getString("nom"),
                        rs.getString("contact"),
                        rs.getString("matricule")
                );
            } else {
                return null;
            }
        }
    }
    @Override
    public Enseignant getEnseignantByMtaricule(String nom) throws SQLException {
        String query = "SELECT * FROM `tb_enseignant` WHERE `matricule`=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Enseignant(
                        rs.getString("nom"),
                        rs.getString("contact"),
                        rs.getString("matricule")
                );
            } else {
                return null;
            }
        }
    }

    @Override
    public List<Enseignant> getAllEnseignants() throws SQLException {
        List<Enseignant> enseignants = new ArrayList<>();
        String query = "SELECT * FROM `tb_enseignant` ORDER BY `matricule` DESC";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                enseignants.add(new Enseignant(
                        rs.getString("nom"),
                        rs.getString("contact"),
                        rs.getString("matricule")
                ));
            }
        }
        return enseignants;
    }
}
