package com.example.coffeetimeadmon;

public class Personal_Entidad {
    String sNombre;
    String sPerfil;
    int iClave;

    public Personal_Entidad(String sNombre, String sPerfil, int iClave) {
        this.sNombre = sNombre;
        this.sPerfil = sPerfil;
        this.iClave = iClave;
    }

    public String getsNombre() {
        return sNombre;
    }

    public void setsNombre(String sNombre) {
        this.sNombre = sNombre;
    }

    public String getsPerfil() {
        return sPerfil;
    }

    public void setsPerfil(String sPerfil) {
        this.sPerfil = sPerfil;
    }

    public int getiClave() {
        return iClave;
    }

    public void setiClave(int iClave) {
        this.iClave = iClave;
    }
}
