package org.aguzman.anotaciones.ejemplo;

import org.aguzman.anotaciones.ejemplo.models.Producto;
import org.aguzman.anotaciones.ejemplo.procesador.JsonSerializador;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;

public class EjemploAnotacion {
    public static void main(String[] args) {
        Producto p=new Producto();
        p.setFecha(LocalDate.now());
        p.setNombre("mesa centro roble");
        p.setPrecio(1000L);

        System.out.println("jason = " + JsonSerializador.convertirJson(p));
    }
}
