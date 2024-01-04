package com.example.tp2christianlima;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentInformationFilmChoisi extends Fragment {

    View vue;

    TextView tv_titreFilmChoisi, tv_acteurPrincipalFilmChoisi, tv_genreFilmChoisi, tv_dateSortieFilmChoisi;

    Resources resources;
    public FragmentInformationFilmChoisi() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vue = inflater.inflate(R.layout.fragment_information_film_choisi, container, false);

        tv_titreFilmChoisi = vue.findViewById(R.id.tv_titreFilmChoisi);
        tv_acteurPrincipalFilmChoisi = vue.findViewById(R.id.tv_acteurPrincipalFilmChoisi);
        tv_genreFilmChoisi = vue.findViewById(R.id.tv_genreFilmChoisi);
        tv_dateSortieFilmChoisi = vue.findViewById(R.id.tv_dateSortieFilmChoisi);

        resources = getResources();

        getParentFragmentManager().setFragmentResultListener("informationsFilmChoisi", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String titre = result.getString("titre");
                String acteurPrincipal = result.getString("acteurPrincipal");
                String genre = result.getString("genre");
                String dateSortie = result.getString("dateSortie");

                tv_titreFilmChoisi.setText("");
                tv_acteurPrincipalFilmChoisi.setText("");
                tv_genreFilmChoisi.setText("");
                tv_dateSortieFilmChoisi.setText("");

                tv_titreFilmChoisi.setText(resources.getString(R.string.InfoFilmTitre) + " " + titre);
                tv_acteurPrincipalFilmChoisi.setText(resources.getString(R.string.InfoFilmActeur) + " " + acteurPrincipal);
                tv_genreFilmChoisi.setText(resources.getString(R.string.InfoGenreFilm) + " " + genre);
                tv_dateSortieFilmChoisi.setText(resources.getString(R.string.InfoDateFilm) + " " + dateSortie);
            }
        });
        return vue;
    }
}