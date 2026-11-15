package cl.duoc.tienda.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    private Connection cone;

    public Conexion(){}

    public Connection getCone(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cone = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/tienda", "root", "");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return cone;
    }



}
