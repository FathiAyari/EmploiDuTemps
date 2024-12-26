package com.example.emploidutemps.models;

public class Cours {
    private int id;
    private String classe;
    private String matiere;
    private String Jour;  // Keeping the original attribute name
    private String heure;
    private String matricule_ens;  // Keeping the original attribute name
    private String nom;  // Keeping the original attribute name
    private String ens_contact;  // Keeping the original attribute name

    // Constructor
    public Cours(int id, String classe, String matiere, String Jour, String heure, String matricule_ens) {
        this.id = id;
        this.classe = classe;
        this.matiere = matiere;
        this.Jour = Jour;
        this.heure = heure;
        this.matricule_ens = matricule_ens;
    }
    public Cours(int id, String classe, String matiere, String Jour, String heure, String nom, String ens_contact) {
        this.id = id;
        this.classe = classe;
        this.matiere = matiere;
        this.Jour = Jour;
        this.heure = heure;
        this.nom = nom;
        this.ens_contact = ens_contact;
    }



    // Getter for id
    public String getContact() {
        return ens_contact;
    }

    // Setter for id
    public void seContact(String ens_contact) {
        this.ens_contact = ens_contact;
    }

    // Getter for id
    public String getNom() {
        return nom;
    }

    // Setter for id
    public void setNom(String nom) {
        this.nom = nom;
    }
    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
    public void setId(int id) {
        this.id = id;
    }

    // Getter for classe
    public String getClasse() {
        return classe;
    }

    // Setter for classe
    public void setClasse(String classe) {
        this.classe = classe;
    }

    // Getter for matiere
    public String getMatiere() {
        return matiere;
    }

    // Setter for matiere
    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    // Getter for Jour
    public String getJour() {
        return Jour;
    }

    // Setter for Jour
    public void setJour(String Jour) {
        this.Jour = Jour;
    }

    // Getter for heure
    public String getHeure() {
        return heure;
    }

    // Setter for heure
    public void setHeure(String heure) {
        this.heure = heure;
    }

    // Getter for matricule_ens
    public String getMatricule_ens() {
        return matricule_ens;
    }

    // Setter for matricule_ens
    public void setMatricule_ens(String matricule_ens) {
        this.matricule_ens = matricule_ens;
    }
}
