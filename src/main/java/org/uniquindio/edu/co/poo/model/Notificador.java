package org.uniquindio.edu.co.poo.model;

public class Notificador implements Notificable {
    @Override
    public void enviarWhatsApp(String destino, String mensaje) {
        System.out.println("[WhatsApp] -> " + destino + ": " + mensaje);
    }

    @Override
    public void enviarEmail(String destino, String asunto, String cuerpo) {
        System.out.println("[Email] -> " + destino + " | " + asunto + " : " + cuerpo);
    }

    @Override
    public void enviarSMS(String destino, String mensaje) {
        System.out.println("[SMS] -> " + destino + ": " + mensaje);
    }
}