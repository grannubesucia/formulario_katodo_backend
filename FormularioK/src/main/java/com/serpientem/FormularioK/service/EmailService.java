package com.serpientem.FormularioK.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.serpientem.FormularioK.model.Pedido;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

@Service
public class EmailService {

        @Value("${resend.api.key}")
        private String resendApiKey;

        private static final String EMAIL_NEGOCIO = "katodociberjoyeria@gmail.com";
        private static final String FROM = "Kátodo Ciberjoyería <onboarding@resend.dev>";

        @Async
        public void enviarConfirmaciones(Pedido pedido) {
                enviarAlCliente(pedido);
                enviarAlNegocio(pedido);
        }

        private void enviarAlCliente(Pedido pedido) {
                String texto = "Hola!\\n\\n" +
                                "Hemos recibido tu pedido personalizado. Aqui tienes el resumen:\\n\\n" +
                                "--------------------------\\n" +
                                "Codigo de seguimiento: " + pedido.getCodigoSeguimiento() + "\\n" +
                                "Tipo de pedido: "
                                + (pedido.getTipoPedido().equals("propio") ? "Para mi" : "Para otra persona") + "\\n" +
                                (pedido.getDescripcionPersona() != null && !pedido.getDescripcionPersona().isEmpty()
                                                ? "Descripcion persona: " + pedido.getDescripcionPersona() + "\\n"
                                                : "")
                                +
                                "Accesorio: " + pedido.getTipoAccesorio() +
                                (pedido.getOtroAccesorio() != null && !pedido.getOtroAccesorio().isEmpty()
                                                ? " (" + pedido.getOtroAccesorio() + ")"
                                                : "")
                                + "\\n" +
                                "Presupuesto: " + pedido.getPresupuesto() + "\\n" +
                                "Origen: " + (pedido.getOrigen().equals("nuevo") ? "Desde cero" : "Inspirado en diseno")
                                +
                                (pedido.getReferencia() != null && !pedido.getReferencia().isEmpty()
                                                ? " - " + pedido.getReferencia()
                                                : "")
                                + "\\n" +
                                "Materiales: " + formatearMateriales(pedido.getMateriales()) + "\\n" +
                                "Config materiales: " + formatearConfig(pedido.getMaterialesConfig()) + "\\n" +
                                "Descripcion final: " + pedido.getDescripcionFinal() + "\\n" +
                                "--------------------------\\n\\n" +
                                "Nos pondremos en contacto contigo pronto.\\n\\n" +
                                "Gracias por confiar en nosotros!\\n" +
                                "- Katodo Ciberjoyeria";

                enviar("marcoeu2001@gmail.com", "Confirmacion de tu pedido - Katodo Ciberjoyeria", texto);
                // enviar(pedido.getEmailCliente(), "Confirmacion de tu pedido - Katodo Ciberjoyeria", texto);
        }

        private void enviarAlNegocio(Pedido pedido) {
                String texto = "Se ha recibido un nuevo pedido:\\n\\n" +
                                "--------------------------\\n" +
                                "ID: " + pedido.getId() + "\\n" +
                                "Email cliente: " + pedido.getEmailCliente() + "\\n" +
                                "Tipo de pedido: " + pedido.getTipoPedido() + "\\n" +
                                "Descripcion pers.: " + pedido.getDescripcionPersona() + "\\n" +
                                "Accesorio: " + pedido.getTipoAccesorio() +
                                (pedido.getOtroAccesorio() != null && !pedido.getOtroAccesorio().isEmpty()
                                                ? " (" + pedido.getOtroAccesorio() + ")"
                                                : "")
                                + "\\n" +
                                "Presupuesto: " + pedido.getPresupuesto() + "\\n" +
                                "Origen: " + pedido.getOrigen() +
                                (pedido.getReferencia() != null && !pedido.getReferencia().isEmpty()
                                                ? " - " + pedido.getReferencia()
                                                : "")
                                + "\\n" +
                                "Materiales: " + pedido.getMateriales() + "\\n" +
                                "Config materiales: " + pedido.getMaterialesConfig() + "\\n" +
                                "Descripcion final: " + pedido.getDescripcionFinal() + "\\n" +
                                "--------------------------";

                enviar("marcoeu2001@gmail.com", "Nuevo pedido recibido - ID #" + pedido.getId(), texto);

                // enviar(EMAIL_NEGOCIO, "Nuevo pedido recibido - ID #" + pedido.getId(), texto);
        }

        /*
         * Correo para notificar al cliente sobre cambios de estado del pedido.
         * Se llama desde PedidoService.actualizarEstado() cada vez que se actualiza el
         * estado de un pedido.
         */

        @Async
        public void enviarCambioEstado(Pedido pedido) {
                String texto = "Hola!\\n\\n" +
                                "Tu pedido ha sido actualizado:\\n\\n" +
                                "--------------------------\\n" +
                                "Pedido #" + pedido.getId() + "\\n" +
                                "Accesorio: " + pedido.getTipoAccesorio() + "\\n" +
                                "Nuevo estado: " + pedido.getEstado() + "\\n" +
                                "--------------------------\\n\\n" +
                                "Gracias por confiar en nosotros!\\n" +
                                "- Katodo Ciberjoyeria";

                enviar(pedido.getEmailCliente(), "Actualizacion de tu pedido - Katodo Ciberjoyeria", texto);
        }

        private void enviar(String to, String subject, String texto) {
                try {
                        String body = "{\"from\":\"" + FROM + "\",\"to\":[\"" + to + "\"],\"subject\":\"" + subject
                                        + "\",\"text\":\"" + texto + "\"}";

                        HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create("https://api.resend.com/emails"))
                                        .header("Authorization", "Bearer " + resendApiKey)
                                        .header("Content-Type", "application/json")
                                        .POST(HttpRequest.BodyPublishers.ofString(body))
                                        .build();

                        HttpResponse<String> response = HttpClient.newHttpClient()
                                        .send(request, HttpResponse.BodyHandlers.ofString());

                        System.out.println("STATUS: " + response.statusCode());
                        System.out.println("BODY: " + response.body());

                } catch (Exception e) {
                        System.err.println("Error enviando email: " + e.getMessage());
                }
        }

        /* Pardeo de datos JSON */

        private String formatearMateriales(String materialesJson) {
                if (materialesJson == null || materialesJson.isEmpty())
                        return "Ninguno";
                return materialesJson.replace("[", "").replace("]", "").replace("\"", "").replace(",", ", ");
        }

        private String formatearConfig(String configJson) {
                if (configJson == null || configJson.isEmpty())
                        return "";
                try {
                        configJson = configJson.replace("{", "").replace("}", "").replace("\"", "");
                        String[] partes = configJson.split(",");
                        StringBuilder sb = new StringBuilder();
                        for (String parte : partes) {
                                if (!parte.trim().isEmpty())
                                        sb.append(parte.trim()).append(" | ");
                        }
                        return sb.toString();
                } catch (Exception e) {
                        return configJson;
                }
        }

}