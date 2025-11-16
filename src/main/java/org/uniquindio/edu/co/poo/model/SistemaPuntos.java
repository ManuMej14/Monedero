package org.uniquindio.edu.co.poo.model;

public class SistemaPuntos {
    public enum Beneficio {
        REDUCCION_10_PCT_COMISION_TRANSFERENCIAS, // 100 pts
        UN_MES_SIN_CARGOS_RETIROS, // 500 pts
        BONO_50_UNIDADES // 1000 pts
    }

    public static boolean aplicarBeneficio(Cliente cliente, Beneficio b) {
        switch (b) {
            case REDUCCION_10_PCT_COMISION_TRANSFERENCIAS:
                if (cliente.getPuntos() >= 100) {
                    cliente.canjearPuntos(100, b);
                    return true;
                }
                return false;
            case UN_MES_SIN_CARGOS_RETIROS:
                if (cliente.getPuntos() >= 500) {
                    cliente.canjearPuntos(500, b);
                    return true;
                }
                return false;
            case BONO_50_UNIDADES:
                if (cliente.getPuntos() >= 1000) {
                    cliente.canjearPuntos(1000, b);
                    if (!cliente.getMonederos().isEmpty()) {
                        cliente.getMonederos().get(0).aplicarSaldo(50.0);
                    }
                    return true;
                }
                return false;
            default:
                return false;
        }
    }
}