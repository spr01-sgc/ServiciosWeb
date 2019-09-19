/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergio
 */
public class Conexion {

    static Connection cn;
    static String puerto;
    static String nombreBD;
    static String contrasena;

    static String driver = "org.postgresql.Driver";
    static String ip = "192.168.0.244";
    static String connect = "jdbc:postgresql://" + ip + ":5432/preproduccion";
    static String usuario = "bereforma";
    static String clave = "PAss01"; 

    static Connection con = null;

    public Conexion() {
        getConexion();
    }

    public static Connection getConexion() {

        try {
            if (con == null) {
                Class.forName(driver);
                con = DriverManager.getConnection(connect, usuario, clave);
            }
            System.out.println("SE CONECTO");
            Date fecha = new Date();
            System.out.println("Fecha: " + fecha);
        } catch (Exception e) {
            System.out.println("Error en la conexiÃ³n " + e);
        }
        return con;
    }

    public static ResultSet query(String qry) {
        ResultSet rs = null;
        try {
            Statement s = con.createStatement();
            rs = s.executeQuery(qry);
        } catch (Exception e) {
            System.out.println("No se a completado la peticiÃ³n " + e);
        }
        return rs;
    }

    public static void ejectuar(String qry) {
        try {
            Statement s = con.createStatement();
            s.executeUpdate(qry);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ResultSet consultar(String qry) {
        ResultSet rs = null;
        Conexion.getConexion();
        rs = Conexion.query(qry);
        return rs;
    }

    public ResultSet tablaException(String sql) throws SQLException {
        //Carga driver

        ResultSet rs = null;

//       if (cn == null || cn.isClosed()) {
        conectar();
//        }
        Statement stmt = null;
        stmt = (Statement) cn.createStatement();

        if (stmt != null) {
//                if (impr) {
            System.out.println(sql);
//                }
            rs = stmt.executeQuery(sql);
            System.out.println("--*---*---*--*--");
        }

        return rs;
    }

//     public ResultSet tablaException(String sql) throws SQLException {
//        //Carga driver
//
//        ResultSet rs = null;
//
////       if (cn == null || cn.isClosed()) {
//            conectar();
////        }
//        Statement stmt = null;
//        stmt = (Statement) cn.createStatement();
//
//        if (stmt != null) {
////                if (impr) {
//            System.out.println(sql);
////                }
//            rs = stmt.executeQuery(sql);
//            System.out.println("--*---*---*--*--");
//        }
//       
//        return rs;
//    }
    public void conectar() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: No se pudo cargar el driver");
            e.printStackTrace();
        }

        ConexionBasico con = new ConexionBasico(connect, usuario, contrasena);
        try {
            cn = con.extraerConexion();
        } catch (SQLException ex) {
            System.out.println("Error en la conexion: " + ex.toString());

        }
    }

}
