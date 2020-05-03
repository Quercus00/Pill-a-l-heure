package com.example.transverse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import androidx.appcompat.app.AppCompatDialogFragment;

public class Alertdialog extends AppCompatDialogFragment {

    @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Supprimer mon compte")
                    .setMessage("Souhaitez vous supprimer votre compte ? (cette action est irréversible)")
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                user.delete();
                                Toast.makeText(null, "votre compte a été supprimé", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(null, "Erreur, aucun utilisateur trouvé", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

            return builder.create();
        }
}

