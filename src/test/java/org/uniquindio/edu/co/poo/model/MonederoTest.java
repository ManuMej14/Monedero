package org.uniquindio.edu.co.poo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MonederoTest {

    private Monedero monedero;
    private Cliente cliente;

    // Subclase para poder acceder a métodos protected
    static class MonederoPrueba extends Monedero {
        public MonederoPrueba(String id, String nombre, Cliente propietario) {
            super(id, nombre, propietario);
        }

        public void aplicar(double valor) {
            super.aplicarSaldo(valor);
        }

        public void registrar(Transaccion t) {
            super.registrarTransaccion(t);
        }
    }

    @BeforeEach
    void setUp() {
        cliente = new Cliente("1", "Edward", "mail@test.com");
        monedero = new MonederoPrueba("M1", "Principal", cliente);
    }

    @Test
    void testAplicarSaldo() {
        MonederoPrueba mp = (MonederoPrueba) monedero;
        mp.aplicar(100.0);

        assertEquals(100.0, monedero.getSaldo());
    }


    @Test
    void testToString() {
        String s = monedero.toString();
        assertTrue(s.contains("Monedero{"));
        assertTrue(s.contains("Principal"));
        assertTrue(s.contains("M1"));
    }
}