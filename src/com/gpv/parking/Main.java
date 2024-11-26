package com.gpv.parking;

import com.gpv.parking.domain.Parking;
import com.gpv.parking.domain.ParkingAbstract;
import com.gpv.parking.domain.Plaza;
import com.gpv.parking.domain.TipoEmisiones;
import com.gpv.parking.domain.TipoVehiculo;
import com.gpv.parking.domain.Vehiculo;
import com.gpv.parking.domain.parkings.ParkingAvanzado;
//import com.gpv.parking.domain.parkings.ParkingAvanzado;
import com.gpv.parking.domain.parkings.ParkingBasico;
import com.gpv.parking.domain.parkings.ParkingMap;
import com.gpv.parking.domain.vehiculos.Coche;
import com.gpv.parking.domain.vehiculos.Moto;
import com.gpv.parking.domain.vehiculos.TodoTerreno;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Consumer;

public class Main {

    static Scanner scanner = null;

    public static void main(String[] args) {
        //Parking<Vehiculo,Plaza>  p = new ParkingBasico(5);
        //Parking<Vehiculo,Plaza> p =
        //         new ParkingAvanzado<>(5, 2);

        Parking<Vehiculo, Plaza> p
                = new ParkingMap(5, 4, 2, 2);
        p.aparcar(new Coche("1111-AA", TipoEmisiones.CERO));
        System.out.println("App gestión parking basico.");
        scanner = new Scanner(System.in);
        int opc;
        do {
            printMenu();
            opc = scanner.nextInt();
            switch (opc) {
                case 1:
                    System.out.println("\n" + p.toString());
                    break;
                case 2:
                    Vehiculo vnew = crearVehiculo();
                    if (vnew == null) {
                        break;
                    }
                    boolean bAparcar = p.aparcar(vnew);
                    if (bAparcar) {
                        System.out.println("Vehiculo aparcado");
                    } else {
                        System.out.println("No aparcado  Plazas libres: "
                                + p.getPlazasDisponibles());
                    }
                    break;
                case 3:
                    System.out.println("Dime la matricula: ");
                    String matricula = scanner.next();
                    double importe = p.pagar(matricula);
                    System.out.println("Importe " + importe + " Matricula: " + matricula);
                    break;
                case 4:
                    System.out.println("Dime la matricula: ");
                    matricula = scanner.next();
                    boolean bRetirar = p.retirar(matricula);
                    if (bRetirar) {
                        System.out.println("Retirado " + matricula);
                    } else {
                        System.out.println("No Retirado " + matricula);
                    }
                    break;
                case 5:
                    verVehiculos(p);
                    break;
                case 6:
                    verPlazas(p);
                    break;
            }
        } while (opc != 9);

    }

    public static void printMenu() {
        System.out.println("Selecione la opcion para continuar:");
        System.out.println("1.- Ver Parking");
        System.out.println("2.- Aparcar vehiculo");
        System.out.println("3.- Pagar vehiculo");
        System.out.println("4.- Retirar vehiculo");
        System.out.println("5.- Ver vehiculos");
        System.out.println("6.- Ver plazas");
        System.out.println("9.- Salir");
    }

    public static Vehiculo crearVehiculo() {

        Vehiculo v = null;
        TipoVehiculo tipo = null;
        String tipoStr = null;
        do {
            System.out.println("Dime el Tipo de Vehiculo ( M, C, T , R )");
            tipoStr = scanner.next();
        } while (Arrays.asList(TipoVehiculo.values()).contains(tipoStr));
        tipo = TipoVehiculo.valueOf(tipoStr);

        System.out.println("Dime la matricula");
        String matricula = scanner.next();
        System.out.println("Dime la clasificaion de emisiones ( CERO, ECO, C, B, A )");
        String emisionesStr = scanner.next();
        TipoEmisiones emisiones = TipoEmisiones.valueOf(emisionesStr);
        switch (tipo) {
            case M:
                v = new Moto(matricula, emisiones);
                break;
            case C:
                v = new Coche(matricula, emisiones);
                break;
            case T:
                v = new TodoTerreno(matricula, emisiones);
                break;
            case R:
                v = new Coche(matricula, emisiones, true);
                break;
            default:
                System.out.println("no encontrado.");
        }
        return v;
    }

    public static void verVehiculos(Parking<Vehiculo, Plaza> elParking) {
        List<Vehiculo> vehiculos = elParking.getVehiculos();
        System.out.println("Vehiculos en el parking: " + vehiculos.size());
        Consumer<Vehiculo> cVehiculo
                = (v) -> System.out.println(v);
        vehiculos
                .forEach(cVehiculo);
    }

    public static void verPlazas(Parking<Vehiculo, Plaza> elParking) {
        List<Plaza> plazas = elParking.getPlazas();
        System.out.println("Plazas en el parking: " + plazas.size());
        Consumer<Plaza> cPlaza = System.out::println;
        plazas.forEach(cPlaza);

    }
}
