package pe.edu.pucp.softven.bo;

import pe.edu.pucp.softven.dao.ProductoDAO;
import pe.edu.pucp.softven.daoImp.ProductoDAOImpl;
import pe.edu.pucp.softven.model.ProductosDTO;

public class ProductoBO {

    private ProductoDAO productoDAO;
    
    public ProductoBO(){
        this.productoDAO = new ProductoDAOImpl();
    }
    
    public Integer insertar(String nombre, String descripcion, Double precio){
        ProductosDTO productosDTO = new ProductosDTO();
        productosDTO.setNombre(nombre);
        productosDTO.setDescripcion(descripcion);
        productosDTO.setPrecio(precio);
        
        return this.productoDAO.insertar(productosDTO);
    }
    
}
