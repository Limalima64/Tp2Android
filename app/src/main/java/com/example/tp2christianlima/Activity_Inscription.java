package com.example.tp2christianlima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.tp2christianlima.databinding.ActivityInscriptionBinding;
import com.example.tp2christianlima.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_Inscription extends AppCompatActivity {

    TextInputEditText tiet_nomCompletInscription, tiet_courrielInscription, tiet_mdpInscription, tiet_mdpValidationInscription;

    FirebaseAuth bdAuth;

    Button btn_inscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        tiet_nomCompletInscription = findViewById(R.id.tiet_nomCompletInscription);
        tiet_courrielInscription = findViewById(R.id.tiet_courrielInscription);
        tiet_mdpInscription = findViewById(R.id.tiet_mdpInscription);
        tiet_mdpValidationInscription = findViewById(R.id.tiet_mdpValidationInscription);

        btn_inscription = findViewById(R.id.btn_inscription);

        bdAuth = FirebaseAuth.getInstance();

        Resources resources = getResources();

        btn_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomComplet = tiet_nomCompletInscription.getText().toString();
                String courriel = tiet_courrielInscription.getText().toString();
                String mdp = tiet_mdpInscription.getText().toString();
                String validationMdp = tiet_mdpValidationInscription.getText().toString();

                if(!nomComplet.isEmpty()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(courriel).matches()) {
                        if (mdp.matches(validationMdp) && mdp.length() >= 10) {

                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                            bdAuth.createUserWithEmailAndPassword(courriel, mdp)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseUser usager = bdAuth.getCurrentUser();
                                                if (usager != null) {

                                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                            .setDisplayName(nomComplet)
                                                            .build();
                                                    usager.updateProfile(profileUpdates);

                                                    //faire le changement de langue du toast
                                                    Toast.makeText(Activity_Inscription.this, resources.getString(R.string.inscriptionReussite), Toast.LENGTH_SHORT).show();
                                                    Intent ActivityConnexion = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(ActivityConnexion);
                                                    //finish();
                                                }
                                            } else {
                                                Toast.makeText(Activity_Inscription.this, resources.getString(R.string.inscriptionErreur), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else if (mdp.length() < 10) {
                            tiet_mdpInscription.setError(resources.getString(R.string.longeurMdp));
                            tiet_mdpInscription.requestFocus();
                        } else {
                            tiet_mdpValidationInscription.setError(resources.getString(R.string.validationMdpErreur));
                            tiet_mdpValidationInscription.requestFocus();
                        }
                    } else {
                        tiet_courrielInscription.setError(resources.getString(R.string.courrielErreur));
                        tiet_courrielInscription.requestFocus();
                    }
                }else{
                    tiet_nomCompletInscription.setError(resources.getString(R.string.nomCompletErreur));
                    tiet_nomCompletInscription.requestFocus();
                }

            }
        });
    }
}