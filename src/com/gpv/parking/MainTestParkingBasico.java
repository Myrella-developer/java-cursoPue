
package com.gpv.parking;

import com.gpv.parking.domain.Parking;
import com.gpv.parking.domain.ParkingAbstract;
import com.gpv.parking.domain.Plaza;
import com.gpv.parking.domain.TipoEmisiones;
import com.gpv.parking.domain.Vehiculo;
import com.gpv.parking.domain.parkings.ParkingBasico;
import com.gpv.parking.domain.vehiculos.Coche;
import com.gpv.parking.domain.vehiculos.Moto;
import com.gpv.parking.domain.vehiculos.TodoTerreno;
import java.time.LocalDateTime;




public class MainTestParkingBasico {

    
    public static void main(String[] args) {
        
        Parking<Vehiculo,Plaza> p1 = new ParkingBasico(5);
        System.out.println("\n"+ p1);     
        Vehiculo moto1 = new Moto("12345", TipoEmisiones.B);
        p1.aparcar(moto1);
        moto1.setHoraEntrada(
                LocalDateTime.now()//.minusDays(1)
                        .minusHours(0)
                        .minusMinutes(20));
       System.out.println("\n"+ p1);
       double importe =  p1.pagar("12345");
        System.out.println("A pagar " + importe);
        System.out.println("\n"+ p1);
       
        Vehiculo coche1 = new Coche("1234-TFG", TipoEmisiones.C);
        p1.aparcar(coche1);
        Vehiculo cocheR1 = new Coche("3456-GTF", TipoEmisiones.B, true);
        p1.aparcar(cocheR1);
        System.out.println("\n"+ p1);
        
        boolean bSacarmoto = p1.retirar("12345");
        System.out.println("Sacar Moto " + bSacarmoto);
        System.out.println("\n"+ p1);
        
        Vehiculo todo1 = new TodoTerreno("6263-TFG", TipoEmisiones.A);
        p1.aparcar(todo1);
        System.out.println("\n"+ p1);
//        Vehiculo coche2 = new Coche("3456-TFG", '0');
//        boolean ok = p1.aparcar(coche2);
//        System.out.println("Coche 2 aparcado " + ok);
//        System.out.println("\n"+ p1);
//        
//        Vehiculo moto2  = new Moto("SEDR-8", 'B');
//        ok = p1.aparcar(moto2);
//        System.out.println("Moto 2 aparcado " + ok);
//        System.out.println("\n"+ p1);
        System.out.println("\n\n nuevps metodos");
        Vehiculo v1 = p1.getVehiculo(new ParkingBasico.PlazaBasico(2));
        System.out.println("v1 " + v1);
        Plaza pl1 = p1.getPlaza("3456-GTF");
        System.out.println("pl1 "+  pl1);
        System.out.println("LIST VEHICULOS");
        p1.getVehiculos().forEach(System.out::println);
        System.out.println("LIST PLAZAS");
        p1.getPlazas().forEach(p-> System.out.println(p));
    }
    
}
