package org.uniquindio.edu.co.poo.model;
import java.util.*;

public class Monedero {
    private final String id;
    private final String nombre;
    private double saldo;
    private final Cliente propietario;
    private final List<Transaccion> historialTransacciones;

    public Monedero(String id, String nombre, Cliente propietario) {
        this.id = id;
        this.nombre = nombre;
        this.propietario = propietario;
        this.saldo = 0.0;
        this.historialTransacciones = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getSaldo() { return saldo; }
    public Cliente getPropietario() { return propietario; }
    public List<Transaccion> getHistorialTransacciones() { return Collections.unmodifiableList(historialTransacciones); }

    protected void aplicarSaldo(double cantidad) {
        this.saldo += cantidad;
    }

    protected void registrarTransaccion(Transaccion t) {
        historialTransacciones.add(t);
    }

    public void imprimirExtracto() {
        System.out.println("Monedero: " + nombre + " | Saldo: " + saldo);
        for (Transaccion t : historialTransacciones) {
            System.out.println(t);
        }
    }

    @Override
    public String toString() {
        return "Monedero{" + nombre + " - " + id + " - Saldo=" + saldo + "}";
    }

}
