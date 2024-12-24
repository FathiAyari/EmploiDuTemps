package com.example.emploidutemps.models;

public class Cours {
    String id;
    String classe;
    String matiere;
    String num_jour;
    String Jour;
    String heure;
    String matricule_ens;

    String nom;
    String contact;
    String matricule;

    public Cours(String nom, String contact, String matricule) {
        this.nom = nom;
        this.contact = contact;
        this.matricule = matricule;
    }
}
