
package com.gpv.parking.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public abstract class VehiculoAbstract implements Vehiculo{
    private final String matricula;
    private LocalDateTime horaEntrada;
    private boolean pagado;
    private final TipoEmisiones emisiones;
    
    public VehiculoAbstract(String matricula, TipoEmisiones emisiones) {
        this.matricula = matricula;
        pagado = false;
        this.emisiones = emisiones;
    }
    @Override
    public String getMatricula() {
        return this.matricula;
    }

    
    
    @Override
    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    @Override
    public void setHoraEntrada(LocalDateTime hora) {
        this.horaEntrada = hora;
    }
    
     @Override
    public boolean estaPagado() {
        return this.pagado;
    }

    @Override
    public void setPagado(boolean value) {
        this.pagado = value;
    }
    
    @Override
    public TipoEmisiones getClasificacionEmisiones() {
        return this.emisiones;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" Matricula: ").append(this.getMatricula())
                .append(" Tipo: ").append(this.getTipoVehiculo().getDescripcion())
                .append(" Emisones: ").append(
                        this.getClasificacionEmisiones().getDescripcion());
        if (this.getHoraEntrada() != null) {
            sb.append(" Hora Entrada: ").append(
                    this.getHoraEntrada().format(fmt));
        }
        sb.append(estaPagado() ? " Pagado" : " No Pagado");
        return sb.toString();
    }
}
