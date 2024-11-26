package com.gpv.parking.domain;

public abstract class ParkingAbstract<V extends Vehiculo, P extends Plaza> implements Parking<V, P>{

    private int numPlazas;
    private TipoVehiculo[] tiposVehiculosPermitidos;

    public ParkingAbstract(int numPlazas, TipoVehiculo[] tiposVehiculosPermitidos) {
        this.numPlazas = numPlazas;
        this.tiposVehiculosPermitidos = tiposVehiculosPermitidos;
    }

    public int getNumPlazas() {
        return numPlazas;
    }

    public TipoVehiculo[] getTiposVehiculosPermitidos() {
        return tiposVehiculosPermitidos;
    }

   // public abstract int getPlazasDisponibles();

   // public abstract boolean hayPlaza(Vehiculo vehiculo);

  //  public abstract boolean aparcar(Vehiculo vehiculo); // true si puede false sino

  //  public abstract double pagar(String matricula); //buscar el vehiculo, sino excepcion y calcualr el importe.

 //   public abstract boolean retirar(String matricula);
    //false si no esta o no pagado y true si ha pagado quitar almacen.

    protected boolean estaPermitido(TipoVehiculo tipo) {
        boolean bPermitido = false;      
        for (TipoVehiculo t : this.getTiposVehiculosPermitidos()) {
            if (t == tipo) {
                bPermitido = true;
            }
        }
        return bPermitido;
    }

    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append(this.getClass().getSimpleName());
       sb.append(" Num Plazas: ").append(getNumPlazas());
       sb.append(" Plazas disponibles: ").append(getPlazasDisponibles());
       sb.append(" Tipos permitidos: ");
       for(TipoVehiculo t: getTiposVehiculosPermitidos()) {
            sb.append( t.getDescripcion());
            sb.append(", ");
       }   
       sb.delete(sb.length()-2, sb.length());
       return sb.toString();
    }
    
    
}
