/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ControladorEncriptacion {

    //aqui se implementan las llaves para consumir en los metodos, son de suma importanciaa no se pueden exponer. 
    String user = "jEXMgNZHsGHJKDR4m5Mq";
    String pass = "qmQTWFj8y9rs0d1yLNsWFgPfUfKXyg6yO3XmQjL5";
    String url = "https://sandbox.tesseract.mx/api/v2/institution/partition/keys";
    String urlhash = "https://sandbox.tesseract.mx/api/v2/institution/hash";

    public ControladorEncriptacion() {
        verifyHostName();
    }

    public void verifyHostName() {

        try {
            // Create a trust manager that does not validate certificate chains
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

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ControladorEncriptacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(ControladorEncriptacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String desencriptar(String cadena) {

        String transformation = "AES_CBC_PKCS5Padding";
        String mode = "DECRYPT";
        String encode = "BASE64";
        String result = "";
        try {
            //autenticacion en la API
            String authStr = user + ":" + pass;
            String encoding = Base64.getEncoder().encodeToString(authStr.getBytes());

            //obtener Llave Hexadecimal AES (hexadecimal-label-aes-key)
            URL urlKey = new URL(url);

            HttpsURLConnection connectionKey = (HttpsURLConnection) urlKey.openConnection();
            connectionKey.setRequestProperty("host", "sandbox.tesseract.mx");
            connectionKey.setRequestProperty("accept", "application/json");
            connectionKey.setRequestProperty("content-type", "application/json");
            connectionKey.setRequestProperty("cache-control", "no-cache");
            connectionKey.setDoOutput(true);
            connectionKey.setDoInput(true);
            connectionKey.setRequestMethod("GET");
            connectionKey.setRequestProperty("Authorization", "Basic " + encoding);

            //*****Leer la respuesta de la API (Response Headers) en caso de que mande
            InputStream inKey = new BufferedInputStream(connectionKey.getInputStream());

            //Cadena Json que se regresa (Response Body)
            String jsonInKey = IOUtils.toString(inKey);

            JsonElement jsonElementKey = new JsonParser().parse(jsonInKey);
            JsonObject jsonObjectKey = jsonElementKey.getAsJsonObject();
            JsonArray jsonArrayKey = jsonObjectKey.getAsJsonArray("_content");

            String urlHexLabelAESKey = "";

            for (int i = 0; i < jsonArrayKey.size(); i++) {
                jsonObjectKey = (JsonObject) jsonArrayKey.get(i).getAsJsonObject().get("_links").getAsJsonObject().get("aes");
                if (jsonObjectKey != null) {
                    urlHexLabelAESKey = jsonObjectKey.get("href").getAsString();
                }
            }
            System.out.println("URL " + urlHexLabelAESKey);
            inKey.close();
            connectionKey.disconnect();

            //Url
            URL url = new URL(urlHexLabelAESKey);

            String json = "{\n"
                    + "    \"transformation\": \"" + transformation + "\",\n"
                    + "    \"mode\": \"" + mode + "\",\n"
                    + "    \"message\": \"" + cadena + "\",\n"
                    + "    \"encode\": \"" + encode + "\"\n"
                    + "}";

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("host", "sandbox.tesseract.mx");
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

        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public String encriptar(String cadena) {

        String transformation = "AES_CBC_PKCS5Padding";
        String mode = "ENCRYPT";
        String encode = "BASE64";
        String result = "";
        try {
            //autenticacion en la API
            String authStr = user + ":" + pass;
            String encoding = Base64.getEncoder().encodeToString(authStr.getBytes());

            //obtener Llave Hexadecimal AES (hexadecimal-label-aes-key)
            URL urlKey = new URL(url);

            HttpsURLConnection connectionKey = (HttpsURLConnection) urlKey.openConnection();
            connectionKey.setRequestProperty("host", "sandbox.tesseract.mx");
            connectionKey.setRequestProperty("accept", "application/json");
            connectionKey.setRequestProperty("content-type", "application/json");
            connectionKey.setRequestProperty("cache-control", "no-cache");
            connectionKey.setDoOutput(true);
            connectionKey.setDoInput(true);
            connectionKey.setRequestMethod("GET");
            connectionKey.setRequestProperty("Authorization", "Basic " + encoding);

            //*****Leer la respuesta de la API (Response Headers) en caso de que mande
            InputStream inKey = new BufferedInputStream(connectionKey.getInputStream());

            //Cadena Json que se regresa (Response Body)
            String jsonInKey = IOUtils.toString(inKey);

            JsonElement jsonElementKey = new JsonParser().parse(jsonInKey);
            JsonObject jsonObjectKey = jsonElementKey.getAsJsonObject();
            JsonArray jsonArrayKey = jsonObjectKey.getAsJsonArray("_content");

            String urlHexLabelAESKey = "";

            for (int i = 0; i < jsonArrayKey.size(); i++) {
                jsonObjectKey = (JsonObject) jsonArrayKey.get(i).getAsJsonObject().get("_links").getAsJsonObject().get("aes");
                if (jsonObjectKey != null) {
                    urlHexLabelAESKey = jsonObjectKey.get("href").getAsString();
                }
            }

            inKey.close();
            connectionKey.disconnect();

            // Url
            System.out.println("url *** -- " + urlHexLabelAESKey);
            URL url = new URL(urlHexLabelAESKey);

            String json = "{\n"
                    + "    \"transformation\": \"" + transformation + "\",\n"
                    + "    \"mode\": \"" + mode + "\",\n"
                    + "    \"message\": \"" + cadena + "\",\n"
                    + "    \"encode\": \"" + encode + "\"\n"
                    + "}";
            System.out.println("JSON " + json);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("host", "sandbox.tesseract.mx");
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

        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public String encryptMessageHASH(String cadena) {

        String result = "";
        try {

            //Url
            URL url = new URL(urlhash);

            String json = "{ \"message\": \"" + cadena + "\", \"algorithm\": \"SHA512\" }";

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

        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }

}
