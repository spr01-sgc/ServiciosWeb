/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import controller.ControladorTranspasoPropio;
import controller.ControladorTraspasoTerceros;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 *
 * @author JASMIN-SOMA
 */
@WebService(serviceName = "TraspasoTerceros")
public class WSTraspasoTerceros {

    @WebMethod(operationName = "traspasoTerceros")
    @WebResult(name = "TraspasoTerceros")
    public String[] traspasoTerceros(
            @WebParam(name = "cliente_id") int pclienteid,
            @WebParam(name = "movimiento") String pmovimiento,
            @WebParam(name = "monto") double pmonto,
            @WebParam(name = "concepto") String pconcepto,
            @WebParam(name = "beneficiario") String pbeneficiario,
            @WebParam(name = "id_beneficiario") int pidbeneficiario,
            @WebParam(name = "cbeneficiario") String pcbeneficiario,
            @WebParam(name = "iva") double piva,
            @WebParam(name = "rfc") String prfc,
            @WebParam(name = "rfc_beneficiario") String prfcbeneficiario,
            @WebParam(name = "cve_operacion") String pcvoperacion,
            @WebParam(name = "numero_cliente") String pnum_cliente,
            @WebParam(name = "id_sesion") String v_idsesion,
            @WebParam(name = "nombre_beneficiario") String v_nom_ben) {
        WSTraspasoTerceros td = new WSTraspasoTerceros();
        ControladorTraspasoTerceros ctp = new ControladorTraspasoTerceros();
        String[] res = ctp.getTraspasoTercero(pclienteid, pmovimiento,
                pmonto, pconcepto,
                pbeneficiario, pidbeneficiario,
                pcbeneficiario, piva,
                prfc, prfcbeneficiario,
                pcvoperacion, pnum_cliente,
                v_idsesion, v_nom_ben);
        return res;

    }
}
