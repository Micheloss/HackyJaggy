package com.app.jaggy.hacky.hackyjaggy;

/**
 * Created by madiazb on 04/02/2017.
 */

public class Bombilla {

    private boolean encendida;

    private float intensidad;

    private String color;

    private String intensidad_color;

    //Para evitar instanciaciones innecesarias
    public Bombilla(){

    }


    public boolean isEncendida() {
        return encendida;
    }

    public void setEncendida(boolean encendida) {
        this.encendida = encendida;
    }

    public float getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(float intensidad) {
        this.intensidad = intensidad;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIntensidad_color() {
        return intensidad_color;
    }

    public void setIntensidad_color(String intensidad_color) {
        this.intensidad_color = intensidad_color;
    }
}
