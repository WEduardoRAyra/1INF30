package pe.edu.pucp.softinv.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softinv.dao.ReporteStockDAO;
import pe.edu.pucp.softinv.daoImp.util.ReporteStockParametros;
import pe.edu.pucp.softinv.daoImp.util.ReporteStockParametrosBuilder;
import pe.edu.pucp.softinv.model.ReportesStocksDTO;

public class ReporteStockDAOImpl extends DAOImplBase implements ReporteStockDAO {

    public ReporteStockDAOImpl() {
        super("INV_REPORTES_STOCKS");
    }

    @Override
    protected void configurarListaDeColumnas() {
        //no se incluirán columnas pues este DAO no corresponde
        //a un CRUD típico
    }

    @Override
    public void insertarDatosDePrueba() {
        String sql = "{call SP_INV_INSERTAR_DATOS_PRUEBA_REPORTE_STOCK()}";
        Boolean conTransaccion = true;
        this.ejecutarProcedimientoAlmacenado(sql, conTransaccion);
    }

    @Override
    public void eliminarDatosDePrueba() {
        String sql = "{call SP_INV_ELIMINAR_DATOS_PRUEBA_REPORTE_STOCK()}";
        Boolean conTransaccion = true;
        this.ejecutarProcedimientoAlmacenado(sql, conTransaccion);
    }

    @Override
    public void generarReporteStock(Integer año, Integer mes) {
        Object parametros = new ReporteStockParametrosBuilder().
                conAño(año).
                conMes(mes).
                BuilReporteStockParametros();
        String sql = "{call SP_INV_GENERAR_REPORTE_STOCK(?, ?)}";
        Boolean conTransaccion = true;
        this.ejecutarProcedimientoAlmacenado(sql, this::incluirValorDeParametrosParaGenerarReporteStock, parametros, conTransaccion);
    }
    
    private void incluirValorDeParametrosParaGenerarReporteStock(Object objetoParametros){
        ReporteStockParametros parametros = (ReporteStockParametros)objetoParametros;
        try {
            this.statement.setInt(1, parametros.getAño());
            this.statement.setInt(2, parametros.getMes());
        } catch (SQLException ex) {
            Logger.getLogger(ReporteStockDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<ReportesStocksDTO> listarPorPeriodo(Integer año, Integer mes, Integer almacenId, Integer productoId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
