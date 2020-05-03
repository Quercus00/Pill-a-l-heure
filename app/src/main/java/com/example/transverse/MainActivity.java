package com.example.transverse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 666; // n'importe quelle valeur, sert juste de condition pour l'authentification
    List<AuthUI.IdpConfig> providers; // liste des modes d'authentification
    private Button btn_sign_out;

    private static final String TAG = "MyActivity";

    private static final String KEY_NOM  = "Nom";
    private static final String KEY_MAIL  = "email";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_sign_out = findViewById(R.id.btn_sign_out);
        //Code en dessous actif que si on clique sur déconnecter
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logout
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                btn_sign_out.setEnabled(false);
                                //test = 0;
                                showSingInOptions();
                        }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //-----------------------------------------------------------------------------


        providers= Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build()//, // Email builder
                //new AuthUI.IdpConfig.PhoneBuilder().build()   en stand by
        );
            showSingInOptions();
    }


    private void showSingInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.MyTheme)
                .build(),MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK){
                //get user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //show email on toast (notif discrète en bas d'écran)
                Toast.makeText(this, "Vous êtes connecté sur : "+user.getEmail(), Toast.LENGTH_SHORT).show();
                //set button sign out
                btn_sign_out.setEnabled(true);


                /*
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> utilisateur = new HashMap<>();


                String nom = user.getDisplayName();
                String email = user.getEmail();
                utilisateur.put(KEY_NOM, nom);
                utilisateur.put(KEY_MAIL, email);
                db.collection("utilisateurs")
                        .add(utilisateur)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

                 */


                Intent intent = new Intent(this, Accueil.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
