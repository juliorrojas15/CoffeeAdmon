package com.example.coffeetimeadmon;

public class Productos_Entidad {
    int iItem;
    String sNombre;
    int iPrecio,iComision,iCosto,iCantidad;

    public Productos_Entidad(int iItem, String sNombre, int iPrecio, int iComision, int iCosto, int iCantidad) {
        this.iItem = iItem;
        this.sNombre = sNombre;
        this.iPrecio = iPrecio;
        this.iComision = iComision;
        this.iCosto = iCosto;
        this.iCantidad = iCantidad;
    }

    public int getiItem() {
        return iItem;
    }

    public void setiItem(int iItem) {
        this.iItem = iItem;
    }

    public String getsNombre() {
        return sNombre;
    }

    public void setsNombre(String sNombre) {
        this.sNombre = sNombre;
    }

    public int getiPrecio() {
        return iPrecio;
    }

    public void setiPrecio(int iPrecio) {
        this.iPrecio = iPrecio;
    }

    public int getiComision() {
        return iComision;
    }

    public void setiComision(int iComision) {
        this.iComision = iComision;
    }

    public int getiCosto() {
        return iCosto;
    }

    public void setiCosto(int iCosto) {
        this.iCosto = iCosto;
    }

    public int getiCantidad() {
        return iCantidad;
    }

    public void setiCantidad(int iCantidad) {
        this.iCantidad = iCantidad;
    }
}
