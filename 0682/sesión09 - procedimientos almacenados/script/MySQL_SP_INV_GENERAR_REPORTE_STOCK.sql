DELIMITER $$

DROP PROCEDURE IF EXISTS SP_INV_GENERAR_REPORTE_STOCK$$

CREATE PROCEDURE `SP_INV_GENERAR_REPORTE_STOCK`(IN p_anho INT, IN p_mes INT)
BEGIN
	DECLARE v_almacen_id INT;
    DECLARE v_producto_id INT;
    DECLARE v_anho_anterior INT;
    DECLARE v_mes_anterior INT;
    DECLARE v_saldo_inicial INT;
    DECLARE v_entradas INT;
    DECLARE v_salidas INT;
    DECLARE v_saldo_final INT;
	DECLARE CURSOR_ALMACENES CURSOR FOR SELECT ALMACEN_ID FROM INV_ALMACENES ;
    DECLARE CURSOR_PRODUCTOS CURSOR FOR SELECT PRODUCTO_ID FROM INV_PRODUCTOS ;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET @fin_cursor_almacenes = TRUE, @fin_cursor_productos = TRUE;    
    
    #BORRAMOS EL REPORTE ANTERIOR DEL PERIODO SI EXISTE
    DELETE FROM INV_REPORTES_STOCKS WHERE ANHO = p_anho AND MES = p_mes;	
    
    #INSERTAMOS EN LA TABLA PERIODO SI ES QUE NO EXISTE EL PERIODO
    IF NOT EXISTS(SELECT ANHO, MES FROM INV_PERIODOS WHERE ANHO = p_anho AND MES = p_mes) THEN
			INSERT INTO INV_PERIODOS(ANHO, MES, DESCRIPCION) VALUES (p_anho, p_mes, CONCAT(CONCAT(p_anho, "-"), LPAD(CAST(p_mes AS CHAR), 2, '0')));
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
    
    #RECORREMOS TODOS LOS ALMACENES
    OPEN CURSOR_ALMACENES;
    BUCLE_ALMACENES: LOOP
        SET @fin_cursor_almacenes = FALSE;
		FETCH CURSOR_ALMACENES INTO v_almacen_id;
        
        IF @fin_cursor_almacenes THEN			
			LEAVE BUCLE_ALMACENES;
		END IF;
     
		#PARA CADA ALMACEN RECORREMOS TODOS LOS PRODUCTOS
        OPEN CURSOR_PRODUCTOS;
		BUCLE_PRODUCTOS: LOOP
			SET @fin_cursor_productos = FALSE;
			FETCH CURSOR_PRODUCTOS INTO v_producto_id;
        
			IF @fin_cursor_productos THEN			
				LEAVE BUCLE_PRODUCTOS;
			END IF;
            
            #INICIO: POR ALMACEN Y PRODUCTO OBTENEMOS LOS DATOS
            #SE OBTIENE EL SALDO INICIAL
            SELECT SALDO_FINAL INTO v_saldo_inicial
            FROM INV_REPORTES_STOCKS
            WHERE ANHO = v_anho_anterior AND MES = v_mes_anterior AND ALMACEN_ID = v_almacen_id AND PRODUCTO_ID = v_producto_id;
            
            IF v_saldo_inicial IS NULL THEN
				SET v_saldo_inicial = 0;
            END IF;
                                    
            #SE OBTIENE LAS ENTRADAS
            SELECT SUM(m.CANTIDAD) INTO v_entradas
            FROM INV_MOVIMIENTOS m
            JOIN INV_TIPOS_MOVIMIENTOS t ON t.TIPO_MOVIMIENTO_ID = m.TIPO_MOVIMIENTO_ID AND t.TIPO_OPERACION = 'ENTRADA'
            WHERE m.ALMACEN_ID = v_almacen_id AND m.PRODUCTO_ID = v_producto_id AND YEAR(m.FECHA_MOVIMIENTO) = p_anho AND MONTH(m.FECHA_MOVIMIENTO) = p_mes;
				
            IF v_entradas IS NULL THEN
				SET v_entradas = 0;
            END IF;
            
            #SE OBTIENE LAS SALIDAS
            SELECT SUM(m.CANTIDAD) INTO v_salidas
            FROM INV_MOVIMIENTOS m
            JOIN INV_TIPOS_MOVIMIENTOS t ON t.TIPO_MOVIMIENTO_ID = m.TIPO_MOVIMIENTO_ID AND t.TIPO_OPERACION = 'SALIDA'
            WHERE m.ALMACEN_ID = v_almacen_id AND m.PRODUCTO_ID = v_producto_id AND YEAR(m.FECHA_MOVIMIENTO) = p_anho AND MONTH(m.FECHA_MOVIMIENTO) = p_mes;
				
            IF v_salidas IS NULL THEN
				SET v_salidas = 0;
            END IF;
            
            #SE CALCULA EL SALDO FINAL
            SET v_saldo_final = v_saldo_inicial + v_entradas - v_salidas;
            
            INSERT INTO INV_REPORTES_STOCKS (ANHO, MES, ALMACEN_ID, PRODUCTO_ID, SALDO_INICIAL, ENTRADAS, SALIDAS, SALDO_FINAL) VALUES (p_anho, p_mes, v_almacen_id, v_producto_id, v_saldo_inicial, v_entradas, v_salidas, v_saldo_final);
            
            #FIN: POR ALMACEN Y PRODUCTO OBTENEMOS LOS DATOS
        
        END LOOP BUCLE_PRODUCTOS;            
		SET @fin_cursor_productos = FALSE;
		CLOSE CURSOR_PRODUCTOS;			
        
	END LOOP BUCLE_ALMACENES;    
    SET @fin_cursor_almacenes = FALSE;
    CLOSE CURSOR_ALMACENES;
END$$

DELIMITER ;