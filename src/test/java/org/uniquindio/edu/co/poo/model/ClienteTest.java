package org.uniquindio.edu.co.poo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("1", "Edward", "email@test.com");
    }

    @Test
    void testCrearMonedero() {
        Monedero m = cliente.crearMonedero("Ahorros");

        assertNotNull(m);
        assertEquals(1, cliente.getMonederos().size());
        assertEquals("Ahorros", m.getNombre());
        assertEquals(cliente, m.getPropietario());
    }

    @Test
    void testAgregarPuntos() {
        cliente.agregarPuntos(300, "Compra");

        assertEquals(300, cliente.getPuntos());
        assertEquals(1, cliente.getHistorialPuntos().size());
        assertEquals(Rango.BRONCE, cliente.getRango());
    }

    @Test
    void testAgregarPuntosCambiaRango() {
        cliente.agregarPuntos(600, "Compra grande");

        assertEquals(600, cliente.getPuntos());
        assertEquals(Rango.PLATA, cliente.getRango());
    }

    @Test
    void testQuitarPuntos() {
        cliente.agregarPuntos(500, "Compra");
        cliente.quitarPuntos(200, "Devolución");

        assertEquals(300, cliente.getPuntos());
        assertEquals(2, cliente.getHistorialPuntos().size());
        assertEquals("REVERSIÓN: Devolución",
                cliente.getHistorialPuntos().get(1).getMotivo());
    }

    @Test
    void testQuitarPuntosNoBajaDeCero() {
        cliente.quitarPuntos(300, "Error");
        assertEquals(0, cliente.getPuntos());
    }

    @Test
    void testHistorialInmutable() {
        cliente.agregarPuntos(200, "Compra");
        List<RegistroPuntos> historial = cliente.getHistorialPuntos();

        assertThrows(UnsupportedOperationException.class, () -> {
            historial.clear();
        });
    }
}