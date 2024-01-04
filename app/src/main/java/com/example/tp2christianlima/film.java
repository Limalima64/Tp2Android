package com.example.tp2christianlima;

public class film {

    String titre, acteurPrincipal, genre, dateSortie;

    public film(String titre, String acteurPrincipal, String genre, String dateSortie) {
        this.titre = titre;
        this.acteurPrincipal = acteurPrincipal;
        this.genre = genre;
        this.dateSortie = dateSortie;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getActeurPrincipal() {
        return acteurPrincipal;
    }

    public void setActeurPrincipal(String acteurPrincipal) {
        this.acteurPrincipal = acteurPrincipal;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(String dateSortie) {
        this.dateSortie = dateSortie;
    }
}
