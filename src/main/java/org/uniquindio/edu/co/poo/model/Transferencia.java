package org.uniquindio.edu.co.poo.model;

public class Transferencia extends Transaccion {
    private final Monedero desde;
    private final Monedero hacia;
    private final double comision; // comisión aplicada

    public Transferencia(Monedero desde, Monedero hacia, double monto, double comision) {
        super(monto, TipoTransaccion.TRANSFERENCIA);
        this.desde = desde;
        this.hacia = hacia;
        this.comision = comision;
    }

    @Override
    public boolean ejecutar() {
        if (ejecutada) return false;
        double total = monto + comision;
        if (desde.getSaldo() >= total) {
            desde.aplicarSaldo(-total);
            hacia.aplicarSaldo(monto);
            desde.registrarTransaccion(this);
            hacia.registrarTransaccion(this);
            // 3 puntos por cada 100 unidades transferidas
            puntosGenerados = (int)(monto / 100) * 3;
            desde.getPropietario().agregarPuntos(puntosGenerados, "Transferencia de " + monto + " a " + hacia.getPropietario().getId());
            ejecutada = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean revertir() {
        if (!ejecutada) return false;
        if (hacia.getSaldo() >= monto) {
            hacia.aplicarSaldo(-monto);
            desde.aplicarSaldo(monto + comision);
            desde.getPropietario().quitarPuntos(puntosGenerados, "Reversión transferencia " + monto);
            ejecutada = false;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + " " + desde.getNombre() + " -> " + hacia.getNombre() + " (comisión=" + comision + ")";
    }

    // getters para notificaciones o uso externo
    public Monedero getDesde() { return desde; }
    public Monedero getHacia() { return hacia; }
    public double getComision() { return comision; }
}