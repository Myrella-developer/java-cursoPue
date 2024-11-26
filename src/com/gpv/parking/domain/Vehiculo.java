
package com.gpv.parking.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public interface Vehiculo {
   // public static final char[] TIPOS_VEHICULOS = {'C', 'M', 'T', 'R'};
  //  public static final char[] TIPOS_EMISIONES = {'0', 'E', 'C', 'B', 'A'};
    
    public String getMatricula(); //matricula pensar en validar 
    public LocalDateTime getHoraEntrada();
    public void setHoraEntrada(LocalDateTime hora);
    public boolean estaPagado();
    public void setPagado(boolean value);
    public TipoVehiculo getTipoVehiculo(); // C M T R
    public TipoEmisiones getClasificacionEmisiones(); // 0 E C B A
    
    
        
}
