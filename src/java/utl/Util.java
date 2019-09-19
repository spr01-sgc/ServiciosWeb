/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utl;

/**
 *
 * @author JASMIN-SOMA
 */
public class Util {

    public Util() {
    }

    static public String creaObjetoPS(Object[] obj) {
        String cadena = "";

        String palabra = ""; //Cada palabra que contendra el arreglo
        String nPalabra = ""; //La nueva palabra que se agregara
        char letra = 0;
        cadena += "'{";
        for (int j = 0; j < obj.length; j++) {
            if (obj[j] == null) {
                obj[j] = "";
            } else {
                palabra = obj[j].toString().trim();
            }
            //Itera las palabras para saber si contiene un ' y modificar para que no mande error la BD
            nPalabra = "";
            for (int z = 0; z < palabra.length(); z++) {
                letra = palabra.charAt(z);
                if (letra == '\'') {
                    nPalabra += letra + "'";
                } else if (letra == '\"') {
                    nPalabra += " ";
                } else {
                    nPalabra += letra;
                }
            }
            cadena += "\"" + nPalabra + "\"";

            if (j != obj.length - 1) {
                cadena += ",\n";
            }
        }
        cadena += "}'";
        return cadena;
    }
}
