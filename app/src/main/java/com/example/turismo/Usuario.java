package com.example.turismo;

public class Usuario {

    //private String id;
    private String nombre;
    private String alias;
    private String genero;
    private String origen;
    private int edad;
    private int telefono;
    private String email;
    private String contrasenia;


    public Usuario( String nombre, String alias, String genero, String origen, int edad, int telefono, String email, String contrasenia) {
        //this.id = id;
        //String id,
        this.nombre = nombre;
        this.alias = alias;
        this.genero = genero;
        this.origen = origen;
        this.edad = edad;
        this.telefono = telefono;
        this.email = email;
        this.contrasenia = contrasenia;
    }

    public Usuario() {
        /*Usuario u = new Usuario();
                            u.setNombre(nombre);
                            u.setGenero(genero);
                            u.setOrigen(origen);
                            u.setEdad(Integer.parseInt(edad));
                            u.setTelefono(Integer.parseInt(telefono));
                            u.setEmail(email);
                            u.setContrasenia(contrasenia);
                            u.setConfcontrasenia(confcontrasenia);*/

    }

    /*public String getId() {
        return id;
    }*/

/*    public void setId(String id) {
        this.id = id;
    }*/

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", alias='" + alias + '\'' +
                ", genero='" + genero + '\'' +
                ", origen='" + origen + '\'' +
                ", edad=" + edad +
                ", telefono=" + telefono +
                ", email='" + email + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }
}
