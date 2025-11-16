package org.uniquindio.edu.co.poo.model;

import java.time.LocalDateTime;

public class TransaccionProgramada {
    private final Transaccion prototipo;
    private LocalDateTime ejecutarEn;
    private final Recurrencia recurrencia;

    public enum Recurrencia { NINGUNA, DIARIA, SEMANAL, MENSUAL }

    public TransaccionProgramada(Transaccion prototipo, LocalDateTime ejecutarEn, Recurrencia recurrencia) {
        this.prototipo = prototipo;
        this.ejecutarEn = ejecutarEn;
        this.recurrencia = recurrencia;
    }

    public Transaccion getPrototipo() { return prototipo; }
    public LocalDateTime getEjecutarEn() { return ejecutarEn; }
    public Recurrencia getRecurrencia() { return recurrencia; }

    public void avanzarSiguiente() {
        switch (recurrencia) {
            case DIARIA:
                ejecutarEn = ejecutarEn.plusDays(1);
                break;
            case SEMANAL:
                ejecutarEn = ejecutarEn.plusWeeks(1);
                break;
            case MENSUAL:
                ejecutarEn = ejecutarEn.plusMonths(1);
                break;
            case NINGUNA:
            default:
                break;
        }
    }

    @Override
    public String toString() {
        return "Programada: " + prototipo + " en " + ejecutarEn + " recur=" + recurrencia;
    }
}
