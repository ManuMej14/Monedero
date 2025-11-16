package org.uniquindio.edu.co.poo.model;

import java.time.LocalDateTime;
import java.util.*;

public class Planificador {
    private final List<TransaccionProgramada> programadas;

    public Planificador() {
        this.programadas = new ArrayList<>();
    }

    public void agendar(TransaccionProgramada tp) {
        programadas.add(tp);
    }

    // Procesa todas las transacciones cuya fecha <= hasta. Usa merge sort para ordenar por fecha.
    public void procesarHasta(LocalDateTime hasta, Notificable notificador) {
        if (programadas.isEmpty()) return;

        List<TransaccionProgramada> listaOrdenada = new ArrayList<>(programadas);
        mergeSort(listaOrdenada, 0, listaOrdenada.size() - 1);

        // iterar y ejecutar las que correspondan
        Iterator<TransaccionProgramada> it = listaOrdenada.iterator();
        while (it.hasNext()) {
            TransaccionProgramada tp = it.next();
            if (!tp.getEjecutarEn().isAfter(hasta)) {
                Transaccion t = tp.getPrototipo();
                boolean ok = t.ejecutar();
                if (ok) {
                    // notificar al propietario(s)
                    if (t instanceof Transferencia) {
                        Transferencia tr = (Transferencia) t;
                        notificador.enviarEmail(tr.getDesde().getPropietario().getContacto(), "Transacción programada ejecutada", t.toString());
                    } else if (t instanceof Deposito) {
                        Deposito d = (Deposito) t;
                        notificador.enviarEmail(d.destino.getPropietario().getContacto(), "Depósito programado ejecutado", t.toString());
                    } else {
                        // fallback: notificar al dueño si aplica
                        notificador.enviarSMS("usuario", "Transacción programada ejecutada: " + t.toString());
                    }
                } else {
                    notificador.enviarSMS("usuario", "Falló transacción programada: " + t.toString());
                }

                // manejar recurrencia
                if (tp.getRecurrencia() == TransaccionProgramada.Recurrencia.NINGUNA) {
                    // eliminar del original
                    programadas.remove(tp);
                } else {
                    tp.avanzarSiguiente();
                }
            } else {
                break; // por estar ordenado, los siguientes también estarán en el futuro
            }
        }
    }

    // merge sort por fecha ejecutarEn
    private void mergeSort(List<TransaccionProgramada> lista, int l, int r) {
        if (l >= r) return;
        int m = (l + r) / 2;
        mergeSort(lista, l, m);
        mergeSort(lista, m + 1, r);
        merge(lista, l, m, r);
    }

    private void merge(List<TransaccionProgramada> lista, int l, int m, int r) {
        List<TransaccionProgramada> izq = new ArrayList<>(lista.subList(l, m + 1));
        List<TransaccionProgramada> der = new ArrayList<>(lista.subList(m + 1, r + 1));
        int i = 0, j = 0, k = l;
        while (i < izq.size() && j < der.size()) {
            if (!izq.get(i).getEjecutarEn().isAfter(der.get(j).getEjecutarEn())) {
                lista.set(k++, izq.get(i++));
            } else {
                lista.set(k++, der.get(j++));
            }
        }
        while (i < izq.size()) lista.set(k++, izq.get(i++));
        while (j < der.size()) lista.set(k++, der.get(j++));
    }

    public List<TransaccionProgramada> listarTodas() {
        return Collections.unmodifiableList(programadas);
    }
}
