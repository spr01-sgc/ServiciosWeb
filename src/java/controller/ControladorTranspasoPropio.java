/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utl.Conexion;

/**
 *
 * @author JASMIN-SOMA
 */

public class ControladorTranspasoPropio {

    static ResultSet rse = null;
    static String mensaje = "";

    public ControladorTranspasoPropio() {
    }

    public String getTraspasoPropio(int pclienteid, String pmovimiento, double pmonto,
            String pconcepto, String pcvoperacion, String b_numcliente) {
        try {
            Conexion c = new Conexion();
            
            StringBuilder query = new StringBuilder();
            //Construimos consulta a funcion postgres
            query.append("select * from dblink_be_traspaso_cuentas_propias(")
                    .append(pclienteid).append(',')
                    .append("'").append(pmovimiento).append("'").append(',')
                    .append("'").append(pmonto).append("'").append(',')
                    .append("'").append(pconcepto).append("'").append(',')
                    .append("'").append(pcvoperacion).append("'").append(',')
                    .append("'").append(b_numcliente).append("'").append(") ;");

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
