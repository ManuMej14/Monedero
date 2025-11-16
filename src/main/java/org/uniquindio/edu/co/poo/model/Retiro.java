package org.uniquindio.edu.co.poo.model;

public class Retiro extends Transaccion {
    private final Monedero origen;

    public Retiro(Monedero origen, double monto) {
        super(monto, TipoTransaccion.RETIRO);
        this.origen = origen;
    }

    @Override
    public boolean ejecutar() {
        if (ejecutada) return false;
        if (origen.getSaldo() >= monto) {
            origen.aplicarSaldo(-monto);
            origen.registrarTransaccion(this);
            // 2 puntos por cada 100 unidades retiradas
            puntosGenerados = (int)(monto / 100) * 2;
            origen.getPropietario().agregarPuntos(puntosGenerados, "Retiro de " + monto + " desde " + origen.getNombre());
            ejecutada = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean revertir() {
        if (!ejecutada) return false;
        origen.aplicarSaldo(monto);
        origen.getPropietario().quitarPuntos(puntosGenerados, "Reversión retiro " + monto);
        ejecutada = false;
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " <- " + origen.getNombre();
    }
}