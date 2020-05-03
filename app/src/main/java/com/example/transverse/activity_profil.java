package com.example.transverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class activity_profil extends AppCompatActivity {
    Button btn_retour;
    Button btn_deconexion;
    Button btn_delete;
    TextView txt_info;
    TextView txt_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // affiche les informations du compte sur la page
        txt_info = findViewById(R.id.txt_info);
        txt_mail = findViewById(R.id.txt_mail);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {

                // Id of the provider (ex: google.com)
                //String providerId = profile.getProviderId();

                // UID specific to the provider
                //String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();

                txt_info.setText("Bonjour " + name);
                txt_mail.setText("Vous êtes connecté sur l'adresse mail : " + email);
            }
        }

        // code pour retour arrière
        btn_retour = findViewById(R.id.btn_return);
        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotta_go_back();
            }
        });

        //code pour déconnecter
        btn_deconexion = findViewById(R.id.deconnexion);
        btn_deconexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                deconexion();
            }
        });

        btn_delete = findViewById(R.id.suppr_compte);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }


    public void openDialog() {
        Alertdialog exampleDialog = new Alertdialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
        // j'ai mis déconnexion pour que si on supprime le compte ça déconnecte direct
        // (sinon ça renvoie sur l'accueil et on voit les "anciennes" infos du compte)
        // faudra trouver un meilleure façon de faire
        deconexion();
    }


    // fonction pour retourner sur une page précédente
    public void gotta_go_back(){
        Intent intent = new Intent(this, Accueil.class);
        startActivity(intent);
    }

    public void deconexion(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}