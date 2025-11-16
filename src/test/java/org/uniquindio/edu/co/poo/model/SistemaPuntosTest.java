package org.uniquindio.edu.co.poo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SistemaPuntosTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("1", "Edward", "mail@test.com");
    }

    @Test
    void testBeneficio_Reduccion10pct_Exitoso() {
        cliente.agregarPuntos(150, "Prueba");

        boolean aplicado = SistemaPuntos.aplicarBeneficio(
                cliente,
                SistemaPuntos.Beneficio.REDUCCION_10_PCT_COMISION_TRANSFERENCIAS
        );

        assertTrue(aplicado);
        assertEquals(50, cliente.getPuntos());  // 150 - 100
    }

    @Test
    void testBeneficio_Reduccion10pct_FallaPorFaltaDePuntos() {
        cliente.agregarPuntos(50, "Prueba");

        boolean aplicado = SistemaPuntos.aplicarBeneficio(
                cliente,
                SistemaPuntos.Beneficio.REDUCCION_10_PCT_COMISION_TRANSFERENCIAS
        );

        assertFalse(aplicado);
        assertEquals(50, cliente.getPuntos()); // No cambia
    }

    @Test
    void testBeneficio_UnMesSinCargos_Exitoso() {
        cliente.agregarPuntos(600, "Prueba");

        boolean aplicado = SistemaPuntos.aplicarBeneficio(
                cliente,
                SistemaPuntos.Beneficio.UN_MES_SIN_CARGOS_RETIROS
        );

        assertTrue(aplicado);
        assertEquals(100, cliente.getPuntos()); // 600 - 500
    }

    @Test
    void testBeneficio_UnMesSinCargos_FallaPorFaltaDePuntos() {
        cliente.agregarPuntos(300, "Prueba");

        boolean aplicado = SistemaPuntos.aplicarBeneficio(
                cliente,
                SistemaPuntos.Beneficio.UN_MES_SIN_CARGOS_RETIROS
        );

        assertFalse(aplicado);
        assertEquals(300, cliente.getPuntos());
    }

    @Test
    void testBeneficio_Bono50_ExitosoConMonedero() {
        cliente.agregarPuntos(1500, "Prueba");
        Monedero m = cliente.crearMonedero("Principal");

        boolean aplicado = SistemaPuntos.aplicarBeneficio(
                cliente,
                SistemaPuntos.Beneficio.BONO_50_UNIDADES
        );

        assertTrue(aplicado);

        // Se deben restar 1000 puntos
        assertEquals(500, cliente.getPuntos());

        // El monedero debe recibir 50 unidades
        assertEquals(50.0, m.getSaldo());
    }

    @Test
    void testBeneficio_Bono50_ExitosoSinMonederos_NoExplota() {
        cliente.agregarPuntos(1500, "Prueba");

        boolean aplicado = SistemaPuntos.aplicarBeneficio(
                cliente,
                SistemaPuntos.Beneficio.BONO_50_UNIDADES
        );

        assertTrue(aplicado);

        // Puntos restados correctamente
        assertEquals(500, cliente.getPuntos());
    }

    @Test
    void testBeneficio_Bono50_FallaPorFaltaDePuntos() {
        cliente.agregarPuntos(500, "Prueba");

        boolean aplicado = SistemaPuntos.aplicarBeneficio(
                cliente,
                SistemaPuntos.Beneficio.BONO_50_UNIDADES
        );

        assertFalse(aplicado);

        // Puntos se mantienen igual
        assertEquals(500, cliente.getPuntos());
    }

    @Test
    void testHistorialActualizadoTrasBeneficio() {
        cliente.agregarPuntos(1200, "Prueba");
        cliente.crearMonedero("Principal");

        SistemaPuntos.aplicarBeneficio(
                cliente, SistemaPuntos.Beneficio.BONO_50_UNIDADES
        );

        assertFalse(cliente.getHistorialPuntos().isEmpty());
        assertTrue(cliente.getHistorialPuntos().get(1).getMotivo().contains("Canje por"));
    }
}