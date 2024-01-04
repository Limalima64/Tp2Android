package com.example.tp2christianlima;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class user {

    private String nomComplet, courriel, mdp;

    public user(String nomComplet, String courriel, String mdp) {
        this.nomComplet = nomComplet;
        this.courriel = courriel;
        this.mdp = mdp;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getMdp(){return mdp;}

    public void setMdp(String mdp){this.mdp = mdp;}

    public user() {}

}
