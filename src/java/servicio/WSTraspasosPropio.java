/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import controller.ControladorTranspasoPropio;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author JASMIN-SOMA
 */

@WebService(serviceName = "traspasoTercero")
public class WSTraspasosPropio {
    
    
    @WebMethod(operationName = "traspasoTercero")
    public String traspasoTercero (
            @WebParam(name ="pclienteid") int pclienteid, 
            @WebParam(name ="pmovimiento") String pmovimiento,
            @WebParam(name ="pmonto") double pmonto, 
            @WebParam(name ="pconcepto") String pconcepto, 
            @WebParam(name ="pcvoperacion") String pcvoperacion, 
            @WebParam(name ="b_numcliente") String b_numcliente){
        
        ControladorTranspasoPropio ctp = new ControladorTranspasoPropio();
        String res = ctp.getTraspasoPropio(pclienteid, pmovimiento, pmonto, pconcepto, pcvoperacion, b_numcliente);
        return res;
    }
    
    
}
