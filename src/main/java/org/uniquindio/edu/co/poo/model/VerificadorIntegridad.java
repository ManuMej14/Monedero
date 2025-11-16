package org.uniquindio.edu.co.poo.model;

import java.util.List;

public class VerificadorIntegridad {
    // Verifica recursivamente que la suma de efectos de transacciones coincide con el saldo
    public static boolean verificarMonedero(Monedero m) {
        double calculado = sumarEfectosRecursivo(m.getHistorialTransacciones(), 0);
        return Math.abs(calculado - m.getSaldo()) < 1e-6;
    }

    // función recursiva que calcula el efecto neto de la lista de transacciones
    private static double sumarEfectosRecursivo(List<Transaccion> transacciones, int indice) {
        if (indice >= transacciones.size()) return 0.0;
        Transaccion t = transacciones.get(indice);
        double resto = sumarEfectosRecursivo(transacciones, indice + 1);
        double efecto = 0.0;
        switch (t.tipo) {
            case DEPOSITO:
                efecto = t.getMonto();
                break;
            case RETIRO:
                efecto = -t.getMonto();
                break;
            case TRANSFERENCIA:
                // para transferencias no podemos decidir sin saber si la lista pertenece al monedero origen o destino.
                // aquí asumimos que la verificación en profundidad requiere comparar ambos monederos y las transacciones entre ellos.
                efecto = 0.0;
                break;
            default:
                efecto = 0.0;
        }
        return efecto + resto;
    }
}