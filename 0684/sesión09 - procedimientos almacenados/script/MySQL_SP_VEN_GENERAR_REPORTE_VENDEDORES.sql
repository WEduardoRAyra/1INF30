DELIMITER $$

DROP PROCEDURE IF EXISTS SP_VEN_GENERAR_REPORTE_VENDEDORES$$

CREATE PROCEDURE `SP_VEN_GENERAR_REPORTE_VENDEDORES`(IN p_anho INT, IN p_mes INT)
BEGIN
	DECLARE v_anho_anterior INT;
    DECLARE v_mes_anterior INT;
    DECLARE v_tienda_id INT;
    DECLARE v_vendedor_id INT;
    DECLARE v_total_ventas DECIMAL(10,3);
    DECLARE v_total_descuentos DECIMAL(10,3);
    DECLARE CURSOR_TIENDAS CURSOR FOR SELECT TIENDA_ID FROM VEN_TIENDAS ;    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET @fin_cursor_tiendas = TRUE;    
    
	#BORRAMOS EL REPORTE ANTERIOR DEL PERIODO SI EXISTE
    DELETE FROM VEN_REPORTES_VENDEDORES WHERE ANHO = p_anho AND MES = p_mes;
    
    #INSERTAMOS EN LA TABLA PERIODO SI ES QUE NO EXISTE EL PERIODO
    IF NOT EXISTS(SELECT ANHO, MES FROM VEN_PERIODOS WHERE ANHO = p_anho AND MES = p_mes) THEN
			INSERT INTO VEN_PERIODOS(ANHO, MES, DESCRIPCION) VALUES (p_anho, p_mes, CONCAT(CONCAT(p_anho, "-"), LPAD(CAST(p_mes AS CHAR), 2, '0')));
	END IF;
    
    #OBTENEMOS EL PERIODO ANTERIOR
    SET v_anho_anterior = p_anho;
    SET v_mes_anterior = p_mes;
    IF v_mes_anterior = 1 THEN
		SET v_mes_anterior=12;
		SET v_anho_anterior=v_anho_anterior-1;
	ELSE
		SET v_mes_anterior=v_mes_anterior-1;
    END IF;
    
    OPEN CURSOR_TIENDAS;
    BUCLE_TIENDAS: LOOP
        SET @fin_cursor_tiendas = FALSE;
		FETCH CURSOR_TIENDAS INTO v_tienda_id;        
        
        IF @fin_cursor_tiendas THEN			
			LEAVE BUCLE_TIENDAS;
		END IF;
        
        BEGIN
			DECLARE CURSOR_VENDEDORES CURSOR FOR SELECT VENDEDOR_ID FROM VEN_VENDEDORES WHERE TIENDA_ID = v_tienda_id;    
			DECLARE CONTINUE HANDLER FOR NOT FOUND SET @fin_cursor_vendedores = TRUE;  
            
            OPEN CURSOR_VENDEDORES;
			BUCLE_VENDEDORES: LOOP
				SET @fin_cursor_vendedores = FALSE;
				FETCH CURSOR_VENDEDORES INTO v_vendedor_id;        
				
				IF @fin_cursor_vendedores THEN			
					LEAVE BUCLE_VENDEDORES;
				END IF;
                
                #SE OBTIENEN LAS VENTAS Y DESCUENTOS REALIZADOS POR EL VENDEDOR EN EL PERIODO
				SELECT SUM(d.PRECIO_VENTA), SUM(d.DESCUENTO) INTO v_total_ventas, v_total_descuentos
				FROM VEN_VENTAS v
                JOIN VEN_VENTAS_DETALLES d ON
					d.VENTA_ID = v.VENTA_ID
				WHERE YEAR(v.FECHA_VENTA) = p_anho AND MONTH(v.FECHA_VENTA) = p_mes AND v.TIENDA_ID = v_tienda_id AND v.VENDEDOR_ID = v_vendedor_id;
				
				IF v_total_ventas IS NULL THEN
					SET v_total_ventas = 0;
				END IF;
                
                IF v_total_descuentos IS NULL THEN
					SET v_total_descuentos = 0;
				END IF;
                
                INSERT INTO VEN_REPORTES_VENDEDORES(ANHO, MES, TIENDA_ID, VENDEDOR_ID, TOTAL_VENTAS, TOTAL_DESCUENTOS) VALUES (p_anho, p_mes, v_tienda_id, v_vendedor_id, v_total_ventas, v_total_descuentos);
                
			END LOOP BUCLE_VENDEDORES;    
			SET @fin_cursor_vendedores = FALSE;
			CLOSE CURSOR_VENDEDORES;
        END;	
    END LOOP BUCLE_TIENDAS;    
    SET @fin_cursor_tiendas = FALSE;
    CLOSE CURSOR_TIENDAS;
END$$

DELIMITER ;