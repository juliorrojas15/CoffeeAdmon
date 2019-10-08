package com.example.coffeetimeadmon;

public class Distribuidor_Entidad {
    String sNombre;
    long lCelular;
    int iAhorro;
    int iDeuda;
    int iTazas;
    int iAromaticas,iInstacrem,iMantecada,iLiberal,iAlmojabana,iArepa, iMustang,iLucky;
    int iEntTazas,iDevTazas,iEntDinero;
    String sCierreAbierto,sUltimoCierre;

    public Distribuidor_Entidad(String sNombre, long lCelular, int iAhorro, int iDeuda, int iTazas,
                                int iAromaticas, int iInstacrem, int iMantecada, int iLiberal,
                                int iAlmojabana, int iArepa, int iMustang, int iLucky,int iEntTazas,
                                int iDevTazas,int iEntDinero,String sCierreAbierto,String sUltimoCierre) {
        this.sNombre = sNombre;
        this.lCelular = lCelular;
        this.iAhorro = iAhorro;
        this.iDeuda = iDeuda;
        this.iTazas = iTazas;
        this.iAromaticas = iAromaticas;
        this.iInstacrem = iInstacrem;
        this.iMantecada = iMantecada;
        this.iLiberal = iLiberal;
        this.iAlmojabana = iAlmojabana;
        this.iArepa = iArepa;
        this.iMustang = iMustang;
        this.iLucky = iLucky;
        this.iEntTazas=iEntTazas;
        this.iDevTazas=iDevTazas;
        this.iEntDinero=iEntDinero;
        this.sCierreAbierto=sCierreAbierto;
        this.sUltimoCierre=sUltimoCierre;
    }

    public String getsCierreAbierto() {
        return sCierreAbierto;
    }

    public void setsCierreAbierto(String sCierreAbierto) {
        this.sCierreAbierto = sCierreAbierto;
    }

    public int getiEntTazas() {
        return iEntTazas;
    }

    public void setiEntTazas(int iEntTazas) {
        this.iEntTazas = iEntTazas;
    }

    public int getiDevTazas() {
        return iDevTazas;
    }

    public void setiDevTazas(int iDevTazas) {
        this.iDevTazas = iDevTazas;
    }

    public int getiEntDinero() {
        return iEntDinero;
    }

    public void setiEntDinero(int iEntDinero) {
        this.iEntDinero = iEntDinero;
    }

    public int getiAromaticas() {
        return iAromaticas;
    }

    public void setiAromaticas(int iAromaticas) {
        this.iAromaticas = iAromaticas;
    }

    public int getiInstacrem() {
        return iInstacrem;
    }

    public void setiInstacrem(int iInstacrem) {
        this.iInstacrem = iInstacrem;
    }

    public int getiMantecada() {
        return iMantecada;
    }

    public void setiMantecada(int iMantecada) {
        this.iMantecada = iMantecada;
    }

    public int getiLiberal() {
        return iLiberal;
    }

    public void setiLiberal(int iLiberal) {
        this.iLiberal = iLiberal;
    }

    public int getiAlmojabana() {
        return iAlmojabana;
    }

    public void setiAlmojabana(int iAlmojabana) {
        this.iAlmojabana = iAlmojabana;
    }

    public int getiArepa() {
        return iArepa;
    }

    public void setiArepa(int iArepa) {
        this.iArepa = iArepa;
    }

    public int getiMustang() {
        return iMustang;
    }

    public void setiMustang(int iMustang) {
        this.iMustang = iMustang;
    }

    public int getiLucky() {
        return iLucky;
    }

    public void setiLucky(int iLucky) {
        this.iLucky = iLucky;
    }

    public String getsNombre() {
        return sNombre;
    }

    public void setsNombre(String sNombre) {
        this.sNombre = sNombre;
    }

    public long getlCelular() {
        return lCelular;
    }

    public void setlCelular(long iCelular) {
        this.lCelular = iCelular;
    }

    public int getiAhorro() {
        return iAhorro;
    }

    public void setiAhorro(int iAhorro) {
        this.iAhorro = iAhorro;
    }

    public int getiDeuda() {
        return iDeuda;
    }

    public void setiDeuda(int iDeuda) {
        this.iDeuda = iDeuda;
    }

    public int getiTazas() {
        return iTazas;
    }

    public void setiTazas(int iTazas) {
        this.iTazas = iTazas;
    }

    public String getsUltimoCierre() {
        return sUltimoCierre;
    }

    public void setsUltimoCierre(String sUltimoCierre) {
        this.sUltimoCierre = sUltimoCierre;
    }
}
