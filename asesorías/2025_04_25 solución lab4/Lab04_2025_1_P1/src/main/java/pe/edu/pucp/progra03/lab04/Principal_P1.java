package pe.edu.pucp.progra03.lab04;

import java.util.ArrayList;
import pe.edu.pucp.progra03.lab04.entidad.Columna;
import pe.edu.pucp.progra03.lab04.entidad.Entidad;
import pe.edu.pucp.progra03.lab04.entidad.TipoDeDato;

public class Principal_P1 {

    public static void main(String[] args) {
        ArrayList<Columna> listaColumnas = new ArrayList<>();
        listaColumnas.add(new Columna("id", TipoDeDato.NUMERO));
        listaColumnas.add(new Columna("nombre", TipoDeDato.CADENA));
        listaColumnas.add(new Columna("paterno", TipoDeDato.CADENA));
        listaColumnas.add(new Columna("materno", TipoDeDato.CADENA));
        
        Entidad entidad = new Entidad("Alumno", listaColumnas);
        entidad.insertarFila();
        entidad.agregarEnteroEnFila(19941146);
        entidad.agregarCadenaEnFila("Héctor Andrés");
        entidad.agregarCadenaEnFila("Melgar");
        entidad.agregarCadenaEnFila("Sasieta");
        entidad.insertarFila();
        entidad.agregarEnteroEnFila(20112728);
        entidad.agregarCadenaEnFila("Freddy Alberto");
        entidad.agregarCadenaEnFila("Paz");
        entidad.agregarCadenaEnFila("Espinoza");
        System.out.println(entidad);
    }
}
