package org.uniquindio.edu.co.poo.model;
public class Deposito extends Transaccion {
    final Monedero destino;

    public Deposito(Monedero destino, double monto) {
        super(monto, TipoTransaccion.DEPOSITO);
        this.destino = destino;
    }

    @Override
    public boolean ejecutar() {
        if (ejecutada) return false;
        destino.aplicarSaldo(monto);
        destino.registrarTransaccion(this);
        // 1 punto por cada 100 unidades depositadas
        puntosGenerados = (int)(monto / 100);
        destino.getPropietario().agregarPuntos(puntosGenerados, "Depósito de " + monto + " en " + destino.getNombre());
        ejecutada = true;
        return true;
    }

    @Override
    public boolean revertir() {
        if (!ejecutada) return false;
        if (destino.getSaldo() >= monto) {
            destino.aplicarSaldo(-monto);
            destino.getPropietario().quitarPuntos(puntosGenerados, "Reversión depósito " + monto);
            ejecutada = false;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + " -> " + destino.getNombre();
    }
}