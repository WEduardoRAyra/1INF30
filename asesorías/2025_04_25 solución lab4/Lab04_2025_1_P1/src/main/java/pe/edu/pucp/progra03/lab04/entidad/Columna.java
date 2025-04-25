package pe.edu.pucp.progra03.lab04.entidad;

public class Columna {

    private String nombre;
    private TipoDeDato tipoDeDato;
        
    public Columna(){
        this.nombre = null;
        this.tipoDeDato = null;
    }
    
    public Columna(String nombre, TipoDeDato tipoDeDato){
        this.nombre = nombre;
        this.tipoDeDato = tipoDeDato;
    }
    
    public Columna(Columna columna){
        this.nombre = columna.nombre;
        this.tipoDeDato = columna.tipoDeDato;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the tipoDeDato
     */
    public TipoDeDato getTipoDeDato() {
        return tipoDeDato;
    }

    /**
     * @param tipoDeDato the tipoDeDato to set
     */
    public void setTipoDeDato(TipoDeDato tipoDeDato) {
        this.tipoDeDato = tipoDeDato;
    }
}
