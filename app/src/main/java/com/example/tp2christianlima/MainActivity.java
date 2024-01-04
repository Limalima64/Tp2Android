package com.example.tp2christianlima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextInputEditText tiet_courrielConnexion, tiet_mdpConnexion;

    FirebaseAuth bdAuth;

    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tiet_courrielConnexion = findViewById(R.id.tiet_courrielModif);
        tiet_mdpConnexion = findViewById(R.id.tiet_telModif);

        bdAuth = FirebaseAuth.getInstance();

        resources = getResources();
    }

    public void onClickPageInscription(View view) {
        Intent ActivityInscription = new Intent(MainActivity.this, Activity_Inscription.class);
        startActivity(ActivityInscription);
    }

    public void onClickConnexion(View view) {
        String courriel = tiet_courrielConnexion.getText().toString();
        String mdp = tiet_mdpConnexion.getText().toString();

        if(Patterns.EMAIL_ADDRESS.matcher(courriel).matches() && mdp.length() >= 10) {
           bdAuth.signInWithEmailAndPassword(courriel, mdp)
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()) {
                               FirebaseUser usager = bdAuth.getCurrentUser();
                               Intent intentionConnecter = new Intent(MainActivity.this, Activity_GestionBd.class);
                               startActivity(intentionConnecter);
                               finish();
                           }else{
                               Toast.makeText(MainActivity.this, resources.getString(R.string.ErreurConnection), Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
        }else if (!Patterns.EMAIL_ADDRESS.matcher(courriel).matches()){
            tiet_courrielConnexion.setError(resources.getString(R.string.courrielErreur));
            tiet_courrielConnexion.requestFocus();
        }else{
            tiet_mdpConnexion.setError(resources.getString(R.string.longeurMdp));
            tiet_mdpConnexion.requestFocus();
        }
    }
}