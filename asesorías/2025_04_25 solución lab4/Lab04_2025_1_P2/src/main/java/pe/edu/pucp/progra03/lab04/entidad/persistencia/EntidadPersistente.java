package pe.edu.pucp.progra03.lab04.entidad.persistencia;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.progra03.lab04.entidad.Columna;
import pe.edu.pucp.progra03.lab04.entidad.Entidad;
import pe.edu.pucp.progra03.lab04.entidad.TipoDeDato;

public class EntidadPersistente extends Entidad {

    private String nombreArchivo;

    public EntidadPersistente(String nombre, ArrayList<Columna> listaColumnas) {
        super(nombre, listaColumnas);
        this.nombreArchivo = nombre.concat(".csv");
    }

    public void salvarEnArchivo() {
        try {
            FileWriter writer = new FileWriter(this.nombreArchivo);
            String contenido = this.toString();
            writer.write(contenido);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(EntidadPersistente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarDesdeArchivo() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.nombreArchivo));
            String linea;
            Boolean esta_leyendo_cabecera = true;
            while ((linea = br.readLine()) != null) {
                if (!esta_leyendo_cabecera) {
                    //linea == 19941146,Héctor Andrés,Melgar,Sasieta
                    //datos[0] =  19941146
                    //datos[1] =  Héctor Andrés
                    //datos[2] =  Melgar
                    //datos[3] =  Sasieta
                    String[] datos = linea.split(",");
                    this.insertarFila();
                    Integer cantidad_columnas = this.listaColumnas.size();
                    for (int i = 0; i < cantidad_columnas; i++) {
                        String dato = datos[i];
                        if (this.listaColumnas.get(i).getTipoDeDato() == TipoDeDato.CADENA) {
                            this.agregarCadenaEnFila(dato);
                        } else {
                            Integer dato_entero = Integer.parseInt(dato);
                            this.agregarEnteroEnFila(dato_entero);
                        }
                    }
                } else;
                esta_leyendo_cabecera = false;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EntidadPersistente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EntidadPersistente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
