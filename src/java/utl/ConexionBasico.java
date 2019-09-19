/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

/**
 *
 * Creador : Victor Juarez Cabrera E-Mail : intovico@hotmail.com Objetivo :
 *
 *
 */
public class ConexionBasico implements IConexionPool {

    private Stack pool;
    protected String connectionURL;
    protected String userName;
    protected String password;

    /**
     * Genera Pool bï¿½sico de conexiï¿½n utilizando URL , contraseï¿½a y nombre
     *
     * @param aConnectionURL
     * @param aUserName
     * @param aPassword
     */
    public ConexionBasico(String aConnectionURL, String aUserName, String aPassword) {
        connectionURL = aConnectionURL;
        userName = aUserName;
        password = aPassword;
        pool = new Stack();
    }

    /**
     * Adquiere conexiï¿½n del Pool o genera una nueva si el pool esta vacï¿½o
     *
     * @return
     * @throws SQLException
     */
    @Override
    public synchronized Connection extraerConexion()
            throws SQLException {
        // Si el pool no esta vacio, tomar una conexion
        if (!pool.empty()) {
            return (Connection) getPool().pop();
        } else {
            // Entonces generar una conexion nueva
            return DriverManager.getConnection(
                    connectionURL, userName, password);
        }
    }

    /**
     * Regresar conexion al pool
     *
     * @param conn
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public synchronized void liberarConexion(Connection conn)
            throws SQLException {
        conn.close();
        getPool().push(conn);
    }

    /**
     * @return the pool
     */
    public Stack getPool() {
        return pool;
    }

    /**
     * @param pool the pool to set
     */
    public void setPool(Stack pool) {
        this.pool = pool;
    }

    public ConexionBasico() {
    }

    @Override
    public void liberarConexion(Conexion conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
