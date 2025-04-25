package pe.edu.pucp.softinv.dao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softinv.daoImp.ReporteStockDAOImpl;

public class ReporteStockDAOTest {
    
    private ReporteStockDAO reporteStockDAO;
    
    public ReporteStockDAOTest() {
        this.reporteStockDAO = new ReporteStockDAOImpl();
    }

    @Test
    public void testListarPorPeriodo() {
        System.out.println("listarPorPeriodo");
        this.reporteStockDAO.insertarDatosDePrueba();
        this.reporteStockDAO.generarReporteStock(2025, 1);
        this.reporteStockDAO.generarReporteStock(2025, 2);
        this.reporteStockDAO.generarReporteStock(2025, 3);
        
        this.reporteStockDAO.eliminarDatosDePrueba();
    }
    
}
