package com.example.tp2christianlima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.tp2christianlima.databinding.ActivityAjoutFilmBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Activity_AjoutFilm extends AppCompatActivity {

    TextInputEditText tiet_ajoutTitreFilm, tiet_ajoutActueurPrincipalFilm, tiet_ajoutGenreFilm, tiet_ajoutDateSortieFilm;

    ActivityAjoutFilmBinding binding;
    String titre, acteurPrincipal, genre, dateSortie;
    FirebaseDatabase bd;
    DatabaseReference ref;
    FirebaseAuth bdAuth;

    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAjoutFilmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bdAuth = FirebaseAuth.getInstance();

        resources = getResources();

        binding.tietAjoutDateSortieFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendrier = Calendar.getInstance();
                int annee = calendrier.get(Calendar.YEAR);
                int mois = calendrier.get(Calendar.MONTH);
                int jour = calendrier.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(Activity_AjoutFilm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dateChoisi = dayOfMonth + "-" + month + "-" + year;
                        binding.tietAjoutDateSortieFilm.setText(dateChoisi);
                    }
                }, annee, mois, jour);

                datePicker.show();
            }
        });

        binding.btnSauvegarderFilmBd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titre = binding.tietAjoutTitreFilm.getText().toString();
                acteurPrincipal = binding.tietAjoutActueurPrincipalFilm.getText().toString();
                genre = binding.tietAjoutGenreFilm.getText().toString();
                dateSortie = binding.tietAjoutDateSortieFilm.getText().toString();

                if(!titre.isEmpty()){
                    if(!acteurPrincipal.isEmpty()){
                        if(!genre.isEmpty()){
                            if(!dateSortie.isEmpty()){
                                film filmBd = new film(titre, acteurPrincipal, genre, dateSortie);

                                bd = FirebaseDatabase.getInstance();
                                ref = bd.getReference("Films").child(bdAuth.getCurrentUser().getDisplayName());
                                ref.child(titre).setValue(filmBd)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                binding.tietAjoutTitreFilm.setText("");
                                                binding.tietAjoutActueurPrincipalFilm.setText("");
                                                binding.tietAjoutGenreFilm.setText("");
                                                binding.tietAjoutDateSortieFilm.setText("");
                                                Toast.makeText(Activity_AjoutFilm.this, resources.getString(R.string.filmAjout), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }else{
                                binding.tietAjoutDateSortieFilm.setError(resources.getString(R.string.dateFilmErreur));
                                binding.tietAjoutDateSortieFilm.callOnClick();
                            }
                        }else{
                            binding.tietAjoutGenreFilm.setError(resources.getString(R.string.genreFilmErreur));
                            binding.tietAjoutGenreFilm.requestFocus();
                        }
                    }else{
                        binding.tietAjoutActueurPrincipalFilm.setError(resources.getString(R.string.acteurFilmErreur));
                        binding.tietAjoutActueurPrincipalFilm.requestFocus();
                    }
                }else{
                    binding.tietAjoutTitreFilm.setError(resources.getString(R.string.titreFilmErreur));
                    binding.tietAjoutTitreFilm.requestFocus();
                }
            }
        });

        binding.btnRevenirAutrePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retournerIntent = new Intent(Activity_AjoutFilm.this, Activity_GestionBd.class);
                startActivity(retournerIntent);
            }
        });


    }
}