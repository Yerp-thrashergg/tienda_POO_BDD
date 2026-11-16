package cl.duoc.tienda.repository;

import cl.duoc.tienda.model.Bebestible;
import cl.duoc.tienda.model.Congelado;
import cl.duoc.tienda.model.Producto;
import cl.duoc.tienda.utils.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoRepository {
    private Connection coneLocal;

    public ProductoRepository(){}

    // Agregar Producto:
    public boolean agregarProducto(Producto p) throws SQLException {
        boolean inserto = false;
        try {
            coneLocal = new Conexion().getCone();
            String sql = "INSERT INTO producto (codigo,precio,stock,tipo,tiene_gas,temperatura) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = coneLocal.prepareStatement(sql);

            ps.setString(1,p.getCodigo());
            ps.setDouble(2,p.getPrecio());
            ps.setInt(3, p.getStock());

            if(p instanceof Bebestible){
                Bebestible b = (Bebestible) p;
                ps.setString(4,"Bebestible");
                ps.setBoolean(5,b.isTieneGas());
                ps.setDouble(6,0);
            }else{
                Congelado c = (Congelado) p;
                ps.setString(4,"Congelado");
                ps.setBoolean(5,false);
                ps.setDouble(6,c.getTemperatura());
            }

            int res = ps.executeUpdate();
            if (res == 1){
                inserto = true;
            }

        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally{
            if(coneLocal != null){
                coneLocal.close();
            }
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
            if(coneLocal != null){
                coneLocal.close();
            }
        }
        return elimino;
    }

    // Ver Productos:
    public ArrayList<Producto> verProductos() throws SQLException {
        ArrayList<Producto> lista = new ArrayList<>();
        try{
            coneLocal = new Conexion().getCone();
            String sql = "SELECT codigo,precio,stock,tipo,tiene_gas,temperatura FROM producto";
            PreparedStatement ps = coneLocal.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String codigo = rs.getString(1);
                double precio = rs.getDouble(2);
                int stock = rs.getInt(3);
                String tipo = rs.getString(4);
                boolean tieneGas = rs.getBoolean(5);
                double temperatura = rs.getDouble(6);

                Producto p;
                if(tipo.equals("Bebestible")){
                    p = new Bebestible(codigo, precio, stock, tieneGas);
                }else{
                    p = new Congelado(codigo, precio, stock, temperatura);
                }
                lista.add(p);
            }
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        } finally{
            if(coneLocal != null){
                coneLocal.close();
            }
        }
        return  lista;
    }

}