package com.example.emploidutemps.models;

public class Enseignant {
    private String nom;
    private String contact;
    private String matricule;

    // Constructor
    public Enseignant(String nom, String contact, String matricule) {
        this.nom = nom;
        this.contact = contact;
        this.matricule = matricule;
    }

    // Getter and Setter for 'nom'
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter and Setter for 'contact'
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    // Getter and Setter for 'matricule'
    public String getMatricule() {
        return matricule;
    }

    // Override toString
    @Override
    public String toString() {
        return "Enseignant{" +
                "nom='" + nom + '\'' +
                ", contact='" + contact + '\'' +
                ", matricule='" + matricule + '\'' +
                '}';
    }
}