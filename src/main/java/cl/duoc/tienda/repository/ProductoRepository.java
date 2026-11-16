package cl.duoc.tienda.repository;

import cl.duoc.tienda.model.Producto;
import cl.duoc.tienda.utils.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductoRepository {
    private Connection coneLocal;

    public ProductoRepository(){}



    // Crear los 5 métodos:

    // Agregar Producto:
    public boolean agregarProducto(Producto p) throws SQLException {
        boolean inserto = false;
        try {
            coneLocal = new Conexion().getCone();
            String sql = "INSERT INTO producto VALUES (?,?,?)";
            PreparedStatement ps = coneLocal.prepareStatement(sql);

            ps.setString(1,p.getCodigo());
            ps.setDouble(2,p.getPrecio());
            ps.setInt(3, p.getStock());

            int res = ps.executeUpdate();
            if (res == 1){
                inserto = true;
            }

        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally{
            coneLocal.close();
        }
        return inserto;
    }

    //Eliminar producto:
    public boolean eliminarProducto(String codigo) throws SQLException {
        boolean  elimino = false;
        try{
            coneLocal = new Conexion().getCone();
            String sql = "DELETE FROM producto WHERE codigo=?";
            PreparedStatement ps = coneLocal.prepareStatement(sql);
            ps.setString(1,codigo);

            int res = ps.executeUpdate();
            if (res == 1){
                elimino = true;
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            coneLocal.close();
        }
        return elimino;
    }




}
