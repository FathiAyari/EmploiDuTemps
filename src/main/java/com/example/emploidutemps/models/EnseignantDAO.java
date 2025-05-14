package com.example.emploidutemps.models;

import java.sql.SQLException;
import java.util.List;

public interface EnseignantDAO {
    void addEnseignant(Enseignant enseignant) throws SQLException;
    void updateEnseignant(Enseignant enseignant) throws SQLException;
    void deleteEnseignant(String matricule) throws SQLException;
    Enseignant getEnseignantByNom(String nom) throws SQLException;  // Add this method
    Enseignant getEnseignantByMtaricule(String nom) throws SQLException;  // Add this method
    List<Enseignant> getAllEnseignants() throws SQLException;
}
