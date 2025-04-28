package pe.edu.pucp.softven.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softven.dao.ReporteVendedorDAO;
import pe.edu.pucp.softven.daoImp.util.ReporteVendedoresParametros;
import pe.edu.pucp.softven.daoImp.util.ReporteVendedoresParametrosBuilder;
import pe.edu.pucp.softven.model.ReportesVendedoresDTO;

public class ReporteVendedoresDAOImpl extends DAOImplBase implements ReporteVendedorDAO {

    public ReporteVendedoresDAOImpl() {
        super("VEN_REPORTES_VENDEDORES");
    }

    @Override
    protected void configurarListaDeColumnas() {
        //no se configura porque se invocará a procedimientos almacenados
        //y SQL con varios joins
    }

    @Override
    public void insertarDatosParaPrueba() {
        String sql = "{CALL SP_VEN_INSERTAR_DATOS_PRUEBA_REPORTE_VENDEDORES()}";
        Boolean conTransaccion = true;
        this.ejecutarProcedimientoAlmacenado(sql, conTransaccion);
    }

    @Override
    public void eliminarDatosParaPrueba() {
        String sql = "{CALL SP_VEN_ELIMINAR_DATOS_PRUEBA_REPORTE_VENDEDORES()}";
        Boolean conTransaccion = true;
        this.ejecutarProcedimientoAlmacenado(sql, conTransaccion);
    }

    @Override
    public void generarReporteVendedores(Integer año, Integer mes) {
        Object parametros = new ReporteVendedoresParametrosBuilder().
                conAño(año).
                conMes(mes).
                BuilReporteVendedoresParametros();
        String sql = "{CALL SP_VEN_GENERAR_REPORTE_VENDEDORES(?, ?)}";
        Boolean conTransaccion = true;
        this.ejecutarProcedimientoAlmacenado(sql, this::incluirValorDelParametroParaGenerarReporteDeVendedores, parametros, conTransaccion);
    }

    private void incluirValorDelParametroParaGenerarReporteDeVendedores(Object objetoParametros) {
        ReporteVendedoresParametros parametros = (ReporteVendedoresParametros) objetoParametros;
        try {
            this.statement.setInt(1, parametros.getAño());
            this.statement.setInt(2, parametros.getMes());
        } catch (SQLException ex) {
            Logger.getLogger(ReporteVendedoresDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<ReportesVendedoresDTO> listarPorPeriodo(Integer año, Integer mes, Integer tiendaId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
