package com.example.turismo;

public class Integrantes {


    private String alias;
    private String edad;
    private String telefono;
    private String email;

    public Integrantes(){

    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    @Override
    public String toString() {
        return "Integrantes{" +

                "alias='" + alias + '\'' +
                ", edad='" + edad + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Integrantes(String alias, String edad, String telefono, String email) {

        this.alias = alias;
        this.edad = edad;
        this.telefono = telefono;
        this.email = email;
    }
//    private String contrasenia;
//    private String confcontrasenia;

}
