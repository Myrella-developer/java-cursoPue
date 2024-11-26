package com.gpv.parking.domain.vehiculos;

import com.gpv.parking.domain.TipoEmisiones;
import com.gpv.parking.domain.TipoVehiculo;
import com.gpv.parking.domain.VehiculoAbstract;

public class Moto extends VehiculoAbstract  {
    public Moto(String matricula, TipoEmisiones emisiones) {        
        super(matricula, emisiones );
    }

    @Override
    public TipoVehiculo getTipoVehiculo() {
        return TipoVehiculo.M;
    }
  
}


