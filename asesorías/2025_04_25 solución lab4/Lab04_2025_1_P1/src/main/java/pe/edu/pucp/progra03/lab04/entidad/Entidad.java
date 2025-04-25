package pe.edu.pucp.progra03.lab04.entidad;

import java.util.ArrayList;

public class Entidad {

    private String nombre;
    protected ArrayList<Columna> listaColumnas;
    private ArrayList<Fila> listaFilas;

    public Entidad(String nombre, ArrayList<Columna> listaColumnas) {
        this.nombre = nombre;
        this.listaColumnas = new ArrayList<Columna>();
        for (Columna columna : listaColumnas) {
            Columna columna_copia = new Columna(columna);
            this.listaColumnas.add(columna);
        }
        this.listaFilas = new ArrayList<Fila>();
    }

    public void insertarFila() {
        this.listaFilas.add(new Fila());
    }

    public void agregarEnteroEnFila(Integer entero) {
        Fila fila = this.listaFilas.getLast();
        fila.insertarEntero(entero);
    }

    public void agregarCadenaEnFila(String cadena) {
        Fila fila = this.listaFilas.getLast();
        fila.insertarCadena(cadena);
    }

    @Override
    public String toString() {
        String cadena = "";
        Integer cantidad_columnas = this.listaColumnas.size();
        for (int i = 0; i < cantidad_columnas; i++) {
            cadena = cadena.concat(this.listaColumnas.get(i).getNombre());
            if (i != cantidad_columnas - 1) {
                cadena = cadena.concat(",");
            }
        }
        cadena = cadena.concat("\n");
        for (Fila fila : this.listaFilas) {
            for (int j = 0; j < cantidad_columnas; ++j) {
                Object dato = fila.obtenerDato(j);
                cadena = cadena.concat(dato.toString());
                if (j != cantidad_columnas - 1) {
                    cadena = cadena.concat(",");
                }
            }
            cadena = cadena.concat("\n");
        };
        return cadena;
    }

}
