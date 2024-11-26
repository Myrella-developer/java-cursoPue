
package com.gpv.parking.domain.parkings;

// solo permitir aparcar C T R

import com.gpv.parking.domain.Parking;
import com.gpv.parking.domain.ParkingAbstract;
import com.gpv.parking.domain.Plaza;
import com.gpv.parking.domain.TipoVehiculo;
import com.gpv.parking.domain.Vehiculo;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
 // solo C T R
// tener un numero plazas R
// un numero de plazas para el resto
// importe  T + 20%    R - 50%
//ArrayList
public class ParkingAvanzado<V extends Vehiculo, P extends Plaza> 
        extends ParkingAbstract<V,P> implements Parking<V, P>{
    
    private List<V> plazas;
    private int numPlazasCT;
    private List<V> plazasR;
    private int numPlazasR;
    public ParkingAvanzado(int numPlazas, int numPlazasR){         
        super(numPlazas+numPlazasR,
            new TipoVehiculo[]{TipoVehiculo.C, TipoVehiculo.R, TipoVehiculo.T});
        this.numPlazasCT =numPlazas;
        this.numPlazasR=numPlazasR;
        plazas=new ArrayList<>();
        plazasR=new ArrayList<>();
    }
    @Override
    public int getPlazasDisponibles() {
        return this.getNumPlazas() - (plazas.size() + plazasR.size());
    }

  
     @Override
    public boolean hayPlaza(V vehiculo) {
        if(!estaPermitido(vehiculo.getTipoVehiculo())){
            return false;
        }
        if(vehiculo.getTipoVehiculo()== TipoVehiculo.R){
            return plazasR.size() < numPlazasR;
        }
        return  plazas.size() < numPlazasCT;
    }
    
     @Override
    public boolean aparcar(V vehiculo) {
        if(!hayPlaza(vehiculo)){
            return false;
        }
        
        int numMinutos = (int) (Math.random()*123);
        vehiculo.setHoraEntrada(LocalDateTime.now().minusMinutes(numMinutos));
        if(vehiculo.getTipoVehiculo()==TipoVehiculo.R){
            plazasR.add(vehiculo);
        }else{
            plazas.add(vehiculo);
        }
        return true;
    }
    
    private int buscarMatricula(String matricula){ //c t
       int pos = -1;
       for(int i=0; i < plazas.size();i++){
           if( plazas.get(i).getMatricula().equals(matricula)){
               pos = i;
               break;
           }
       }
       return pos;
    }
    private int buscarMatriculaR(String matricula){ // r
       int pos = -1;
       for(int i=0; i < plazasR.size();i++){
           if( plazasR.get(i).getMatricula().equals(matricula)){
               pos = i;
               break;
           }
       }
       return pos;
    }
     @Override
    public double pagar(String matricula) {
       int pos =buscarMatricula(matricula);
        List<V> lista = plazas;
       if(pos==-1){
           lista = plazasR;
           pos = buscarMatriculaR(matricula);
           if(pos == -1){
               System.out.println("Matricula no encontrada");
               return -1.0;
           }  
       }
       V elVehiculo = lista.get(pos);
       //1.25€ o 25€ dia
      
       Duration d = Duration.between(elVehiculo.getHoraEntrada(), LocalDateTime.now());
       long dias = d.toDays();
       int horas = d.toHoursPart();
       int minutos = d.toMinutesPart();
     //  System.out.println("dias: " + dias + " horas " + horas + " minutos " + minutos);
       horas += (minutos > 0 ) ? 1 : 0;
       double totalHoras = horas*1.25;
       if (totalHoras>25.00){
           totalHoras=25.00;
       }
       double total = (dias*25.00) + totalHoras;
       if(elVehiculo.getTipoVehiculo()==TipoVehiculo.T){
           total= total * 1.20;
       }
       if(elVehiculo.getTipoVehiculo()==TipoVehiculo.R){
           total= total * 0.50;
       }
      //  System.out.println("Total " + total + " total horas " + totalHoras);
      elVehiculo.setPagado(true);
       return total;
    }
    
        @Override
    public boolean retirar(String matricula) {
        int pos =buscarMatricula(matricula);
       List<V> lista = plazas;
       if(pos==-1){
           lista = plazasR;
           pos = buscarMatriculaR(matricula);
           if(pos == -1){
               System.out.println("Matricula no encontrada");
               return false;
           }  
       }
       V elVehiculo = lista.get(pos);
      
       if(!elVehiculo.estaPagado()){
           return false;
       }    
      lista.remove(pos);

       return true;
    }
    
    
     @Override
    public String toString() {
            
         StringBuilder sb = new StringBuilder(super.toString());
         sb.append("\n");
         sb.append("Num Plazas movilidad reducida: ").append(getNumPlazasR());
         sb.append(" Plazas disponibles movilidad reducida: ").append(getNumPlazasR()-plazasR.size());
         sb.append("\nVehículos en parking R:\n");
         for(Vehiculo v : plazasR){
             sb.append(v.toString()).append("\n");
         }
         sb.append("\n");
         sb.append("Num Plazas resto: ").append(getNumPlazasCT());
         sb.append(" Plazas disponibles resto: ").append(getNumPlazasCT()-plazas.size());
         sb.append("\nVehículos en parking R:\n");
         for(Vehiculo v : plazas){
             sb.append(v.toString()).append("\n");
         }

         return sb.toString();
    }

    public int getNumPlazasCT() {
        return numPlazasCT;
    }

    public int getNumPlazasR() {
        return numPlazasR;
    }

    @Override
    public V getVehiculo(P plaza) { // R-1 C-0
        String[] idenPartes = plaza.getIdentificador().split("-");
        int pos = Integer.parseInt(idenPartes[1]);
        if("R".equals(idenPartes[0])){
            return plazasR.get(pos);
        }else{
            return plazas.get(pos);
        }
    }

    @Override
    public P getPlaza(String matricula) { //sino esta nulo
        int pos = buscarMatricula(matricula); //buscar en plazas y -1 sino esta
        if(pos > -1 ){
            return (P) new PlazaAvanzado(pos, plazas.get(pos).getTipoVehiculo());
        }else{
            pos = buscarMatriculaR(matricula);
            if(pos > -1 ){
                return (P) new PlazaAvanzado(pos, TipoVehiculo.R);
            }            
        }
        return null;        
    }

    @Override
    public List<V> getVehiculos() {
        List<V> lstV = new ArrayList<>();
        lstV.addAll(plazas);
        lstV.addAll(plazasR);
        return lstV;
    }

    @Override
    public List<P> getPlazas() {
        List<P> lstPlaza = new ArrayList<>();
       
        
        for(int p=0; p < plazas.size();p++){
            lstPlaza.add( (P) new PlazaAvanzado(p, plazas.get(p).getTipoVehiculo()));
        }
        for(int p=0; p < plazasR.size();p++){
            lstPlaza.add( (P) new PlazaAvanzado(p, plazasR.get(p).getTipoVehiculo()));
        }
        return lstPlaza;
    }
    
    
    
    public static class PlazaAvanzado implements Plaza{
        private int pos;
        private TipoVehiculo tipo;

        public PlazaAvanzado(int pos, TipoVehiculo tipo) {
            this.pos = pos;
            this.tipo = tipo;
            
        }
                       
        @Override
        public String getIdentificador() { // R-1 C-0
            String strResult = "";
            if(tipo==TipoVehiculo.R){
                strResult="R";
            }else{
                strResult="C";
            }
            strResult += "-" + this.pos;
            return strResult;
        }

        @Override
        public TipoVehiculo getTipo() {
           return tipo;
        }
        
        @Override
        public String toString() {
            return "Plaza Avanzado: " + getIdentificador(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }
    }
}
