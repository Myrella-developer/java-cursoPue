
package com.gpv.parking.domain.vehiculos;

import com.gpv.parking.domain.TipoEmisiones;
import com.gpv.parking.domain.TipoVehiculo;
import com.gpv.parking.domain.Vehiculo;
import com.gpv.parking.domain.VehiculoAbstract;
import java.time.LocalDateTime;


public class Coche extends VehiculoAbstract{
    private final boolean adaptado;
    public Coche(String matricula, TipoEmisiones emisiones){
        this(matricula, emisiones, false);      
    }
    public Coche(String matricula, TipoEmisiones emisiones, boolean  adaptado){
        super(matricula, emisiones);
        this.adaptado=adaptado;
    }
    public boolean estaAdaptado(){
        return adaptado;
    }
    
    @Override
    public TipoVehiculo getTipoVehiculo() {
        return adaptado ? TipoVehiculo.R : TipoVehiculo.C;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if(this.estaAdaptado()){
            sb.append(" Vehículo adaptado para movilidad reducida.");
        }
        return sb.toString(); 
    }
    
    
}