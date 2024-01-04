package com.example.tp2christianlima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Activity_listeFilm extends AppCompatActivity implements FragmentListeFilm.FragmentCallback{

    String titreFilm = "", acteurPrincipalFilm = "", genreFilm = "", dateSortieFilm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_liste_film);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fr_listeFilm, new FragmentListeFilm());
        fragmentTransaction.replace(R.id.fr_filmChoisi, new FragmentInformationFilmChoisi());
        fragmentTransaction.commit();

    }

    public void onClick_misAJourfilm(View view) {

        if(!titreFilm.isEmpty() && !acteurPrincipalFilm.isEmpty() && !genreFilm.isEmpty() && !dateSortieFilm.isEmpty()){
            Intent miseAJourFilmIntent = new Intent(Activity_listeFilm.this, Activity_MiseAJourFilm.class);
            miseAJourFilmIntent.putExtra("titreFilm", titreFilm);
            miseAJourFilmIntent.putExtra("acteurPrincipalFilm", acteurPrincipalFilm);
            miseAJourFilmIntent.putExtra("genreFilm", genreFilm);
            miseAJourFilmIntent.putExtra("dateSortieFilm", dateSortieFilm);
            startActivity(miseAJourFilmIntent);
            finish();
        }else{
            Toast.makeText(this, "Vous devez selectionner un film.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick_retour(View view) {
        Intent retounerIntent = new Intent(Activity_listeFilm.this, Activity_GestionBd.class);
        startActivity(retounerIntent);
        finish();
    }

    @Override
    public void filmChoisi(String titre, String acteurPrincipal, String genre, String dateSortie) {
        titreFilm = titre;
        acteurPrincipalFilm = acteurPrincipal;
        genreFilm = genre;
        dateSortieFilm = dateSortie;
    }
}