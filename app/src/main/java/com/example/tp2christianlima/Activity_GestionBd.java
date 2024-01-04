package com.example.tp2christianlima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_GestionBd extends AppCompatActivity {

    FirebaseAuth bdAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_bd);

        bdAuth = FirebaseAuth.getInstance();

    }

    public void onClick_deconnecter(View view) {
        bdAuth.signOut();
        Intent ActivityConnexion = new Intent(Activity_GestionBd.this, MainActivity.class);
        startActivity(ActivityConnexion);
        finish();
    }

    public void onClick_profil(View view) {
            Intent ActivityProfil = new Intent(Activity_GestionBd.this, Activity_Profil.class);
            startActivity(ActivityProfil);

    }

    public void onClick_ajouterFilm(View view) {
            Intent AfficherAjoutFilm = new Intent(Activity_GestionBd.this, Activity_AjoutFilm.class);
            startActivity(AfficherAjoutFilm);
    }

    public void onClick_listeFilm(View view) {
        Intent afficherListFilm = new Intent(Activity_GestionBd.this, Activity_listeFilm.class);
        startActivity(afficherListFilm);
    }
}