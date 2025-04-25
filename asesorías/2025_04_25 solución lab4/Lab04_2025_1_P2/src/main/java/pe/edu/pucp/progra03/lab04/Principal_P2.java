package pe.edu.pucp.progra03.lab04;

import java.util.ArrayList;
import pe.edu.pucp.progra03.lab04.entidad.Columna;
import pe.edu.pucp.progra03.lab04.entidad.TipoDeDato;
import pe.edu.pucp.progra03.lab04.entidad.persistencia.EntidadPersistente;

public class Principal_P2 {

    public static void main(String[] args) {
        ArrayList<Columna> listaColumnas = new ArrayList<>();
        listaColumnas.add(new Columna("id", TipoDeDato.NUMERO));
        listaColumnas.add(new Columna("nombre", TipoDeDato.CADENA));
        listaColumnas.add(new Columna("paterno", TipoDeDato.CADENA));
        listaColumnas.add(new Columna("materno", TipoDeDato.CADENA));
//        
//        EntidadPersistente entidad = new EntidadPersistente("Alumno", listaColumnas);
//        entidad.insertarFila();
//        entidad.agregarEnteroEnFila(19941146);
//        entidad.agregarCadenaEnFila("Héctor Andrés");
//        entidad.agregarCadenaEnFila("Melgar");
//        entidad.agregarCadenaEnFila("Sasieta");
//        entidad.insertarFila();
//        entidad.agregarEnteroEnFila(20112728);
//        entidad.agregarCadenaEnFila("Freddy Alberto");
//        entidad.agregarCadenaEnFila("Paz");
//        entidad.agregarCadenaEnFila("Espinoza");
//        entidad.salvarEnArchivo();
        
        EntidadPersistente entidad2 = new EntidadPersistente("Alumno", listaColumnas);
        entidad2.cargarDesdeArchivo();
        System.out.println(entidad2);
    }
}
