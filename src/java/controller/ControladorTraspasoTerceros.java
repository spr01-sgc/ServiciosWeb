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
public class ControladorTraspasoTerceros {

    static ResultSet rse = null;

    public ControladorTraspasoTerceros() {
    }

    public String[] getTraspasoTercero(int pclienteid, String pmovimiento,
            double pmonto, String pconcepto,
            String pbeneficiario, int pidbeneficiario,
            String pcbeneficiario, double piva,
            String prfc, String prfcbeneficiario,
            String pcvoperacion, String pnum_cliente,
            String v_idsesion, String v_nom_ben) {
        String[] res = new String[14];
        try {
            Conexion c = new Conexion();
            Object[] dat = new Object[14];

            dat[0] = pclienteid;
            dat[1] = pmovimiento;
            dat[2] = pmonto;
            dat[3] = pconcepto;
            dat[4] = pbeneficiario;
            dat[5] = pidbeneficiario;
            dat[6] = pcbeneficiario;
            dat[7] = piva;
            dat[8] = prfc;
            dat[9] = prfcbeneficiario;
            dat[10] = pcvoperacion;
            dat[11] = pnum_cliente;
            dat[12] = v_idsesion;
            dat[13] = v_nom_ben;
           
            String arrpostgres = Util.creaObjetoPS(dat);

            StringBuilder query = new StringBuilder();
            //Construimos consulta a funcion postgres
            query.append("Select * from dblink_be_traspaso_terceros(").append(arrpostgres).append(");");

//           String consulta = "Select * from seg_spi_login (").append(arrpostgres).append(");";
            rse = c.tablaException(query.toString());

            while (rse.next()) {
                // guarda el resultado de la funcion en un array
                Array arr = rse.getArray(1);

                //convierte el array en arreglo de strings
                res = (String[]) arr.getArray();

            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorTraspasoTerceros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}
