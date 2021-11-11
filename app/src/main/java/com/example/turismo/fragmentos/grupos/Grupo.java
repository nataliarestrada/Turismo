package com.example.turismo.fragmentos.grupos;

public class Grupo {
    private int id;
    private String sitio;
    private String region;
    private String genero;
    private String mes_estimado;
    private String origen;
    private int cant_min;
    private int cant_max;
    private int cantidad;
    private String descripcion;
    private String estado;

    public Grupo(int id, String sitio, String region, String genero, String mes_estimado,
                 String origen, int cant_min, int cant_max, int cantidad, String descripcion, String estado) {

        this.id = id;
        this.sitio = sitio;
        this.region = region;
        this.genero = genero;
        this.mes_estimado = mes_estimado;
        this.origen = origen;
        this.cant_min = cant_min;
        this.cant_max = cant_max;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Grupo() {
    }

    public Grupo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getMes_estimado() {
        return mes_estimado;
    }

    public void setMes_estimado(String mes_estimado) {
        this.mes_estimado = mes_estimado;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public int getCant_min() {
        return cant_min;
    }

    public void setCant_min(int cant_min) {
        this.cant_min = cant_min;
    }

    public int getCant_max() {
        return cant_max;
    }

    public void setCant_max(int cant_max) {
        this.cant_max = cant_max;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "id=" + id +
                ", sitio='" + sitio + '\'' +
                ", region='" + region + '\'' +
                ", genero='" + genero + '\'' +
                ", mes_estimado='" + mes_estimado + '\'' +
                ", origen='" + origen + '\'' +
                ", cant_min=" + cant_min +
                ", cant_max=" + cant_max +
                ", cantidad=" + cantidad +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
