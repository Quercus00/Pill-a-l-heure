package com.example.transverse;

import android.widget.EditText;

import java.util.Calendar;


public class Alarm {
    Calendar date;
    EditText nom;

    public Alarm(Calendar date, EditText nom) {
        this.date = date;
        this.nom = nom;
    }

    public Alarm(){};


    public Calendar getDate() {
        return date;
    }

    public EditText getNom() {
        return nom;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setNom(EditText nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "date=" + date +
                '}';
    }


}
