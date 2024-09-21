package org.vasquez.java.jdbc;

import org.vasquez.java.jdbc.modelo.Categoria;
import org.vasquez.java.jdbc.modelo.Producto;
import org.vasquez.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.vasquez.java.jdbc.repositorio.Repositorio;
import org.vasquez.java.jdbc.util.ConexionBD;

import java.sql.*;
import java.util.Date;

public class EjemploJdbc {
    public static void main(String[] args) {

            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
            System.out.println("=======LISTAR=======");
            System.out.println("id | nombre | precio | fecha | categoría");
            repositorio.listar().forEach(System.out::println);
            System.out.println();

            System.out.println("=======OBTENER POR ID=======");
            System.out.println(repositorio.porId(1L));
            System.out.println();

            System.out.println("=======INSERTAR NUEVO PRODUCTO=======");
            Producto producto = new Producto(3L,
                    "Notebook Omen HP",1000,new Date());
            Categoria categoria = new Categoria();
            categoria.setId(3L);
            producto.setCategoria(categoria);
            repositorio.guardar(producto);
            System.out.println("Producto guardado con éxito.");
            System.out.println("id | nombre | precio | fecha | categoría");
            repositorio.listar().forEach(System.out::println);


    }
}
