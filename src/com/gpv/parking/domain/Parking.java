
package com.gpv.parking.domain;

import java.util.List;

public interface Parking<V extends Vehiculo, P extends Plaza> {
    
    public int getNumPlazas();
    //public boolean hayPlaza(TipoVehiculo tipo);
     public boolean hayPlaza(V t);
      public abstract int getPlazasDisponibles();
    public boolean aparcar(V t);
    public double pagar(String matricula);
    public boolean retirar(String matricula);
    
    V getVehiculo(P plaza);
    P getPlaza(String matricula);
    List<V> getVehiculos();
    List<P> getPlazas();
}
