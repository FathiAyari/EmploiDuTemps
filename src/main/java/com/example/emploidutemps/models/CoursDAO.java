package com.example.emploidutemps.models;

import com.example.emploidutemps.models.Cours;

import java.util.List;

public interface CoursDAO {
    List<Cours> getAllCours();
    void addCours(Cours cours);
    void deleteCours(int id);
    void updateCours(Cours cours);
}
