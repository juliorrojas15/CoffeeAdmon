package com.example.coffeetimeadmon;

public class Cierres_Entidad_Nomina {
    String sFecha;
    int iTazas,iComision;
    String sNombreCierre;

    public Cierres_Entidad_Nomina(String sFecha, int iTazas, int iComision,String sNombreCierre) {
        this.sFecha = sFecha;
        this.iTazas = iTazas;
        this.iComision = iComision;
        this.sNombreCierre=sNombreCierre;
    }

    public String getsNombreCierre() {
        return sNombreCierre;
    }

    public void setsNombreCierre(String sNombreCierre) {
        this.sNombreCierre = sNombreCierre;
    }

    public String getsFecha() {
        return sFecha;
    }

    public void setsFecha(String sFecha) {
        this.sFecha = sFecha;
    }

    public int getiTazas() {
        return iTazas;
    }

    public void setiTazas(int iTazas) {
        this.iTazas = iTazas;
    }

    public int getiComision() {
        return iComision;
    }

    public void setiComision(int iComision) {
        this.iComision = iComision;
    }
}
