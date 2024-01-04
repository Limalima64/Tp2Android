package com.example.tp2christianlima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthentificationBidon extends AppCompatActivity{
    String jeton = "";
    FirebaseAuth bdAuth = FirebaseAuth.getInstance();
    public String getUserConnectToken() {
        user usager = new user("Christian", "Christian@hotmail.com", "1234567890");

        if(Patterns.EMAIL_ADDRESS.matcher(usager.getCourriel()).matches() && usager.getMdp().length() >= 10) {
            bdAuth.signInWithEmailAndPassword(usager.getCourriel(), usager.getMdp())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                FirebaseUser user = bdAuth.getCurrentUser();
                                SharedPreferences prefs = getSharedPreferences("UserTokenConnecter", MODE_PRIVATE);
                                jeton = "UserConnecter";
                            }else{
                                SharedPreferences prefs = getSharedPreferences("UserTokenPasConnecter", MODE_PRIVATE);
                                jeton = "UserNonConnecter";
                            }
                        }
                    });
        }

        return jeton;
    }
}
