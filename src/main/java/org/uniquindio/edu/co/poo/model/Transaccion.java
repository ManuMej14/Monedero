package org.uniquindio.edu.co.poo.model;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Transaccion {
    protected final String id;
    protected final LocalDateTime fecha;
    protected final double monto;
    protected final TipoTransaccion tipo;
    protected boolean ejecutada;
    protected int puntosGenerados;

    public Transaccion(double monto, TipoTransaccion tipo) {
        this.id = UUID.randomUUID().toString();
        this.fecha = LocalDateTime.now();
        this.monto = monto;
        this.tipo = tipo;
        this.ejecutada = false;
        this.puntosGenerados = 0;
    }

    // Ejecuta la transacción. Devuelve true si fue exitosa.
    public abstract boolean ejecutar();
    // Revierte la transacción. Devuelve true si fue revertida correctamente.
    public abstract boolean revertir();

    public double getMonto() { return monto; }
    public String getId() { return id; }
    public LocalDateTime getFecha() { return fecha; }
    public int getPuntosGenerados() { return puntosGenerados; }

    @Override
    public String toString() {
        return "[" + fecha + "] " + tipo + " - " + monto + " (pts: " + puntosGenerados + ") id:" + id;
    }
}