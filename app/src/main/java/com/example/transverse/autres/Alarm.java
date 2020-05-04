package com.example.transverse.autres;

import android.widget.EditText;

import java.util.Calendar;


public class Alarm {
    Calendar date;
    String nom;

    public Alarm(Calendar date, String nom) {
        this.date = date;
        this.nom = nom;
    }

    public Alarm(){};


    public Calendar getDate() {
        return date;
    }

    public String getNom() {
        return nom;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "date=" + date +
                '}';
    }


}
