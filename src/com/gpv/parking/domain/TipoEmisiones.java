/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.gpv.parking.domain;

//  public static final char[] TIPOS_EMISIONES = {'0', 'E', 'C', 'B', 'A'};
public enum TipoEmisiones {
    CERO("Cero"),
    ECO("Eco"), 
    A("A"),
    B("B"),
    C("C");
    private TipoEmisiones(String desc){
        this.desc=desc;
    }
    private String desc;
    public String getDescripcion(){
        return this.desc;
    }
    
}
