package pe.edu.pucp.softven.dao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softven.daoImp.ReporteVendedoresDAOImpl;

public class ReporteVendedorDAOTest {
    
    private ReporteVendedorDAO reporteVendedorDAO;
    
    public ReporteVendedorDAOTest() {
        this.reporteVendedorDAO = new ReporteVendedoresDAOImpl();
    }
    @Test
    public void testListarPorPeriodo() {
        System.out.println("listarPorPeriodo");
        this.reporteVendedorDAO.insertarDatosParaPrueba();
        this.reporteVendedorDAO.generarReporteVendedores(2025, 1);
        this.reporteVendedorDAO.generarReporteVendedores(2025, 2);
        this.reporteVendedorDAO.generarReporteVendedores(2025, 3);
        //this.reporteVendedorDAO.eliminarDatosParaPrueba();
    }

    
}
