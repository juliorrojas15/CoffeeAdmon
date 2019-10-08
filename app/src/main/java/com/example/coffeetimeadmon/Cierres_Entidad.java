package com.example.coffeetimeadmon;

public class Cierres_Entidad {
    
    
    String sNumTicket;
    int iAño,iMes,iFecha;
    String sDia,sHora;
    int iEntVasosPrevios,iEntVasosHoy,iEntAromatica,iEntInstacrem,iEntMantecada,iEntLiberal,iEntAlmojabana,iEntArepa,iEntMustang,iEntLucky;
    int iDevVasosPrevios,iDevVasosHoy,iDevAromatica,iDevInstacrem,iDevMantecada,iDevLiberal,iDevAlmojabana,iDevArepa,iDevMustang,iDevLucky;
    int iDineroPrevio,iDineroDia,iDineroMulta,iDineroComisionAdd;

    public Cierres_Entidad(String sNumTicket, int iAño, int iMes, int iFecha, String sDia, String sHora, int iEntVasosPrevios, int iEntVasosHoy, int iEntAromatica, int iEntInstacrem, int iEntMantecada, int iEntLiberal, int iEntAlmojabana, int iEntArepa, int iEntMustang, int iEntLucky, int iDevVasosPrevios, int iDevVasosHoy, int iDevAromatica, int iDevInstacrem, int iDevMantecada, int iDevLiberal, int iDevAlmojabana, int iDevArepa, int iDevMustang, int iDevLucky,  int iDineroPrevio, int iDineroDia, int iDineroMulta, int iDineroComisionAdd) {
        this.sNumTicket = sNumTicket;
        this.iAño = iAño;
        this.iMes = iMes;
        this.iFecha = iFecha;
        this.sDia = sDia;
        this.sHora = sHora;
        this.iEntVasosPrevios = iEntVasosPrevios;
        this.iEntVasosHoy = iEntVasosHoy;
        this.iEntAromatica = iEntAromatica;
        this.iEntInstacrem = iEntInstacrem;
        this.iEntMantecada = iEntMantecada;
        this.iEntLiberal = iEntLiberal;
        this.iEntAlmojabana = iEntAlmojabana;
        this.iEntArepa = iEntArepa;
        this.iEntMustang = iEntMustang;
        this.iEntLucky = iEntLucky;
        this.iDevVasosPrevios = iDevVasosPrevios;
        this.iDevVasosHoy = iDevVasosHoy;
        this.iDevAromatica = iDevAromatica;
        this.iDevInstacrem = iDevInstacrem;
        this.iDevMantecada = iDevMantecada;
        this.iDevLiberal = iDevLiberal;
        this.iDevAlmojabana = iDevAlmojabana;
        this.iDevArepa = iDevArepa;
        this.iDevMustang = iDevMustang;
        this.iDevLucky = iDevLucky;
        this.iDineroPrevio = iDineroPrevio;
        this.iDineroDia = iDineroDia;
        this.iDineroMulta = iDineroMulta;
        this.iDineroComisionAdd = iDineroComisionAdd;
    }

    public String getsNumTicket() {
        return sNumTicket;
    }

    public void setsNumTicket(String sNumTicket) {
        this.sNumTicket = sNumTicket;
    }

    public int getiAño() {
        return iAño;
    }

    public void setiAño(int iAño) {
        this.iAño = iAño;
    }

    public int getiMes() {
        return iMes;
    }

    public void setiMes(int iMes) {
        this.iMes = iMes;
    }

    public int getiFecha() {
        return iFecha;
    }

    public void setiFecha(int iFecha) {
        this.iFecha = iFecha;
    }

    public String getsDia() {
        return sDia;
    }

    public void setsDia(String sDia) {
        this.sDia = sDia;
    }

    public String getsHora() {
        return sHora;
    }

    public void setsHora(String sHora) {
        this.sHora = sHora;
    }


    public int getiEntVasosPrevios() {
        return iEntVasosPrevios;
    }

    public void setiEntVasosPrevios(int iEntVasosPrevios) {
        this.iEntVasosPrevios = iEntVasosPrevios;
    }

    public int getiEntVasosHoy() {
        return iEntVasosHoy;
    }

    public void setiEntVasosHoy(int iEntVasosHoy) {
        this.iEntVasosHoy = iEntVasosHoy;
    }

    public int getiEntAromatica() {
        return iEntAromatica;
    }

    public void setiEntAromatica(int iEntAromatica) {
        this.iEntAromatica = iEntAromatica;
    }

    public int getiEntInstacrem() {
        return iEntInstacrem;
    }

    public void setiEntInstacrem(int iEntInstacrem) {
        this.iEntInstacrem = iEntInstacrem;
    }

    public int getiEntMantecada() {
        return iEntMantecada;
    }

    public void setiEntMantecada(int iEntMantecada) {
        this.iEntMantecada = iEntMantecada;
    }

    public int getiEntLiberal() {
        return iEntLiberal;
    }

    public void setiEntLiberal(int iEntLiberal) {
        this.iEntLiberal = iEntLiberal;
    }

    public int getiEntAlmojabana() {
        return iEntAlmojabana;
    }

    public void setiEntAlmojabana(int iEntAlmojabana) {
        this.iEntAlmojabana = iEntAlmojabana;
    }

    public int getiEntArepa() {
        return iEntArepa;
    }

    public void setiEntArepa(int iEntArepa) {
        this.iEntArepa = iEntArepa;
    }

    public int getiEntMustang() {
        return iEntMustang;
    }

    public void setiEntMustang(int iEntMustang) {
        this.iEntMustang = iEntMustang;
    }

    public int getiEntLucky() {
        return iEntLucky;
    }

    public void setiEntLucky(int iEntLucky) {
        this.iEntLucky = iEntLucky;
    }

    public int getiDevVasosPrevios() {
        return iDevVasosPrevios;
    }

    public void setiDevVasosPrevios(int iDevVasosPrevios) {
        this.iDevVasosPrevios = iDevVasosPrevios;
    }

    public int getiDevVasosHoy() {
        return iDevVasosHoy;
    }

    public void setiDevVasosHoy(int iDevVasosHoy) {
        this.iDevVasosHoy = iDevVasosHoy;
    }

    public int getiDevAromatica() {
        return iDevAromatica;
    }

    public void setiDevAromatica(int iDevAromatica) {
        this.iDevAromatica = iDevAromatica;
    }

    public int getiDevInstacrem() {
        return iDevInstacrem;
    }

    public void setiDevInstacrem(int iDevInstacrem) {
        this.iDevInstacrem = iDevInstacrem;
    }

    public int getiDevMantecada() {
        return iDevMantecada;
    }

    public void setiDevMantecada(int iDevMantecada) {
        this.iDevMantecada = iDevMantecada;
    }

    public int getiDevLiberal() {
        return iDevLiberal;
    }

    public void setiDevLiberal(int iDevLiberal) {
        this.iDevLiberal = iDevLiberal;
    }

    public int getiDevAlmojabana() {
        return iDevAlmojabana;
    }

    public void setiDevAlmojabana(int iDevAlmojabana) {
        this.iDevAlmojabana = iDevAlmojabana;
    }

    public int getiDevArepa() {
        return iDevArepa;
    }

    public void setiDevArepa(int iDevArepa) {
        this.iDevArepa = iDevArepa;
    }

    public int getiDevMustang() {
        return iDevMustang;
    }

    public void setiDevMustang(int iDevMustang) {
        this.iDevMustang = iDevMustang;
    }

    public int getiDevLucky() {
        return iDevLucky;
    }

    public void setiDevLucky(int iDevLucky) {
        this.iDevLucky = iDevLucky;
    }


    public int getiDineroPrevio() {
        return iDineroPrevio;
    }

    public void setiDineroPrevio(int iDineroPrevio) {
        this.iDineroPrevio = iDineroPrevio;
    }

    public int getiDineroDia() {
        return iDineroDia;
    }

    public void setiDineroDia(int iDineroDia) {
        this.iDineroDia = iDineroDia;
    }
    public int getiDineroMulta() {
        return iDineroMulta;
    }

    public void setiDineroMulta(int iDineroMulta) {
        this.iDineroMulta = iDineroMulta;
    }

    public int getiDineroComisionAdd() {
        return iDineroComisionAdd;
    }

    public void setiDineroComisionAdd(int iDineroComisionAdd) {
        this.iDineroComisionAdd = iDineroComisionAdd;
    }
}
