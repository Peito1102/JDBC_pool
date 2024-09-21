package org.vasquez.java.jdbc.repositorio;

import org.vasquez.java.jdbc.modelo.Categoria;
import org.vasquez.java.jdbc.modelo.Producto;
import org.vasquez.java.jdbc.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImpl implements Repositorio<Producto> {
    private Connection getConnection() throws SQLException {
        return ConexionBD.getConnection();
    }
    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        try(Connection conn = getConnection();
                Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.*, c.nombre as categoria " +
                    "FROM productos as p inner join categorias as c on (p.categoria_id = c.id)")) {
            while (rs.next()) {
                Producto p = new Producto(rs.getLong(1),
                        rs.getString(2),rs.getInt(3),rs.getDate(4));
                Categoria categoria = new Categoria();
                categoria.setId(rs.getLong(5));
                categoria.setNombre(rs.getString(6));
                p.setCategoria(categoria);
                productos.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    @Override
    public Producto porId(Long id) {
        Producto producto = null;
        try(Connection conn = getConnection();
                PreparedStatement stmt = conn
                .prepareStatement("SELECT p.*, c.nombre as categoria" +
                        " FROM productos as p inner join categorias as c " +
                        "on (p.categoria_id = c.id) WHERE p.id = ?")) {
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                producto = new Producto(rs.getLong(1),rs.getString(2),
                        rs.getInt(3),rs.getDate(4));
                Categoria categoria = new Categoria();
                categoria.setId(rs.getLong(5));
                categoria.setNombre(rs.getString(6));
                producto.setCategoria(categoria);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return producto;
    }

    @Override
    public void guardar(Producto producto) {
        String sql;
        if (producto.getId() != null && producto.getId() > 0) {
            sql = "UPDATE productos SET nombre=?, precio=?, categoria_id = ? WHERE id =?";
        } else {
            sql = "INSERT INTO productos(nombre,precio,categoria_id,fecha_registro) " +
                    "VALUES(?,?,?,?)";
        }
        try(Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,producto.getNombre());
            stmt.setInt(2,producto.getPrecio());
            stmt.setLong(3,producto.getCategoria().getId());
            if (producto.getId() != null && producto.getId() > 0) {
                stmt.setLong(4,producto.getId());
            } else {
                stmt.setDate(4,new Date(producto.getFechaRegistro().getTime()));
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void elmimnar(Long id) {
        try(Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM productos WHERE id = ?")) {
            stmt.setLong(1,id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
