package pe.edu.pucp.progra03.lab04.entidad;

import java.util.ArrayList;

public class Fila {

    private ArrayList<Object> listaDatos;

    public Fila() {
        this.listaDatos = new ArrayList<>();
    }

    public void insertarEntero(Integer dato) {
        this.listaDatos.add(dato);
    }

    public void insertarCadena(String dato) {
        this.listaDatos.add(dato);
    }
    
    public Object obtenerDato(Integer indice){
        return this.listaDatos.get(indice);
    }
}
