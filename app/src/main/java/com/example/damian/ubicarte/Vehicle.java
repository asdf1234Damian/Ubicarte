package com.example.damian.ubicarte;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Vehicle {
    public String Placas;
    public String Modelo;
    public String Propietario;
    public String lat;
    public String lng;

    public Vehicle(){
    }

    public Vehicle(String pla, String mod, String pro, String lat, String lng){
        this.Modelo=mod;
        this.Placas=pla;
        this.Propietario=pro;
        this.lat=lat;
        this.lng=lng;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getModelo() {
        return Modelo;
    }

    public String getPropietario() {
        return Propietario;
    }

    public String getPlacas() {
        return Placas;
    }

}
