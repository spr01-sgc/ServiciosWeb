/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utl;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author JASMIN-SOMA
 */
public interface IConexionPool {
    
    public Connection extraerConexion() throws SQLException;
    public void liberarConexion(Conexion conn) throws SQLException;
}
