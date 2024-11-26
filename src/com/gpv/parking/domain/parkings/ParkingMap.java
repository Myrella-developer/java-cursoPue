/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gpv.parking.domain.parkings;

import com.gpv.parking.domain.Parking;
import com.gpv.parking.domain.ParkingAbstract;
import com.gpv.parking.domain.Plaza;
import com.gpv.parking.domain.TipoEmisiones;
import com.gpv.parking.domain.TipoVehiculo;
import com.gpv.parking.domain.Vehiculo;
import com.gpv.parking.domain.vehiculos.TodoTerreno;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 *
 * @author pue
 */
// PLAZAS M , C, T , R
// M-1 , M-2, M-3 
// V getVehiculo(P plaza); SOLO EN UNA LINEA
public class ParkingMap
        extends ParkingAbstract<Vehiculo, Plaza> implements Parking<Vehiculo, Plaza> {

    private Map<PlazaMap, Vehiculo> elParking;

    public ParkingMap(int numC, int numM, int numT, int numR) {
        super(numC + numM + numT + numR, TipoVehiculo.values());
        creaPlazas(numC, numM, numT, numR);
    }

    private void creaPlazas(int numC, int numM, int numT, int numR) {
        elParking =new TreeMap<>(); // new HashMap<>();
        for (int n = 0; n < numC; n++) {
            PlazaMap key = new PlazaMap(String.valueOf(n), TipoVehiculo.C);
            Vehiculo value = null;
            elParking.put(key, value);
        }
        for (int n = 0; n < numM; n++) {
            PlazaMap key = new PlazaMap(String.valueOf(n), TipoVehiculo.M);
            Vehiculo value = null;
            elParking.put(key, value);
        }
        for (int n = 0; n < numT; n++) {
            PlazaMap key = new PlazaMap(String.valueOf(n), TipoVehiculo.T);
            Vehiculo value = null;
            elParking.put(key, value);
        }
        for (int n = 0; n < numR; n++) {
            PlazaMap key = new PlazaMap(String.valueOf(n), TipoVehiculo.R);
            Vehiculo value = null;
            elParking.put(key, value);
        }
    }

    @Override
    public boolean hayPlaza(Vehiculo t) {
        Map.Entry<PlazaMap, Vehiculo> entry = buscarPlazaLibre(t);
        return entry != null;
    }

    @Override
    public int getPlazasDisponibles() {
        return super.getNumPlazas() - getVehiculos().size();
    }

    private Map.Entry<PlazaMap, Vehiculo> buscarPlazaLibre(Vehiculo t) {
        for (Map.Entry<PlazaMap, Vehiculo> entry : this.elParking.entrySet()) {
            if (entry.getKey().getTipo() == t.getTipoVehiculo()) {
                if (entry.getValue() == null) {
                    return entry;
                }
            }
        }
        if(t.getTipoVehiculo()==TipoVehiculo.R){
            Vehiculo tT = new TodoTerreno(t.getMatricula(), t.getClasificacionEmisiones());
            return buscarPlazaLibre(tT);
        }
        return null;
    }
    //LOS VEHICULOS DE R SINO TIENE PLAZA PUEDEN APARCAR EN LAS PLAZAS T
    //NO REPETIR MATRICULAS
    @Override
    public boolean aparcar(Vehiculo t) {
        if(buscarVehiculo(t.getMatricula())!=null) return false;
        
        Map.Entry<PlazaMap, Vehiculo> entry = buscarPlazaLibre(t);
        if (entry != null) {
            entry.setValue(t);
            int numMinutos = (int) (Math.random() * 123);
            t.setHoraEntrada(LocalDateTime.now().minusMinutes(numMinutos));
            entry.getKey().setMatricula(t.getMatricula());
            entry.getKey().setLibre(false);
            return true;
        }
        return false;
    }

    private PlazaMap buscarVehiculo(String matricula) {
        PlazaMap laPlaza = null;
        for (PlazaMap plaza : this.elParking.keySet()) {
            if (!plaza.isLibre() && plaza.getMatricula().equals(matricula)) {
                laPlaza = plaza;
                break;
            }
        }
        return laPlaza;
    }

    @Override
    public double pagar(String matricula
    ) {
        double total = 0.0;
        PlazaMap laPlaza = buscarVehiculo(matricula);
        if (laPlaza == null) {
            return -1.0;
        }
        Vehiculo elVehiculo = this.elParking.get(laPlaza);
        Duration d = Duration.between(elVehiculo.getHoraEntrada(), LocalDateTime.now());
        long dias = d.toDays();
        int horas = d.toHoursPart();
        int minutos = d.toMinutesPart();
        horas += (minutos > 0) ? 1 : 0;
        double totalHoras = horas * 1.25;
        if (totalHoras > 25.00) {
            totalHoras = 25.00;
        }
        total = (dias * 25.00) + totalHoras;
        elVehiculo.setPagado(true);
        return total;
    }

    @Override
    public boolean retirar(String matricula) {

        PlazaMap laPlaza = buscarVehiculo(matricula);
        if (laPlaza == null) {
            return false;
        } else {
            Vehiculo elVehiculo = this.elParking.get(laPlaza);
            if (elVehiculo.estaPagado()) {
                this.elParking.put(laPlaza, null);
                laPlaza.setLibre(true);
                laPlaza.setMatricula(null);
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public Vehiculo getVehiculo(Plaza plaza    ) {
        return this.elParking.get((PlazaMap) plaza);
    }

    @Override
    public Plaza getPlaza(String matricula    ) {
        PlazaMap laPlaza = buscarVehiculo(matricula);
        return laPlaza;
    }

    @Override
    public List<Vehiculo> getVehiculos() {
        List<Vehiculo> lstV = new ArrayList<>(this.elParking.values());
        lstV.removeIf(v -> v == null);
        return lstV;
    }

    @Override
    public List<Plaza> getPlazas() {
        return new ArrayList<>(this.elParking.keySet());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\n");
        BiConsumer<Plaza, Vehiculo> biConsumer =
                ( p, v) -> sb.append(p.toString())
                           .append( "\t").append(v!=null? v.toString():"") 
                           .append("\n");
                
        this.elParking.forEach(biConsumer);
        return sb.toString(); 
    }

    
    
    public static class PlazaMap implements Plaza, Comparable<Plaza> {

        private String identificador;
        private TipoVehiculo tipo;
        private boolean libre;
        private String matricula;

        public PlazaMap(String identificador, TipoVehiculo tipo) {
            this.identificador = identificador;
            this.tipo = tipo;
            this.libre = true;
            this.matricula = null;
        }

        @Override
        public String getIdentificador() {
            return identificador;
        }

        @Override
        public TipoVehiculo getTipo() {
            return tipo;
        }

        public void setLibre(boolean libre) {
            this.libre = libre;
        }

        public void setMatricula(String matricula) {
            this.matricula = matricula;
        }

        public boolean isLibre() {
            return libre;
        }

        public String getMatricula() {
            return matricula;
        }

        @Override
        public String toString() {
            return "PlazaMap: " + identificador + "-" + tipo + " "
                    + (this.libre ? "Libre" : "Ocupado " + matricula);
        }

//        @Override
//        public int hashCode() {
//            return tipo.hashCode(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
//        }
        @Override
        public int hashCode() {
            return tipo.hashCode(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        @Override
        public int compareTo(Plaza o) {
            int r = tipo.compareTo(o.getTipo());
            if(r==0){
                r = identificador.compareTo(o.getIdentificador());
            }
            return r;
        }

    }
}
