package pe.edu.pucp.softinv.bo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlmacenBOTest {
    
    private AlmacenBO almacenBO;
    
    public AlmacenBOTest() {
        this.almacenBO = new AlmacenBO();
    }

    @Test
    public void testInsertar() {
        System.out.println("insertar");
        Integer resultado = this.almacenBO.insertar("Mi almacén central", Boolean.TRUE);
        assertTrue(resultado >0);
        
        resultado = this.almacenBO.insertar("Mi segundo almacén", Boolean.FALSE);
        assertTrue(resultado >0);
        
        resultado = this.almacenBO.insertar("Mi tercer almacén", Boolean.FALSE);
        assertTrue(resultado >0);
    }
    
}
