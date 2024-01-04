package com.example.tp2christianlima;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp2christianlima.databinding.ActivityMiseAjourFilmBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class Activity_MiseAJourFilm extends AppCompatActivity {


    ActivityMiseAjourFilmBinding binding;
    String titreFilm, acteurPrincipalFilm, genreFilm, dateSortieFilm;
    String nouveauTitre, nouveauActeur, nouveauGenre, nouveauDate;
    FirebaseDatabase bd;
    DatabaseReference ref;
    FirebaseAuth bdAuth;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMiseAjourFilmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /*tv_titreMiseAJourFilm = findViewById(R.id.tv_titreMiseAJourFilm);
        tv_informationTitreFilm = findViewById(R.id.tv_informationTitreFilm);
        tv_informationActeurFilm = findViewById(R.id.tv_informationActeurFilm);
        tv_informationGenreFilm = findViewById(R.id.tv_informationGenreFilm);
        tv_informationDateFilm = findViewById(R.id.tv_informationDateFilm);*/

        Intent recoitInfosFilm = getIntent();
        titreFilm = recoitInfosFilm.getStringExtra("titreFilm");
        acteurPrincipalFilm = recoitInfosFilm.getStringExtra("acteurPrincipalFilm");
        genreFilm = recoitInfosFilm.getStringExtra("genreFilm");
        dateSortieFilm = recoitInfosFilm.getStringExtra("dateSortieFilm");

        resources = getResources();

        binding.tvTitreMiseAJourFilm.setText(resources.getString(R.string.modifFilmTitre) + " " + titreFilm);
        binding.tvInformationTitreFilm.setText(resources.getString(R.string.titreFilm) + " " + titreFilm);
        binding.tvInformationActeurFilm.setText(resources.getString(R.string.acteurFilm) + " " + acteurPrincipalFilm);
        binding.tvInformationGenreFilm.setText(resources.getString(R.string.genreFilm) + " " + genreFilm);
        binding.tvInformationDateFilm.setText(resources.getString(R.string.dateFilm) + " " + dateSortieFilm);

        bdAuth = FirebaseAuth.getInstance();

        binding.btnRetour2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retourIntent = new Intent(Activity_MiseAJourFilm.this, Activity_listeFilm.class);
                startActivity(retourIntent);
                finish();
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationSuppresion();
            }
        });

        binding.tietModifDateFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendrier = Calendar.getInstance();
                int annee = calendrier.get(Calendar.YEAR);
                int mois = calendrier.get(Calendar.MONTH);
                int jour = calendrier.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(Activity_MiseAJourFilm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dateChoisi = dayOfMonth + "-" + month + "-" + year;
                        binding.tietModifDateFilm.setText(dateChoisi);
                    }
                }, annee, mois, jour);

                datePicker.show();
            }
        });

        binding.btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nouveauTitre = binding.tietModifTitreFilm.getText().toString();
                nouveauActeur = binding.tietModifActeurFilm.getText().toString();
                nouveauGenre = binding.tietModifGenreFilm.getText().toString();
                nouveauDate = binding.tietModifDateFilm.getText().toString();

                if(nouveauTitre.isEmpty()){
                    nouveauTitre = titreFilm;
                }
                if(nouveauActeur.isEmpty()){
                    nouveauActeur = acteurPrincipalFilm;
                }
                if(nouveauGenre.isEmpty()){
                    nouveauGenre = genreFilm;
                }
                if(nouveauDate.isEmpty()){
                    nouveauDate = dateSortieFilm;
                }

                HashMap nouveauFilm = new HashMap();
                nouveauFilm.put("titre", nouveauTitre);
                nouveauFilm.put("acteurPrincipal", nouveauActeur);
                nouveauFilm.put("genre", nouveauGenre);
                nouveauFilm.put("dateSortie", nouveauDate);

                ref = FirebaseDatabase.getInstance().getReference("Films").child(bdAuth.getCurrentUser().getDisplayName());
                ref.child(titreFilm).updateChildren(nouveauFilm)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){
                                    ref.child(titreFilm).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                ref.child(titreFilm).removeValue();
                                                DatabaseReference databaseReferenceNouveauFilm = FirebaseDatabase.getInstance().getReference("Films").child(bdAuth.getCurrentUser().getDisplayName()).child(nouveauTitre);
                                                databaseReferenceNouveauFilm.setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                        if (error == null) {
                                                            binding.tietModifTitreFilm.setText("");
                                                            binding.tietModifActeurFilm.setText("");
                                                            binding.tietModifGenreFilm.setText("");
                                                            binding.tietModifDateFilm.setText("");
                                                            Toast.makeText(Activity_MiseAJourFilm.this, resources.getString(R.string.modificationReussi), Toast.LENGTH_SHORT).show();
                                                            Intent retour = new Intent(Activity_MiseAJourFilm.this, Activity_listeFilm.class);
                                                            startActivity(retour);
                                                            finish();
                                                        }else{
                                                            Toast.makeText(Activity_MiseAJourFilm.this, resources.getString(R.string.modifProfilErreur), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });
                                }
                            }
                        });
            }
        });
    }

    private void confirmationSuppresion() {
        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(this);
        dialogueBuilder.setMessage(resources.getString(R.string.confirmationSuppriemr));

        dialogueBuilder.setPositiveButton(resources.getString(R.string.Oui), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                supprimerFilm();
            }
        });

        dialogueBuilder.setNegativeButton(resources.getString(R.string.Non), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = dialogueBuilder.create();
        dialog.show();
    }

    private void supprimerFilm() {
        ref = FirebaseDatabase.getInstance().getReference("Films").child(bdAuth.getCurrentUser().getDisplayName());
        ref.child(titreFilm).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Activity_MiseAJourFilm.this, resources.getString(R.string.supprimerFilm), Toast.LENGTH_SHORT).show();
                            Intent retourIntent = new Intent(Activity_MiseAJourFilm.this, Activity_listeFilm.class);
                            startActivity(retourIntent);
                            finish();
                        }else{
                            Toast.makeText(Activity_MiseAJourFilm.this, resources.getString(R.string.supprimerFilmErreur), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}