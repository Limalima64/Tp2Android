package com.example.tp2christianlima;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tp2christianlima.databinding.ActivityListeFilmBinding;
import com.example.tp2christianlima.databinding.FragmentListeFilmBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FragmentListeFilm extends Fragment {

    View vue;

    FirebaseDatabase bd;
    FirebaseAuth bdAuth;
    DatabaseReference ref;
    List<String> films = new ArrayList<>();
    static ListView lv_listeFilms;
    String titre, acteurPrincipal, genre, dateSortie;
    private FragmentCallback fragmentCallback;
    public FragmentListeFilm() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bdAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vue = inflater.inflate(R.layout.fragment_liste_film, container, false);

        lv_listeFilms = vue.findViewById(R.id.lv_listeFilms);

        //recuprer les cle primaire
        ref = FirebaseDatabase.getInstance().getReference("Films").child(bdAuth.getCurrentUser().getDisplayName());
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot data = task.getResult();
                        for (DataSnapshot filmSnapshot : data.getChildren()) {
                            // Ajouter la clé primaire à la liste
                            films.add(filmSnapshot.getKey());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, films);
                        lv_listeFilms.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(getContext(), "Erreur dans la recupération de vos films", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lv_listeFilms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filmChoisi = films.get(position);

                ref = FirebaseDatabase.getInstance().getReference("Films").child(bdAuth.getCurrentUser().getDisplayName()).child(filmChoisi);
                ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().exists()){
                                DataSnapshot data = task.getResult();
                                titre = String.valueOf(data.child("titre").getValue());
                                acteurPrincipal = String.valueOf(data.child("acteurPrincipal").getValue());
                                genre = String.valueOf(data.child("genre").getValue());
                                dateSortie = String.valueOf(data.child("dateSortie").getValue());

                                Bundle bundle = new Bundle();
                                bundle.putString("titre", titre);
                                bundle.putString("acteurPrincipal", acteurPrincipal);
                                bundle.putString("genre", genre);
                                bundle.putString("dateSortie", dateSortie);

                                getParentFragmentManager().setFragmentResult("informationsFilmChoisi", bundle);
                                fragmentCallback.filmChoisi(titre, acteurPrincipal, genre, dateSortie);
                            }else{
                                Toast.makeText(getContext(), "Le film n'existe pas", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getContext(), "Erreur de la lecture", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return vue;
    }

    public interface FragmentCallback {
        void filmChoisi(String titre, String acteurPrincipal, String genre, String dateSortie);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentCallback = (FragmentCallback) context;
    }
}