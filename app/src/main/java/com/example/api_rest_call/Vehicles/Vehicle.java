package com.example.api_rest_call.Vehicles;

public class Vehicle {
    private String id;
    private String marca;
    private String modelo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        if (marca.isEmpty())
            throw new IllegalArgumentException();

        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        if (modelo.isEmpty())
            throw new IllegalArgumentException();

        this.modelo = modelo;
    }
}
