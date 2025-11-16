package org.uniquindio.edu.co.poo.model;

public interface Notificable {
    void enviarWhatsApp(String destino, String mensaje);
    void enviarEmail(String destino, String asunto, String cuerpo);
    void enviarSMS(String destino, String mensaje);
}
