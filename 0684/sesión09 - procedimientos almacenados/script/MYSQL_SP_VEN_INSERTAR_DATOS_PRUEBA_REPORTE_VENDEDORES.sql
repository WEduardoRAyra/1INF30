DELIMITER $$
DROP PROCEDURE IF EXISTS SP_VEN_INSERTAR_DATOS_PRUEBA_REPORTE_VENDEDORES$$

CREATE PROCEDURE `SP_VEN_INSERTAR_DATOS_PRUEBA_REPORTE_VENDEDORES` ()
BEGIN
	DECLARE v_producto_id01 INT;
    DECLARE v_producto_id02 INT;
    DECLARE v_producto_id03 INT;
    DECLARE v_producto_id04 INT;
    DECLARE v_producto_id05 INT;
    DECLARE v_producto_id06 INT;
    DECLARE v_producto_id07 INT;
    DECLARE v_producto_id08 INT;
    DECLARE v_producto_id09 INT;
    DECLARE v_producto_id10 INT;
    DECLARE v_precio_producto01 DECIMAL(10,3);
    DECLARE v_precio_producto02 DECIMAL(10,3);
    DECLARE v_precio_producto03 DECIMAL(10,3);
    DECLARE v_precio_producto04 DECIMAL(10,3);
    DECLARE v_precio_producto05 DECIMAL(10,3);
    DECLARE v_precio_producto06 DECIMAL(10,3);
    DECLARE v_precio_producto07 DECIMAL(10,3);
    DECLARE v_precio_producto08 DECIMAL(10,3);
    DECLARE v_precio_producto09 DECIMAL(10,3);
    DECLARE v_precio_producto10 DECIMAL(10,3);
    DECLARE v_vendedor_id01 INT;
    DECLARE v_vendedor_id02 INT;
    DECLARE v_vendedor_id03 INT;
    DECLARE v_vendedor_id04 INT;
    DECLARE v_vendedor_id05 INT;
    DECLARE v_vendedor_id06 INT;
    DECLARE v_vendedor_id07 INT;
    DECLARE v_vendedor_id08 INT;
    DECLARE v_vendedor_id09 INT;
    DECLARE v_vendedor_id10 INT;
    DECLARE v_venta_id INT;
    DECLARE v_descuento_tienda1 DECIMAL(10,3);
    DECLARE v_descuento_tienda2 DECIMAL(10,3);
    DECLARE v_descuento_tienda3 DECIMAL(10,3);
    DECLARE v_item  INT; 
    DECLARE v_cantidad  INT; 
    DECLARE v_precio  DECIMAL(10,3); 
    DECLARE v_descuento  DECIMAL(10,3);
    DECLARE v_IGV_tasa  DECIMAL(10,3);
	DECLARE v_IGV_valor  DECIMAL(10,3);
    DECLARE v_subtotal  DECIMAL(10,3);
    DECLARE v_total  DECIMAL(10,3);
    
	DELETE FROM VEN_REPORTES_VENDEDORES;
	DELETE FROM VEN_PERIODOS;
    DELETE FROM VEN_VENTAS_DETALLES;
	DELETE FROM VEN_VENTAS;	
	DELETE FROM VEN_TIPOS_DOCUMENTOS;
	DELETE FROM VEN_PRODUCTOS;
	DELETE FROM VEN_VENDEDORES;
	DELETE FROM VEN_TIENDAS;
    
    INSERT INTO VEN_TIPOS_DOCUMENTOS (TIPO_DOCUMENTO_ID, NOMBRE) VALUES (1, "DNI");
    INSERT INTO VEN_TIPOS_DOCUMENTOS (TIPO_DOCUMENTO_ID, NOMBRE) VALUES (2, "DNI");
    
    SET v_descuento_tienda1 = 15.00;
    SET v_descuento_tienda2 = 12.50;
    SET v_descuento_tienda3 = 08.50;
    
    INSERT INTO VEN_TIENDAS (TIENDA_ID, NOMBRE, DIRECCION, TIENDA_PRINCIPAL, COMISION) VALUES (1, "Tiene Chacarilla - Caminos del Inca", "Av. Caminos del Inca 508", 1, v_descuento_tienda1);
    INSERT INTO VEN_TIENDAS (TIENDA_ID, NOMBRE, DIRECCION, TIENDA_PRINCIPAL, COMISION) VALUES (2, "Tiene Chacarilla - Primavera", "Av. Primavera 1567", 0, v_descuento_tienda2);
    INSERT INTO VEN_TIENDAS (TIENDA_ID, NOMBRE, DIRECCION, TIENDA_PRINCIPAL, COMISION) VALUES (3, "Tiene San Borja - Javier Prado", "Av. Javier Prado 1234", 0, v_descuento_tienda3);

    INSERT INTO VEN_VENDEDORES (TIENDA_ID, PATERNO, MATERNO, NOMBRES) VALUES (1, "García", "Rodríguez", "Santiago");
    SET v_vendedor_id01 = LAST_INSERT_ID();
    INSERT INTO VEN_VENDEDORES (TIENDA_ID, PATERNO, MATERNO, NOMBRES) VALUES (1, "Martínez", "Hernández", "Valentina");
    SET v_vendedor_id02 = LAST_INSERT_ID();
    INSERT INTO VEN_VENDEDORES (TIENDA_ID, PATERNO, MATERNO, NOMBRES) VALUES (1, "López", "González", "Mateo");
    SET v_vendedor_id03 = LAST_INSERT_ID();

    INSERT INTO VEN_VENDEDORES (TIENDA_ID, PATERNO, MATERNO, NOMBRES) VALUES (2, "Pérez", "Sánchez", "Isabella");
    SET v_vendedor_id04 = LAST_INSERT_ID();
    INSERT INTO VEN_VENDEDORES (TIENDA_ID, PATERNO, MATERNO, NOMBRES) VALUES (2, "Ramírez", "Romero", "Sebastián");
    SET v_vendedor_id05 = LAST_INSERT_ID();
    INSERT INTO VEN_VENDEDORES (TIENDA_ID, PATERNO, MATERNO, NOMBRES) VALUES (2, "Benítez", "Ramos", "Camila");
    SET v_vendedor_id06 = LAST_INSERT_ID();
    
    INSERT INTO VEN_VENDEDORES (TIENDA_ID, PATERNO, MATERNO, NOMBRES) VALUES (3, "Rey", "Molina", "Alejandro");
    SET v_vendedor_id07 = LAST_INSERT_ID();
    INSERT INTO VEN_VENDEDORES (TIENDA_ID, PATERNO, MATERNO, NOMBRES) VALUES (3, "Ruiz", "Cabrera", "Sofia");
    SET v_vendedor_id08 = LAST_INSERT_ID();
    INSERT INTO VEN_VENDEDORES (TIENDA_ID, PATERNO, MATERNO, NOMBRES) VALUES (3, "Iglesias", "Moreno", "Héctor");
    SET v_vendedor_id09 = LAST_INSERT_ID();
    INSERT INTO VEN_VENDEDORES (TIENDA_ID, PATERNO, MATERNO, NOMBRES) VALUES (3, "Méndez", "Vásquez", "Laura");
    SET v_vendedor_id10 = LAST_INSERT_ID();
    
    SET v_precio_producto01 = 8999;
    SET v_precio_producto05 = 2620;
    
	INSERT INTO VEN_PRODUCTOS (NOMBRE, DESCRIPCION, PRECIO) VALUES ("MacBook Pro 16", "Apple - MK183LL/A", v_precio_producto01);
    SET v_producto_id01 = LAST_INSERT_ID();
    INSERT INTO VEN_PRODUCTOS (NOMBRE, DESCRIPCION, PRECIO) VALUES ("ThinkPad X1 Carbon", "Lenovo - 21HM000BUS", 8599);
    SET v_producto_id02 = LAST_INSERT_ID();
    INSERT INTO VEN_PRODUCTOS (NOMBRE, DESCRIPCION, PRECIO) VALUES ("XPS 15", "Dell - 9530-7523SLV", 6929);
    SET v_producto_id03 = LAST_INSERT_ID();
    INSERT INTO VEN_PRODUCTOS (NOMBRE, DESCRIPCION, PRECIO) VALUES ("Galaxy S24 Ultra", "Samsung - SM-S928BZKJ", 3899);
    SET v_producto_id04 = LAST_INSERT_ID();
    INSERT INTO VEN_PRODUCTOS (NOMBRE, DESCRIPCION, PRECIO) VALUES ("iPhone 15 Pro Max", "Apple - A3102", 3799);
    SET v_producto_id05 = LAST_INSERT_ID();
    INSERT INTO VEN_PRODUCTOS (NOMBRE, DESCRIPCION, PRECIO) VALUES ("Pixel 8 Pro", "Google - G1HZ5", v_precio_producto05);    
    SET v_producto_id06 = LAST_INSERT_ID();
    INSERT INTO VEN_PRODUCTOS (NOMBRE, DESCRIPCION, PRECIO) VALUES ('OLED C3 55"', "LG - OLED55C3PUA", 3799);
    SET v_producto_id07 = LAST_INSERT_ID();
    INSERT INTO VEN_PRODUCTOS (NOMBRE, DESCRIPCION, PRECIO) VALUES ('BRAVIA XR A80L 65"', "Sony - XR65A80L", 8999);
    SET v_producto_id08 = LAST_INSERT_ID();
    INSERT INTO VEN_PRODUCTOS (NOMBRE, DESCRIPCION, PRECIO) VALUES ('The Frame 55"', "Samsung - QN55LS03BAFXZA", 2999);
    SET v_producto_id09 = LAST_INSERT_ID();
    INSERT INTO VEN_PRODUCTOS (NOMBRE, DESCRIPCION, PRECIO) VALUES ('P-Series Quantum 75"', "Vizio - P75Q9-J01", 2899);
    SET v_producto_id10 = LAST_INSERT_ID();

    SET v_IGV_tasa = 0.18;
    #VENTAS EN TIENDA 1 (VENDEDORES 1, 2, 3)
    SET v_total = 0;
    INSERT INTO VEN_VENTAS (TIPO_DOCUMENTO_ID, TIENDA_ID, VENDEDOR_ID, FECHA_VENTA, FECHA_CREACION, NUM_DOC_CLIENTE, NOMBRE_CLIENTE, TOTAL_VENTA, ESTADO) VALUES (1, 1, v_vendedor_id01, STR_TO_DATE('2025-01-05 11:33:00','%Y-%m-%d %H:%i:%s'), NOW(), "DNI", "CLIENTE", 00.00, 'ABIERTA');
    SET v_venta_id = LAST_INSERT_ID();
    
    SET v_item = 1;
    SET v_cantidad = 1;
    SET v_precio = v_precio_producto01;
    SET v_descuento = v_precio*v_descuento_tienda1/100;
    SET v_IGV_valor = (v_precio - v_descuento)*v_IGV_tasa;
    SET v_subtotal = v_precio - v_descuento + v_IGV_valor;
    SET v_total = v_total + v_subtotal;
    INSERT INTO VEN_VENTAS_DETALLES(VENTA_ID, ITEM, PRODUCTO_ID, CANTIDAD, PRECIO_VENTA, DESCUENTO, IGV, SUBTOTAL) VALUES (v_venta_id, v_item, v_producto_id01, v_cantidad, v_precio, v_descuento, v_IGV_valor, v_subtotal);
    
    SET v_item = 2;
    SET v_cantidad = 1;
    SET v_precio = v_precio_producto05;
    SET v_descuento = v_precio*v_descuento_tienda1/100;
    SET v_IGV_valor = (v_precio - v_descuento)*v_IGV_tasa;
    SET v_subtotal = v_precio - v_descuento + v_IGV_valor;
    SET v_total = v_total + v_subtotal;
    INSERT INTO VEN_VENTAS_DETALLES(VENTA_ID, ITEM, PRODUCTO_ID, CANTIDAD, PRECIO_VENTA, DESCUENTO, IGV, SUBTOTAL) VALUES (v_venta_id, v_item, v_producto_id05, v_cantidad, v_precio, v_descuento, v_IGV_valor, v_subtotal);
    
    UPDATE VEN_VENTAS SET TOTAL_VENTA = v_total WHERE VENTA_ID = v_venta_id;
    
    #VENTAS EN TIENDA 2 (VENDEDORES 4, 5, 6)
    
    #VENTAS EN TIENDA 3 (VENDEDORES 7, 8, 9, 10)
END $$

DELIMITER ;