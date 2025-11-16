package org.uniquindio.edu.co.poo.app;

import org.uniquindio.edu.co.poo.model.*;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Notificable notificador = new Notificador();
        Planificador planificador = new Planificador();

        // Demo inicial con dos clientes
        Cliente ana = new Cliente("CL1", "Ana", "ana@ejemplo.com");
        Cliente luis = new Cliente("CL2", "Luis", "luis@ejemplo.com");
        Monedero anaAhorros = ana.crearMonedero("Ahorros");
        Monedero anaGastos = ana.crearMonedero("Gastos");
        Monedero luisPrincipal = luis.crearMonedero("Principal");

        System.out.println("=== Sistema Monedero Virtual (Demo) ===");

        boolean ejecutar = true;
        while (ejecutar) {
            System.out.println("\nSeleccione acción:");
            System.out.println("1=Depositar 2=Retirar 3=Transferir 4=Consultar saldo 5=Historial 6=Agendar demo 7=Puntos 8=Verificar integridad 9=Salir");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    System.out.print("Monto a depositar en Ahorros de Ana: ");
                    double md = Double.parseDouble(sc.nextLine());
                    Deposito dep = new Deposito(anaAhorros, md);
                    if (dep.ejecutar()) System.out.println("Depósito realizado.");
                    else System.out.println("Error en depósito.");
                    break;
                case "2":
                    System.out.print("Monto a retirar de Gastos de Ana: ");
                    double mr = Double.parseDouble(sc.nextLine());
                    Retiro ret = new Retiro(anaGastos, mr);
                    if (ret.ejecutar()) System.out.println("Retiro realizado.");
                    else System.out.println("Saldo insuficiente.");
                    break;
                case "3":
                    System.out.print("Monto a transferir desde Ahorros Ana hacia Principal Luis: ");
                    double mt = Double.parseDouble(sc.nextLine());
                    // calcular comisión según rango (ejemplo)
                    double comision = 1.0;
                    if (ana.getRango() == Rango.PLATA) comision = 0.9;
                    else if (ana.getRango() == Rango.ORO) comision = 0.7;
                    else if (ana.getRango() == Rango.PLATINO) comision = 0.5;
                    Transferencia tr = new Transferencia(anaAhorros, luisPrincipal, mt, comision);
                    if (tr.ejecutar()) System.out.println("Transferencia realizada.");
                    else System.out.println("Fallo transferencia (saldo insuficiente).");
                    break;
                case "4":
                    System.out.println("Saldo Ana Ahorros: " + anaAhorros.getSaldo());
                    System.out.println("Saldo Ana Gastos: " + anaGastos.getSaldo());
                    System.out.println("Saldo Luis Principal: " + luisPrincipal.getSaldo());
                    break;
                case "5":
                    System.out.println("Historial Ana Ahorros:");
                    anaAhorros.imprimirExtracto();
                    break;
                case "6":
                    System.out.println("Agendando depósito diario de 10 unidades en Ahorros de Ana (demo para mañana)...");
                    Deposito prot = new Deposito(anaAhorros, 10);
                    TransaccionProgramada tp = new TransaccionProgramada(prot, LocalDateTime.now().plusDays(1), TransaccionProgramada.Recurrencia.DIARIA);
                    planificador.agendar(tp);
                    System.out.println("Agendado. Procesando hasta mañana (demo)...");
                    planificador.procesarHasta(LocalDateTime.now().plusDays(1), notificador);
                    break;
                case "7":
                    System.out.println(ana);
                    System.out.println("Historial de puntos de Ana:");
                    for (RegistroPuntos rp : ana.getHistorialPuntos()) System.out.println(rp);
                    break;
                case "8":
                    System.out.println("Verificando integridad del monedero Ahorros de Ana: " + VerificadorIntegridad.verificarMonedero(anaAhorros));
                    break;
                case "9":
                    ejecutar = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }

        sc.close();
        System.out.println("Saliendo del sistema...");
    }
}