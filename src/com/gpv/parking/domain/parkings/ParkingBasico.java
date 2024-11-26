package com.gpv.parking.domain.parkings;

import com.gpv.parking.domain.ParkingAbstract;
import com.gpv.parking.domain.Plaza;
import com.gpv.parking.domain.TipoVehiculo;
import com.gpv.parking.domain.Vehiculo;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParkingBasico extends ParkingAbstract {

    private final Vehiculo[] plazas;
    private int plazasOcupadas;

    public ParkingBasico(int numPlazas) {
        super(numPlazas, TipoVehiculo.values());
        plazas = new Vehiculo[numPlazas];
        plazasOcupadas = 0;
    }

    @Override
    public int getPlazasDisponibles() {
        return this.getNumPlazas() - plazasOcupadas;
    }

    @Override
    public boolean hayPlaza(Vehiculo vehiculo) {
        if (!estaPermitido(vehiculo.getTipoVehiculo())) {
            return false;
        }
        return this.getPlazasDisponibles() > 0;
    }

    private int buscarhueco() { //-1 sino encuentra >=0 pos
        int pos = -1;
        for (int i = 0; i < plazas.length; i++) {
            if (plazas[i] == null) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    @Override
    public boolean aparcar(Vehiculo vehiculo) {
        if (!hayPlaza(vehiculo)) {
            return false;
        }
        int pos = buscarhueco();
        int numMinutos = (int) (Math.random() * 123);
        vehiculo.setHoraEntrada(LocalDateTime.now().minusMinutes(numMinutos));
        plazas[pos] = vehiculo;
        plazasOcupadas++;
        return true;
    }

    private int buscarMatricula(String matricula) {
        int pos = -1;
        for (int i = 0; i < plazas.length; i++) {
            if (plazas[i] != null && plazas[i].getMatricula().equals(matricula)) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    @Override
    public double pagar(String matricula) {
        int pos = buscarMatricula(matricula);
        if (pos == -1) {
            System.out.println("Matricula no encontrada");
            return -1.0;
        }
        Vehiculo elVehiculo = plazas[pos];
        //1.25€ o 25€ dia

        Duration d = Duration.between(elVehiculo.getHoraEntrada(), LocalDateTime.now());
        long dias = d.toDays();
        int horas = d.toHoursPart();
        int minutos = d.toMinutesPart();
        //  System.out.println("dias: " + dias + " horas " + horas + " minutos " + minutos);
        horas += (minutos > 0) ? 1 : 0;
        double totalHoras = horas * 1.25;
        if (totalHoras > 25.00) {
            totalHoras = 25.00;
        }
        double total = (dias * 25.00) + totalHoras;
        //  System.out.println("Total " + total + " total horas " + totalHoras);
        elVehiculo.setPagado(true);
        return total;
    }

    @Override
    public boolean retirar(String matricula) {
        int pos = buscarMatricula(matricula);
        if (pos == -1) {
            System.out.println("Matricula no encontrada");
            return false;
        }
        Vehiculo elVehiculo = plazas[pos];
        if (!elVehiculo.estaPagado()) {
            return false;
        }
        plazas[pos] = null;
        plazasOcupadas--;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\nVehículos en parking:\n");
        for (int i = 0; i < plazas.length; i++) {
            sb.append("Plaza: ").append(i + 1);
            if (plazas[i] == null) {
                sb.append(" Libre\n");
            } else {
                sb.append(" ").append(plazas[i].toString()).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public Vehiculo getVehiculo(Plaza plaza) {
        Vehiculo v = null;
        int pos =Integer.parseInt( plaza.getIdentificador());
        if(pos >=0 && pos < plazas.length){
            v = plazas[pos];
        }
        return v;
    }

    @Override
    public Plaza getPlaza(String matricula) {
        for (int i = 0; i < this.plazas.length; i++) {
            if (this.plazas[i] != null) {
               if(this.plazas[i].getMatricula().equals(matricula)){
                  return new PlazaBasico(i);
               }
            }
        }
        return null;
    }

    @Override
    public List<Vehiculo> getVehiculos() {
        List<Vehiculo> lstVehiculos = new ArrayList<>();
        for (int i = 0; i < this.plazas.length; i++) {
            if (this.plazas[i] != null) {
               lstVehiculos.add(this.plazas[i]);
            }
        }
        return lstVehiculos;
    }

    @Override
    public List<Plaza> getPlazas() {
        List<Plaza> lstPlazas = new ArrayList<>();
        for (int i = 0; i < this.plazas.length; i++) {
            lstPlazas.add(new PlazaBasico(i));
        }
        return lstPlazas;
    }

    public static class PlazaBasico implements Plaza {

        private int pos;
        //private TipoVehiculo

        public PlazaBasico(int pos) {
            this.pos = pos;
        }

        @Override
        public String getIdentificador() {
            return String.valueOf(pos);
        }

        @Override
        public TipoVehiculo getTipo() {
            return TipoVehiculo.U;
        }

        @Override
        public String toString() {
            return "Plaza Basico: " + pos; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }
        
    }
}
