/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utl.Conexion;
import utl.Util;

/**
 *
 * @author JASMIN-SOMA
 */
public class ControladorRecargas {

    static ResultSet rse = null;
    static String mensaje = "";

    public ControladorRecargas() {
    }

    public String validaServicios(String cliente, double cveoperacion,double monto) {
        try {
            Conexion c = new Conexion();
            
            StringBuilder query = new StringBuilder();
            //Construimos consulta a funcion postgres
            query.append("select * from be_valida_send_servicio(")
                    .append("'")
                    .append(cliente).append("'")
                    .append(',')
                    .append("'").append(cveoperacion).append("'").append(',')
                    .append("'").append(monto).append("'")
                   .append(") ;");

            rse = c.tablaException(query.toString());
            while (rse.next()) {
                mensaje = mensaje + rse.getArray(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorTranspasoPropio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }
    
}
