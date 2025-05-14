package com.example.emploidutemps.models;

import com.example.emploidutemps.helpers.DbConnect;
import com.example.emploidutemps.models.Cours;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursDAOImpl implements CoursDAO {

    private Connection connection;

    public CoursDAOImpl() {
        this.connection = DbConnect.getConnect();
    }

    @Override
    public List<Cours> getAllCours() {
        List<Cours> coursList = new ArrayList<>();
        String query = "SELECT * FROM tb_cours";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cours cours = new Cours(
                        rs.getInt("id"),
                        rs.getString("classe"),
                        rs.getString("matiere"),
                        rs.getString("jour"),
                        rs.getString("heure"),
                        rs.getString("matricule_ens")
                );
                coursList.add(cours);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursList;
    }

    @Override
    public void addCours(Cours cours) {
        String query = "INSERT INTO tb_cours (classe, matiere, jour, heure, matricule_ens) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, cours.getClasse());
            ps.setString(2, cours.getMatiere());
            ps.setString(3, cours.getJour());
            ps.setString(4, cours.getHeure());
            ps.setString(5, cours.getMatricule_ens());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCours(int id) {
        String query = "DELETE FROM tb_cours WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCours(Cours cours) {
        String query = "UPDATE tb_cours SET classe=?, matiere=?, jour=?, heure=?, matricule_ens=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, cours.getClasse());
            ps.setString(2, cours.getMatiere());
            ps.setString(3, cours.getJour());
            ps.setString(4, cours.getHeure());
            ps.setString(5, cours.getMatricule_ens());
            ps.setInt(6, cours.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
