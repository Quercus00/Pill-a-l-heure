package com.example.transverse.activités;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transverse.autres.Alertdialog;
import com.example.transverse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class activity_profil extends AppCompatActivity {

    Button btn_retour;
    Button btn_deconexion;
    Button btn_delete;
    Button btn_mdp;

    TextView txt_info;
    TextView txt_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // configure le titre de la page
        try {
            this.getSupportActionBar().setTitle("Médishare - Mon profil");
        }
        catch (NullPointerException e){}

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

        // code pour supprimer le compte via la classe Alert dialog (message de dernière chance)
        btn_delete = findViewById(R.id.suppr_compte);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        btn_mdp = findViewById(R.id.btn_mdp);
        btn_mdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetMdp();
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

    public void resetMdp() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email ="";
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                email = profile.getEmail();
            }
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email);
        Toast.makeText(this, "Un mail vous a été renvoyé pour réinitialiser votre mot de passe", Toast.LENGTH_LONG).show();
    }

}
