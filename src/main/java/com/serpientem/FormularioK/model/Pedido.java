package com.serpientem.FormularioK.model;

import jakarta.persistence.*;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoPedido;
    private String descripcionPersona;
    private String presupuesto;
    private String tipoAccesorio;
    private String otroAccesorio;
    private String origen;
    private String referencia;
    private String emailCliente;
    private String codigoSeguimiento;

    @Column(nullable = false)
    private String estado = "Recibido";

    private java.time.LocalDateTime fechaCreacion = java.time.LocalDateTime.now();

    @Lob
    private String materiales;

    @Lob
    private String materialesConfig;

    @Lob
    private String descripcionFinal;

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
