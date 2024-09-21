package org.vasquez.java.jdbc;

import org.vasquez.java.jdbc.modelo.Producto;
import org.vasquez.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.vasquez.java.jdbc.repositorio.Repositorio;
import org.vasquez.java.jdbc.util.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;

public class EjemploJdbcDelete {
    public static void main(String[] args) {

            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
            System.out.println("=======LISTAR=======");
            System.out.println("id | nombre | precio | fecha");
            repositorio.listar().forEach(System.out::println);
            System.out.println();

            System.out.println("=======OBTENER POR ID=======");
            System.out.println(repositorio.porId(2L));
            System.out.println();

            System.out.println("=======EDITAR NUEVO PRODUCTO=======");
            repositorio.elmimnar(4L);
            System.out.println("Producto eliminado con éxito.");
            System.out.println("id | nombre | precio | fecha");
            repositorio.listar().forEach(System.out::println);

    }
}
