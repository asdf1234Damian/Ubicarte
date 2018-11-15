package com.example.damian.ubicarte;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Vehicle {
    public String Placas;
    public String Modelo;
    public String Propietario;

    public Vehicle(){
    }

    public Vehicle(String pla, String mod, String pro){
        this.Modelo=mod;
        this.Placas=pla;
        this.Propietario=pro;
    }

}
