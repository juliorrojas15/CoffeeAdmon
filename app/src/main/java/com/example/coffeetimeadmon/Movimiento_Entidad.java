package com.example.coffeetimeadmon;

public class Movimiento_Entidad {
    String sMes,sFecha,sHora,sTipo_de_Movimiento,sCategoria_1,sCategoria_2,sDescripción;
    int iCantidad,iValorUND,iTotal;

    public Movimiento_Entidad(String sMes, String sFecha, String sHora, String sTipo_de_Movimiento, String sCategoria_1, String sCategoria_2, String sDescripción, int iCantidad, int iValorUND, int iTotal) {
        this.sMes = sMes;
        this.sFecha = sFecha;
        this.sHora = sHora;
        this.sTipo_de_Movimiento = sTipo_de_Movimiento;
        this.sCategoria_1 = sCategoria_1;
        this.sCategoria_2 = sCategoria_2;
        this.sDescripción = sDescripción;
        this.iCantidad = iCantidad;
        this.iValorUND = iValorUND;
        this.iTotal = iTotal;
    }

    public String getsMes() {
        return sMes;
    }

    public void setsMes(String sMes) {
        this.sMes = sMes;
    }

    public String getsFecha() {
        return sFecha;
    }

    public void setsFecha(String sFecha) {
        this.sFecha = sFecha;
    }

    public String getsHora() {
        return sHora;
    }

    public void setsHora(String sHora) {
        this.sHora = sHora;
    }

    public String getsTipo_de_Movimiento() {
        return sTipo_de_Movimiento;
    }

    public void setsTipo_de_Movimiento(String sTipo_de_Movimiento) {
        this.sTipo_de_Movimiento = sTipo_de_Movimiento;
    }

    public String getsCategoria_1() {
        return sCategoria_1;
    }

    public void setsCategoria_1(String sCategoria_1) {
        this.sCategoria_1 = sCategoria_1;
    }

    public String getsCategoria_2() {
        return sCategoria_2;
    }

    public void setsCategoria_2(String sCategoria_2) {
        this.sCategoria_2 = sCategoria_2;
    }

    public String getsDescripción() {
        return sDescripción;
    }

    public void setsDescripción(String sDescripción) {
        this.sDescripción = sDescripción;
    }

    public int getiCantidad() {
        return iCantidad;
    }

    public void setiCantidad(int iCantidad) {
        this.iCantidad = iCantidad;
    }

    public int getiValorUND() {
        return iValorUND;
    }

    public void setiValorUND(int iValorUND) {
        this.iValorUND = iValorUND;
    }

    public int getiTotal() {
        return iTotal;
    }

    public void setiTotal(int iTotal) {
        this.iTotal = iTotal;
    }
}
