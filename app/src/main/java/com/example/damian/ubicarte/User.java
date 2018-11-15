package com.example.damian.ubicarte;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
class User{
    public String Nombre;
    public String Apellidos;
    public String Telefono;
    public String CorreoElectronico;

    public User(){}

    public User(String name, String ap, String tel, String CE){
        this.Nombre=name;
        this.Apellidos=ap;
        this.Telefono=tel;
        this.CorreoElectronico=CE;
    }
}
