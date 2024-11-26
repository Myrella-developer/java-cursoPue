/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gpv.parking.domain.vehiculos;

import com.gpv.parking.domain.TipoEmisiones;
import com.gpv.parking.domain.TipoVehiculo;


public class TodoTerreno extends Coche{
    
    public TodoTerreno(String matricula, TipoEmisiones emisiones){
        super(matricula, emisiones);
    }

    @Override
    public TipoVehiculo getTipoVehiculo() {
        return TipoVehiculo.T;
    }
    
}
