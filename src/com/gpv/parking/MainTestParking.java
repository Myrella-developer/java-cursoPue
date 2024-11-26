
package com.gpv.parking;

import com.gpv.parking.domain.TipoEmisiones;
import com.gpv.parking.domain.Vehiculo;
import com.gpv.parking.domain.vehiculos.Coche;
import com.gpv.parking.domain.vehiculos.Moto;
import com.gpv.parking.domain.vehiculos.TodoTerreno;
import java.time.LocalDateTime;

public class MainTestParking {

   
    public static void main(String[] args) {
        Vehiculo moto1 = new Moto("12345", TipoEmisiones.B);
        System.out.println(moto1);
        moto1.setHoraEntrada(LocalDateTime.now());
        System.out.println(moto1);
        moto1.setPagado(true);
        System.out.println(moto1);
        
        System.out.println("-*-*-* cOCHES -*-*-*");
        Vehiculo coche1 = new Coche("1234-TFG", TipoEmisiones.C);
        System.out.println(coche1);
        coche1.setHoraEntrada(LocalDateTime.now());
        coche1.setPagado(true);
        System.out.println(coche1);
        
        Vehiculo cocheR1 = new Coche("3456-GTF", TipoEmisiones.C, true);
        System.out.println(cocheR1);
        System.out.println(cocheR1.getTipoVehiculo());
        
        System.out.println("-*-*- TodoTerreno -*-*-");
        Vehiculo todo1 = new TodoTerreno("6263-TFG", TipoEmisiones.A);
        System.out.println(todo1);
        todo1.setPagado(true);
        todo1.setHoraEntrada(LocalDateTime.now());
        System.out.println(todo1);
        
    }
    
}
