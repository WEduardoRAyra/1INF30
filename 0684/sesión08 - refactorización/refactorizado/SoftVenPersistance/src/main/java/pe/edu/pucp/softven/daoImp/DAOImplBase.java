package pe.edu.pucp.softven.daoImp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pe.edu.pucp.softven.daoImp.util.Columna;
import pe.edu.pucp.softven.daoImp.util.Tipo_Operacion;
import pe.edu.pucp.softven.db.DBManager;

public abstract class DAOImplBase {

    protected String nombre_tabla;
    protected ArrayList<Columna> listaColumnas;
    protected Connection conexion;
    protected CallableStatement statement;
    protected ResultSet resultSet;

    public DAOImplBase(String nombre_tabla) {
        this.nombre_tabla = nombre_tabla;
        this.incluirListaDeColumnas();
    }

    private void incluirListaDeColumnas() {
        this.listaColumnas = new ArrayList<>();
        this.configurarListaDeColumnas();
    }

    protected abstract void configurarListaDeColumnas();

    public void abrirConexion() {
        this.conexion = DBManager.getInstance().getConnection();
    }

    public void cerrarConexion() throws SQLException {
        if (this.conexion != null) {
            this.conexion.close();
        }
    }

    public void iniciarTransaccion() throws SQLException {
        this.abrirConexion();
        this.conexion.setAutoCommit(false);
    }

    public void comitarTransaccion() throws SQLException {
        this.conexion.commit();
    }

    public void rollbackTransaccion() throws SQLException {
        if (this.conexion != null) {
            this.conexion.rollback();
        }
    }

    public void colocarSQLEnSentencia(String sql) throws SQLException {
        this.statement = this.conexion.prepareCall(sql);
    }

    public void ejecutarModificacionesEnBD() throws SQLException {
        this.statement.executeUpdate();
    }

    protected Integer insertar() {
        return this.ejecuta_DML(Tipo_Operacion.INSERTAR);
    }

    protected Integer modificar() {
        return this.ejecuta_DML(Tipo_Operacion.MODIFICAR);
    }

    protected Integer eliminar() {
        return this.ejecuta_DML(Tipo_Operacion.ELIMINAR);
    }

    public Integer ejecuta_DML(Tipo_Operacion tipo_Operacion) {
        int resultado = 0;
        try {
            this.iniciarTransaccion();
            String sql = "";
            switch (tipo_Operacion) {
                case Tipo_Operacion.INSERTAR ->
                    sql = this.generarSQLParaInsercion();
                case Tipo_Operacion.MODIFICAR ->
                    sql = this.generarSQLParaModificacion();
                case Tipo_Operacion.ELIMINAR ->
                    sql = this.generarSQLParaEliminacion();
            }
            this.colocarSQLEnSentencia(sql);
            switch (tipo_Operacion) {
                case Tipo_Operacion.INSERTAR ->
                    this.incluirValorDeParametrosParaInsercion();
                case Tipo_Operacion.MODIFICAR ->
                    this.incluirValorDeParametrosModificacion();
                case Tipo_Operacion.ELIMINAR ->
                    this.incluirValorDeParametrosEliminacion();
            }
            this.ejecutarModificacionesEnBD();
            if (tipo_Operacion == Tipo_Operacion.INSERTAR) {
                resultado = this.retornarUltimoAutoGenerado();
            }
            this.comitarTransaccion();
        } catch (SQLException ex) {
            System.err.println("Error al intentar insertar - " + ex);
            try {
                this.rollbackTransaccion();
            } catch (SQLException ex1) {
                System.err.println("Error al hacer rollback - " + ex1);
            }
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
        return resultado;
    }

    protected String generarSQLParaInsercion() {
        String sql = "INSERT INTO ";
        sql = sql.concat(this.nombre_tabla);
        String sql_columnas = "";
        String sql_parametros = "";
        for (Columna columna : this.listaColumnas) {
            if (!columna.getEsAutoGenerado()) {
                if (!sql_columnas.isBlank()) {
                    sql_columnas = sql_columnas.concat(", ");
                    sql_parametros = sql_parametros.concat(", ");
                }
                sql_columnas = sql_columnas.concat(columna.getNombre());
                sql_parametros = sql_parametros.concat("?");
            }
        }
        sql = sql.concat("(");
        sql = sql.concat(sql_columnas);
        sql = sql.concat(") VALUES(");
        sql = sql.concat(sql_parametros);
        sql = sql.concat(")");
        return sql;
    }

    protected String generarSQLParaModificacion() {
        //sentencia SQL a generar es similar a 
        //UPDATE INV_ALMACENES SET NOMBRE=?, ALMACEN_CENTRAL=? WHERE ALMACEN_ID=?
        String sql = "UPDATE ";
        sql = sql.concat(this.nombre_tabla);
        sql = sql.concat(" SET ");
        String sql_columnas = "";
        String sql_predicado = "";
        for (Columna columna : this.listaColumnas) {
            if (columna.getEsLlavePrimaria()) {
                if (!sql_predicado.isBlank()) {
                    sql_predicado = sql_predicado.concat(", ");
                }
                sql_predicado = sql_predicado.concat(columna.getNombre());
                sql_predicado = sql_predicado.concat("=?");
            } else {
                if (!sql_columnas.isBlank()) {
                    sql_columnas = sql_columnas.concat(", ");
                }
                sql_columnas = sql_columnas.concat(columna.getNombre());
                sql_columnas = sql_columnas.concat("=?");
            }
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(" WHERE ");
        sql = sql.concat(sql_predicado);
        return sql;
    }

    protected String generarSQLParaEliminacion() {
        //sentencia SQL a generar es similar a 
        //DELETE FROM INV_ALMACENES WHERE ALMACEN_ID=?
        String sql = "DELETE FROM ";
        sql = sql.concat(this.nombre_tabla);
        sql = sql.concat(" WHERE ");
        String sql_predicado = "";
        for (Columna columna : this.listaColumnas) {
            if (columna.getEsLlavePrimaria()) {
                if (!sql_predicado.isBlank()) {
                    sql_predicado = sql_predicado.concat(", ");
                }
                sql_predicado = sql_predicado.concat(columna.getNombre());
                sql_predicado = sql_predicado.concat("=?");
            }
        }
        sql = sql.concat(sql_predicado);
        return sql;
    }

    public Integer retornarUltimoAutoGenerado() {
        Integer resultado = null;
        try {
            String sql = "select @@last_insert_id as id";
            this.statement = this.conexion.prepareCall(sql);
            this.resultSet = this.statement.executeQuery();
            if (this.resultSet.next()) {
                resultado = this.resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar retornarUltimoAutoGenerado - " + ex);
        }
        return resultado;
    }

    protected void incluirValorDeParametrosParaInsercion() {
        throw new UnsupportedOperationException("Método no implementado en la clase derivada.");
    }

    protected void incluirValorDeParametrosModificacion() {
        throw new UnsupportedOperationException("Método no implementado en la clase derivada..");
    }

    protected void incluirValorDeParametrosEliminacion() {
        throw new UnsupportedOperationException("Método no implementado en la clase derivada..");
    }
}
