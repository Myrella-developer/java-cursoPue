
package com.gpv.parking.domain;

// public static final char[] TIPOS_VEHICULOS = {'C', 'M', 'T', 'R'};
public enum TipoVehiculo {
    U("No Definido"),
    C("Coche"),
    M("Moto"),
    T("Todo Terreno"),
    R("Adaptado M Reducida");
    
    private TipoVehiculo(String desc){
        this.desc = desc;
    }
    private String desc;
    
    public String getDescripcion(){
        return this.desc;
    }
}
