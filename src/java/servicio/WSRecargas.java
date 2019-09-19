/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import controller.ControladorRecargas;
import controller.ControladorTranspasoPropio;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author JASMIN-SOMA
 */

@WebService(serviceName = "recargas")
public class WSRecargas {
    
    
      
    @WebMethod(operationName = "recargas")
    public String recargas (
            @WebParam(name ="cliente") String cliente, 
            @WebParam(name ="cveoperacion") Double cveoperacion, 
            @WebParam(name ="monto") Double monto) {
        
        ControladorRecargas recargas  = new ControladorRecargas();
        String res = recargas.validaServicios(cliente, cveoperacion, monto);
        return res;
    }
}
