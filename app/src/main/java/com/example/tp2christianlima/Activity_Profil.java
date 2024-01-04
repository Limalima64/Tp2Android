package com.example.tp2christianlima;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp2christianlima.databinding.ActivityProfilBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Activity_Profil extends AppCompatActivity {

    String nomComplet, courriel, nouveauNom, nouveauCourriel;

    FirebaseAuth bdAuth;

    ActivityProfilBinding binding;

    FirebaseDatabase bd;
    DatabaseReference ref;

    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bdAuth = FirebaseAuth.getInstance();

        resources = getResources();

        courriel = bdAuth.getCurrentUser().getEmail();
        nomComplet = bdAuth.getCurrentUser().getDisplayName();

        binding.tvInformationNomComplet.setText(resources.getString(R.string.nomProfil) + ": " + nomComplet);
        binding.tvInformationCourriel.setText(resources.getString(R.string.courrielProfil) + ": " + courriel);

        resources = getResources();

        binding.btnSauvegarder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nouveauNom = binding.tietNomModif.getText().toString();
                nouveauCourriel = binding.tietCourrielModif.getText().toString();

                if(nouveauNom.isEmpty()){
                    nouveauNom = nomComplet;
                }

                if(nouveauCourriel.isEmpty()){
                    nouveauCourriel = courriel;
                }

                if (Patterns.EMAIL_ADDRESS.matcher(nouveauCourriel).matches()) {
                    /*Véerifier si le user name existe dans la base de données si on on change aussi masi on ne touche pas dans la base de donnée*/
                    DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("Films").child(nomComplet);

                    databaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                databaseReferenceUser.removeValue();
                                DatabaseReference databaseReferenceNouveauNom = FirebaseDatabase.getInstance().getReference("Films").child(nouveauNom);
                                databaseReferenceNouveauNom.setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if (error == null) {

                                            binding.tietNomModif.setText("");
                                            binding.tietCourrielModif.setText("");
                                            Toast.makeText(Activity_Profil.this, resources.getString(R.string.modifProfilReussi), Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(Activity_Profil.this, resources.getString(R.string.modifProfilErreur), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            //Changer le nom de l'user dans authtification
                            FirebaseUser user = bdAuth.getCurrentUser();
                            user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(nouveauNom).build());

                            //changer le courriel
                            if (!courriel.equals(nouveauCourriel)) {
                                updateFirebaseEmail(nouveauCourriel);
                            } else {
                                Intent retourIntent = new Intent(Activity_Profil.this, Activity_GestionBd.class);
                                startActivity(retourIntent);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    binding.tietCourrielModif.setError(resources.getString(R.string.courrielProfilModifErreur));
                    binding.tietCourrielModif.requestFocus();
                }
            }
        });

        binding.btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retourIntent = new Intent(Activity_Profil.this, Activity_GestionBd.class);
                startActivity(retourIntent);
            }
        });
    }

    private void updateFirebaseEmail(String nouveauCourriel) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.verifyBeforeUpdateEmail(nouveauCourriel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> updateTask) {
                    if (updateTask.isSuccessful()) {
                        Toast.makeText(Activity_Profil.this, resources.getString(R.string.envoieCourrielValidation) + " " + nouveauCourriel, Toast.LENGTH_LONG).show();
                        user.updateEmail(nouveauCourriel);
                        bdAuth.signOut();
                        Toast.makeText(Activity_Profil.this, resources.getString(R.string.courrielValidationReussi), Toast.LENGTH_SHORT).show();
                        Intent retourPagePrincipal = new Intent(Activity_Profil.this, MainActivity.class);
                        startActivity(retourPagePrincipal);
                    } else {
                        Toast.makeText(Activity_Profil.this, resources.getString(R.string.EchecModifCourriel), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(Activity_Profil.this, resources.getString(R.string.utilisateurNonTrouverProfil), Toast.LENGTH_SHORT).show();
        }
    }
}