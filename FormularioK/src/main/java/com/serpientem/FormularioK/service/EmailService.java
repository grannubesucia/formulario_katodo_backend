package com.serpientem.FormularioK.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.serpientem.FormularioK.model.Pedido;

@Service
public class EmailService {

        private final JavaMailSender mailSender;
        private static final String EMAIL_NEGOCIO = "katodociberjoyeria@gmail.com";

        public EmailService(JavaMailSender mailSender) {
                this.mailSender = mailSender;
        }

        
        public void enviarConfirmaciones(Pedido pedido) {
                enviarAlCliente(pedido);
                enviarAlNegocio(pedido);
        }

        private void enviarAlCliente(Pedido pedido) {
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setFrom(EMAIL_NEGOCIO);
                msg.setTo(pedido.getEmailCliente());
                msg.setSubject("✨ Confirmación de tu pedido — Katodo Ciberjoyería");
                msg.setText(
                                "¡Hola!\n\n" +
                                                "Hemos recibido tu pedido personalizado. Aquí tienes el resumen:\n\n" +
                                                "──────────────────────────\n" +
                                                "Código de seguimiento: " + pedido.getCodigoSeguimiento() + "\n" +
                                                "Tipo de pedido:    "
                                                + (pedido.getTipoPedido()
                                                                .equals("propio") ? "Para mí" : "Para otra persona")
                                                + "\n" +
                                                (pedido.getDescripcionPersona() != null
                                                                && !pedido.getDescripcionPersona().isEmpty()
                                                                                ? "Descripción persona: " + pedido
                                                                                                .getDescripcionPersona()
                                                                                                + "\n"
                                                                                : "")
                                                +
                                                "Accesorio:         " + pedido.getTipoAccesorio() +
                                                (pedido.getOtroAccesorio() != null
                                                                && !pedido.getOtroAccesorio().isEmpty()
                                                                                ? " (" + pedido.getOtroAccesorio() + ")"
                                                                                : "")
                                                + "\n" +
                                                "Presupuesto:       " + pedido.getPresupuesto() + "\n" +
                                                "Origen:            "
                                                + (pedido.getOrigen().equals("nuevo") ? "Desde cero"
                                                                : "Inspirado en diseño")
                                                +
                                                (pedido.getReferencia() != null && !pedido.getReferencia().isEmpty()
                                                                ? " — " + pedido.getReferencia()
                                                                : "")
                                                + "\n" +
                                                "Materiales:        " + formatearMateriales(pedido.getMateriales())
                                                + "\n" +
                                                "Config materiales: \n" + formatearConfig(pedido.getMaterialesConfig())
                                                + "\n" +
                                                "Descripción final: " + pedido.getDescripcionFinal() + "\n" +
                                                "──────────────────────────\n\n" +
                                                "Nos pondremos en contacto contigo pronto.\n\n" +
                                                "¡Gracias por confiar en nosotros!\n" +
                                                "— Katodo Ciberjoyería");
                mailSender.send(msg);
        }

        private void enviarAlNegocio(Pedido pedido) {
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setFrom(EMAIL_NEGOCIO);
                msg.setTo(EMAIL_NEGOCIO);
                msg.setSubject("🛒 Nuevo pedido recibido — ID #" + pedido.getId());
                msg.setText(
                                "Se ha recibido un nuevo pedido:\n\n" +
                                                "──────────────────────────\n" +
                                                "ID:                " + pedido.getId() + "\n" +
                                                "Email cliente:     " + pedido.getEmailCliente() + "\n" +
                                                "Tipo de pedido:    " + pedido.getTipoPedido() + "\n" +
                                                "Descripción pers.: " + pedido.getDescripcionPersona() + "\n" +
                                                "Accesorio:         " + pedido.getTipoAccesorio() +
                                                (pedido.getOtroAccesorio() != null
                                                                && !pedido.getOtroAccesorio().isEmpty()
                                                                                ? " (" + pedido.getOtroAccesorio() + ")"
                                                                                : "")
                                                + "\n" +
                                                "Presupuesto:       " + pedido.getPresupuesto() + "\n" +
                                                "Origen:            " + pedido.getOrigen() +
                                                (pedido.getReferencia() != null && !pedido.getReferencia().isEmpty()
                                                                ? " — " + pedido.getReferencia()
                                                                : "")
                                                + "\n" +
                                                "Materiales:        " + pedido.getMateriales() + "\n" +
                                                "Config materiales: " + pedido.getMaterialesConfig() + "\n" +
                                                "Descripción final: " + pedido.getDescripcionFinal() + "\n" +
                                                "──────────────────────────");
                mailSender.send(msg);
        }

        /*
         * Correo para notificar al cliente sobre cambios de estado del pedido.
         * Se llama desde PedidoService.actualizarEstado() cada vez que se actualiza el
         * estado de un pedido.
         */

        @Async
        public void enviarCambioEstado(Pedido pedido) {
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setFrom(EMAIL_NEGOCIO);
                msg.setTo(pedido.getEmailCliente());
                msg.setSubject("📦 Actualización de tu pedido — Katodo Ciberjoyería");
                msg.setText(
                                "¡Hola!\n\n" +
                                                "Tu pedido ha sido actualizado:\n\n" +
                                                "──────────────────────────\n" +
                                                "Pedido #" + pedido.getId() + "\n" +
                                                "Accesorio: " + pedido.getTipoAccesorio() + "\n" +
                                                "Nuevo estado: " + pedido.getEstado() + "\n" +
                                                "──────────────────────────\n\n" +
                                                "¡Gracias por confiar en nosotros!\n" +
                                                "— Katodo Ciberjoyería");
                mailSender.send(msg);
        }

        /* Pardeo de datos JSON */
        
        private String formatearMateriales(String materialesJson) {
                if (materialesJson == null || materialesJson.isEmpty())
                        return "Ninguno";
                return materialesJson
                                .replace("[", "").replace("]", "")
                                .replace("\"", "").replace(",", ", ");
        }

        private String formatearConfig(String configJson) {
                if (configJson == null || configJson.isEmpty())
                        return "";
                try {
                        configJson = configJson.replace("{", "").replace("}", "").replace("\"", "");
                        String[] partes = configJson.split(",");
                        StringBuilder sb = new StringBuilder();
                        for (String parte : partes) {
                                if (!parte.trim().isEmpty()) {
                                        sb.append("  · ").append(parte.trim()).append("\n");
                                }
                        }
                        return sb.toString();
                } catch (Exception e) {
                        return configJson;
                }
        }

}