package com.serpientem.FormularioK.model; // Paquete del modelo

import jakarta.persistence.*; // Anotaciones JPA para persistencia

@Entity // Marca la clase como entidad de base de datos
public class Pedido {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    private Long id;

    private String tipoPedido; // Tipo de pedido (propio o otra persona)
    private String descripcionPersona; // Descripción del destinatario
    private String presupuesto; // Presupuesto estimado
    private String tipoAccesorio; // Tipo de accesorio
    private String otroAccesorio; // Accesorio personalizado
    private String origen; // Origen del diseño
    private String referencia; // Referencia del diseño
    private String emailCliente; // Email del cliente
    private String codigoSeguimiento; // Código de seguimiento

    @Column(nullable = false) // No puede ser nulo
    private String estado = "Recibido"; // Estado inicial del pedido

    private java.time.LocalDateTime fechaCreacion = java.time.LocalDateTime.now(); // Fecha de creación automática

    @Lob // Campo grande (texto largo)
    private String materiales; // Materiales seleccionados

    @Lob // Texto largo
    private String materialesConfig; // Configuración de materiales

    @Lob // Texto largo
    private String descripcionFinal; // Descripción final del pedido

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    } // Obtener ID

    public void setId(Long id) {
        this.id = id;
    } // Asignar ID

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public String getDescripcionPersona() {
        return descripcionPersona;
    }

    public void setDescripcionPersona(String descripcionPersona) {
        this.descripcionPersona = descripcionPersona;
    }

    public String getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(String presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getTipoAccesorio() {
        return tipoAccesorio;
    }

    public void setTipoAccesorio(String tipoAccesorio) {
        this.tipoAccesorio = tipoAccesorio;
    }

    public String getOtroAccesorio() {
        return otroAccesorio;
    }

    public void setOtroAccesorio(String otroAccesorio) {
        this.otroAccesorio = otroAccesorio;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getMateriales() {
        return materiales;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }

    public String getMaterialesConfig() {
        return materialesConfig;
    }

    public void setMaterialesConfig(String materialesConfig) {
        this.materialesConfig = materialesConfig;
    }

    public String getDescripcionFinal() {
        return descripcionFinal;
    }

    public void setDescripcionFinal(String descripcionFinal) {
        this.descripcionFinal = descripcionFinal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public java.time.LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getCodigoSeguimiento() {
        return codigoSeguimiento;
    }

    public void setCodigoSeguimiento(String codigoSeguimiento) {
        this.codigoSeguimiento = codigoSeguimiento;
    }

}