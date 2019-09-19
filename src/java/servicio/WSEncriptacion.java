/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import controller.ControladorEncriptacion;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Base64;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author JASMIN-SOMA
 */
@WebService(serviceName = "WSEncriptacion")
public class WSEncriptacion {

    String user = "jEXMgNZHsGHJKDR4m5Mq";
    String pass = "qmQTWFj8y9rs0d1yLNsWFgPfUfKXyg6yO3XmQjL5";

    String urlhash = "https://sandbox.tesseract.mx/api/v2/institution/hash/";

    /**
     * This is a sample web service operation
     *
     * @param message
     * @return
     */
    @WebMethod(operationName = "encryptMessageHASH")
    public String encryptMessageHASH(@WebParam(name = "message") String message) {
        String result = "";
        try {
            //Para las cadenas del certificado
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String string) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String string) throws CertificateException {

                }
            }
            };

            // Instalar el all-trusting trust manager fh
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            // Crear all-trusting verificador de hostname
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }

            };

            // Instalar el all-trusting verificador de host
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            //Url
            URL url = new URL(urlhash);

            String json = "{ \"message\": \"" + message + "\", \"algorithm\": \"SHA512\" }";

            //autenticacion en la API 
            String authStr = user + ":" + pass;
            String encoding = Base64.getEncoder().encodeToString(authStr.getBytes());

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("content-type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Basic " + encoding);

            //****Para ingresar la cadena Json a la API
            OutputStream os = connection.getOutputStream();
            os.write(json.getBytes("utf-8"));
            os.close();

            //*****Leer la respuesta de la API (Response Headers) en caso de que mande
            InputStream in = new BufferedInputStream(connection.getInputStream());

            //Cadena Json que se regresa (Response Body)
            String jsonIn = IOUtils.toString(in);

            JsonElement jsonElement = new JsonParser().parse(jsonIn);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            result = jsonObject.get("message").getAsString();

            in.close();
            connection.disconnect();

        } catch (JsonSyntaxException | IOException | KeyManagementException | NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }

    @WebMethod(operationName = "encryptDecryptMessageAES")
    public String encryptDecryptMessageAES(
            @WebParam(name = "message") String message) {
        String result = "";
        ControladorEncriptacion ce = new ControladorEncriptacion();
        result = ce.encriptar(message);
        return result;
    }

    @WebMethod(operationName = "decryptMessageAES")
    public String decryptMessageAES(
            @WebParam(name = "message") String message) {
        String result = "";
        ControladorEncriptacion ce = new ControladorEncriptacion();
        result = ce.desencriptar(message);
        return result;
    }

    @WebMethod(operationName = "encryptHASH")
    public String encryptHASH(@WebParam(name = "message") String message) {
        String result = "";
        ControladorEncriptacion ce = new ControladorEncriptacion();
        result = ce.encryptMessageHASH(message);
        return result;
    }
}
