package org.uniquindio.edu.co.poo.model;

import java.util.*;

public class Cliente {
    private final String id;
    private final String nombre;
    private String contacto; // email o teléfono
    private final List<Monedero> monederos;
    private final List<RegistroPuntos> historialPuntos;
    private int puntos;
    private Rango rango;

    public Cliente(String id, String nombre, String contacto) {
        this.id = id;
        this.nombre = nombre;
        this.contacto = contacto;
        this.monederos = new ArrayList<>();
        this.historialPuntos = new ArrayList<>();
        this.puntos = 0;
        this.rango = Rango.BRONCE;
    }

    public Monedero crearMonedero(String nombreMonedero) {
        Monedero m = new Monedero(UUID.randomUUID().toString(), nombreMonedero, this);
        monederos.add(m);
        return m;
    }

    public List<Monedero> getMonederos() { return Collections.unmodifiableList(monederos); }
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public int getPuntos() { return puntos; }
    public Rango getRango() { return rango; }
    public List<RegistroPuntos> getHistorialPuntos() { return Collections.unmodifiableList(historialPuntos); }

    public void agregarPuntos(int p, String motivo) {
        if (p == 0) return;
        puntos += p;
        historialPuntos.add(new RegistroPuntos(p, motivo));
        recalcularRango();
    }

    public void quitarPuntos(int p, String motivo) {
        if (p == 0) return;
        puntos = Math.max(0, puntos - p);
        historialPuntos.add(new RegistroPuntos(-p, "REVERSIÓN: " + motivo));
        recalcularRango();
    }

    private void recalcularRango() {
        if (puntos <= 500) rango = Rango.BRONCE;
        else if (puntos <= 1000) rango = Rango.PLATA;
        else if (puntos <= 5000) rango = Rango.ORO;
        else rango = Rango.PLATINO;
    }

    public void canjearPuntos(int p, SistemaPuntos.Beneficio beneficio) {
        if (p <= puntos) {
            puntos -= p;
            historialPuntos.add(new RegistroPuntos(-p, "Canje por " + beneficio));
            recalcularRango();
        } else {
            throw new IllegalArgumentException("No hay suficientes puntos para canjear");
        }
    }

    @Override
    public String toString() {
        return "Cliente{" + id + " - " + nombre + " - Puntos: " + puntos + " - Rango: " + rango + "}";
    }
}