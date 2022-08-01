package org.aguzman.anotaciones.ejemplo.procesador;

import org.aguzman.anotaciones.ejemplo.JsonAtributo;
import org.aguzman.anotaciones.ejemplo.procesador.exception.JsonSerializadorExceptions;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class JsonSerializador {
    public static String convertirJson(Object object){
        if (Objects.isNull(object)){
            throw new JsonSerializadorExceptions("El objeto a serializar no puede ser null");
        }
        Field[] atributos=object.getClass().getDeclaredFields();
        return Arrays.stream(atributos)
                .filter(f->f.isAnnotationPresent(JsonAtributo.class))
                .map(f->{
                    f.setAccessible(true);
                    String nombre= f.getAnnotation(JsonAtributo.class).nombre().equals("")
                            ?f.getName()
                            :f.getAnnotation(JsonAtributo.class).nombre();

                    try {
                        Object valor=f.get(object);
                        if (f.getAnnotation(JsonAtributo.class).capitalizar() &&
                        valor instanceof String){
                            String nuevoValor= (String) valor;
                            nuevoValor=String.valueOf(nuevoValor.charAt(0)).toUpperCase()
                                +nuevoValor.substring(1).toLowerCase();
                            f.set(object,nuevoValor);
                        }
                        return "\""+nombre+"\": \""+f.get(object)+"\"";
                    } catch (IllegalAccessException e) {
                        throw new JsonSerializadorExceptions("Error al serializar a json "+e.getMessage());
                    }
                })
                .reduce("{",(a,b)->{
                    if ("{".equals(a)){
                        return a+b;
                    }
                    return a+", "+b;
                }).concat("}");
    }
}
