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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Haro
 */
public class ConnectionBD {

    private static final String DBIP = "192.168.0.244";
    private static final String DBURL = "jdbc:postgresql://" + DBIP + ":5432/preproduccion";
    private static final String DBUSERNAME = "bereforma";
    private static final String DBPASS = "PAss01";
    private static ResultSet rs = null;
    private static Statement stmt = null;

    /**
     * Metodo que retorna una conexion
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBURL, DBUSERNAME, DBPASS);
    }

    /**
     * Ejecuta una consulta de base de datos
     *
     * @param qry
     * @return
     */
    public static ResultSet query(String qry) {
        try {
            Statement s = getConnection().createStatement();
            // System.out.println("Consulta query: " + qry);
            rs = s.executeQuery(qry);
        } catch (SQLException e) {
            System.out.println("No se a completado la peticion " + e);
        }
        return rs;
    }

    /**
     * Actualiza una consulta
     *
     * @param qry
     */
    public static void ejectuar(String qry) {
        try {
            Statement s = getConnection().createStatement();
            s.executeUpdate(qry);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param qry
     * @return
     * @throws SQLException
     */
    public ResultSet consultar(String qry) throws SQLException {
        getConnection();
        return Conexion.query(qry);
    }

    /**
     * 
     * @param sql
     * @return
     * @throws SQLException 
     */
    public ResultSet tablaException(String sql) throws SQLException {
        //Carga driver
        getConnection();
        stmt = (Statement) getConnection().createStatement();
        if (stmt != null) {
            //System.out.println(sql);
            rs = stmt.executeQuery(sql);
        }
        return rs;
    }
}