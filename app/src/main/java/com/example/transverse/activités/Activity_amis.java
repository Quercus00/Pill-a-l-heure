package com.example.transverse.activités;

import android.os.Bundle;

import com.example.transverse.R;
import com.example.transverse.autres.InstructionDialog;
import com.example.transverse.autres.InstructionDialog2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_amis extends AppCompatActivity {

    private static final String TAG = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> liste_amis = new ArrayList<>();
    private EditText input_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        openInstruction();

        try {
            this.getSupportActionBar().setTitle("Mes amis");
        }
        catch (NullPointerException e){}


        /** code pour lire la base de données (lecture de 5jY2Mzq02tKS94sUM3Zq)
         * ------------------------- non fonctionnel -------------------------
         * bug possible -> problème d'autorisations de lecture/ecriture : non résolu
         *
         * pas de plantage lors de l'éxécution, aucun affichage de la liste qui récupère les données

        DocumentReference docRef = db.collection("utilisateurs").document("5jY2Mzq02tKS94sUM3Zq");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        liste_amis.add(document.getData().toString());
                        System.out.println("test");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        final ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_listview, liste_amis);
        final ListView listView = findViewById(R.id.liste_amis);
        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); // censé activer les checkbox
        listView.setAdapter(adapter);
        */
        //----------------------------------------------------------------------------------------------------------------------------



        input_mail = findViewById(R.id.input_mail);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liste_amis.add(input_mail.getText().toString());
            }
        });



        final ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_listview, liste_amis);
        final ListView listView = findViewById(R.id.liste_amis);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); // censé activer les checkbox
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray positionchecker = listView.getCheckedItemPositions();

                for (int i = listView.getCount(); i >=0 ; i --){
                    if (positionchecker.get(i)){
                        adapter.remove(liste_amis.get(i));
                    }
                }

                positionchecker.clear();
                adapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    // popup pour informer sur la suppression d'alarme (cf InstructionDialog.java)
    public void openInstruction() {
        InstructionDialog2 exampleDialog = new InstructionDialog2();
        exampleDialog.show(getSupportFragmentManager(), "");
    }
}
