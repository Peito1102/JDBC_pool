package org.vasquez.java.jdbc.repositorio;

import org.vasquez.java.jdbc.modelo.Producto;

import java.util.List;

public interface Repositorio<T> {
    List<T> listar();
    T porId(Long id);
    void guardar(T t);
    void elmimnar(Long id);
}
