package pe.edu.pucp.softven.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softven.dao.ProductoDAO;
import pe.edu.pucp.softven.daoImp.util.Columna;
import pe.edu.pucp.softven.db.DBManager;
import pe.edu.pucp.softven.model.ProductosDTO;

public class ProductoDAOImpl extends DAOImplBase implements ProductoDAO {

    private ProductosDTO producto;
    
    public ProductoDAOImpl() {
        super("VEN_PRODUCTOS");
        this.producto = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("PRODUCTO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
        this.listaColumnas.add(new Columna("PRECIO", false, false));
    }
    
    @Override
    protected void incluirValorDeParametrosParaInsercion() {
        try {
            this.statement.setString(1, this.producto.getNombre());
            this.statement.setString(2, this.producto.getDescripcion());
            this.statement.setDouble(3, this.producto.getPrecio());
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void incluirValorDeParametrosModificacion() {
        throw new UnsupportedOperationException("Método no implementado en la clase derivada..");
    }

    @Override
    protected void incluirValorDeParametrosEliminacion() {
        throw new UnsupportedOperationException("Método no implementado en la clase derivada..");
    }

    @Override
    public Integer insertar(ProductosDTO producto) {
        this.producto = producto;
        return super.insertar();
    }

    @Override
    public ProductosDTO obtenerPorId(Integer productoId) {
        ProductosDTO producto = null;
        try {
            this.conexion = DBManager.getInstance().getConnection();
            String sql = "SELECT PRODUCTO_ID, NOMBRE, DESCRIPCION, PRECIO FROM VEN_PRODUCTOS WHERE PRODUCTO_ID = ?";
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setInt(1, productoId);
            this.resultSet = this.statement.executeQuery();
            if (this.resultSet.next()) {
                producto = new ProductosDTO();
                producto.setProductoId(this.resultSet.getInt("PRODUCTO_ID"));
                producto.setNombre(this.resultSet.getString("NOMBRE"));
                producto.setDescripcion(this.resultSet.getString("DESCRIPCION"));
                producto.setPrecio(this.resultSet.getDouble("PRECIO"));
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar obtenerPorId - " + ex);
        } finally {
            try {
                if (this.conexion != null) {
                    this.conexion.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
        return producto;
    }

    @Override
    public ArrayList<ProductosDTO> listarTodos() {
        ArrayList<ProductosDTO> listaProductos = new ArrayList<>();
        try {
            this.conexion = DBManager.getInstance().getConnection();
            String sql = "SELECT PRODUCTO_ID, NOMBRE, DESCRIPCION, PRECIO FROM VEN_PRODUCTOS";
            this.statement = this.conexion.prepareCall(sql);
            this.resultSet = this.statement.executeQuery();
            while (this.resultSet.next()) {
                ProductosDTO producto = new ProductosDTO();
                producto.setProductoId(this.resultSet.getInt("PRODUCTO_ID"));
                producto.setNombre(this.resultSet.getString("NOMBRE"));
                producto.setDescripcion(this.resultSet.getString("DESCRIPCION"));
                producto.setPrecio(this.resultSet.getDouble("PRECIO"));
                listaProductos.add(producto);
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar listarTodos - " + ex);
        } finally {
            try {
                if (this.conexion != null) {
                    this.conexion.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
        return listaProductos;
    }

    @Override
    public Integer modificar(ProductosDTO producto) {
        int resultado = 0;
        try {
            this.conexion = DBManager.getInstance().getConnection();
            this.conexion.setAutoCommit(false);
            String sql = this.generarSQLParaModificacion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setString(1, producto.getNombre());
            this.statement.setString(2, producto.getDescripcion());
            this.statement.setDouble(3, producto.getPrecio());
            this.statement.setInt(4, producto.getProductoId());

            resultado = this.statement.executeUpdate();
            this.conexion.commit();
        } catch (SQLException ex) {
            System.err.println("Error al intentar modificar - " + ex);
            try {
                if (this.conexion != null) {
                    this.conexion.rollback();
                }
            } catch (SQLException ex1) {
                System.err.println("Error al hacer rollback - " + ex1);
            }
        } finally {
            try {
                if (this.conexion != null) {
                    this.conexion.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
        return resultado;
    }

    @Override
    public Integer eliminar(ProductosDTO producto) {
        int resultado = 0;
        try {
            this.conexion = DBManager.getInstance().getConnection();
            this.conexion.setAutoCommit(false);
            String sql = this.generarSQLParaEliminacion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setInt(1, producto.getProductoId());
            resultado = this.statement.executeUpdate();
            this.conexion.commit();
        } catch (SQLException ex) {
            System.err.println("Error al intentar eliminar - " + ex);
            try {
                if (this.conexion != null) {
                    this.conexion.rollback();
                }
            } catch (SQLException ex1) {
                System.err.println("Error al hacer rollback - " + ex1);
            }
        } finally {
            try {
                if (this.conexion != null) {
                    this.conexion.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
        return resultado;
    }

}
