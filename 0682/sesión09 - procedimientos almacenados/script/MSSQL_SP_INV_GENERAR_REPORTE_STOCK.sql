
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER PROCEDURE SP_INV_GENERAR_REPORTE_STOCK
	@p_anho INT, @p_mes INT
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @v_almacen_id INT;
    DECLARE @v_producto_id INT;
    DECLARE @v_anho_anterior INT;
    DECLARE @v_mes_anterior INT;
    DECLARE @v_saldo_inicial INT;
    DECLARE @v_entradas INT;
    DECLARE @v_salidas INT;
    DECLARE @v_saldo_final INT;
	DECLARE CURSOR_ALMACENES CURSOR FOR SELECT ALMACEN_ID FROM INV_ALMACENES;
    DECLARE CURSOR_PRODUCTOS CURSOR FOR SELECT PRODUCTO_ID FROM INV_PRODUCTOS;

	--BORRAMOS EL REPORTE ANTERIOR DEL PERIODO SI EXISTE
    DELETE FROM INV_REPORTES_STOCKS WHERE ANHO = @p_anho AND MES = @p_mes;	

	--INSERTAMOS EN LA TABLA PERIODO SI ES QUE NO EXISTE EL PERIODO
    IF NOT EXISTS(SELECT ANHO, MES FROM INV_PERIODOS WHERE ANHO = @p_anho AND MES = @p_mes) 
	BEGIN
		INSERT INTO INV_PERIODOS(ANHO, MES, DESCRIPCION) VALUES (@p_anho, @p_mes, @p_anho+  '-' + FORMAT(@p_mes, '00'));
	END;

	--OBTENEMOS EL PERIODO ANTERIOR
    SET @v_anho_anterior = @p_anho;
    SET @v_mes_anterior = @p_mes;
    IF @v_mes_anterior = 1 
	BEGIN
		SET @v_mes_anterior=12;
		SET @v_anho_anterior=@v_anho_anterior-1;
	END
	ELSE	
		SET @v_mes_anterior=@v_mes_anterior-1;
	
	--RECORREMOS TODOS LOS ALMACENES	
	OPEN CURSOR_ALMACENES;
	FETCH NEXT FROM CURSOR_ALMACENES INTO @v_almacen_id;
	WHILE @@fetch_status = 0
	BEGIN
		--PARA CADA ALMACEN RECORREMOS TODOS LOS PRODUCTOS
		OPEN CURSOR_PRODUCTOS;
		FETCH NEXT FROM CURSOR_PRODUCTOS INTO @v_producto_id;
		WHILE @@fetch_status = 0
		BEGIN
			--INICIO: POR ALMACEN Y PRODUCTO OBTENEMOS LOS DATOS
            --SE OBTIENE EL SALDO INICIAL
            SELECT @v_saldo_inicial = SALDO_FINAL
            FROM INV_REPORTES_STOCKS
            WHERE ANHO = @v_anho_anterior AND MES = @v_mes_anterior AND ALMACEN_ID = @v_almacen_id AND PRODUCTO_ID = @v_producto_id;
            
            IF @v_saldo_inicial IS NULL
				SET @v_saldo_inicial = 0;
            
			--SE OBTIENE LAS ENTRADAS
            SELECT @v_entradas = SUM(m.CANTIDAD) 
            FROM INV_MOVIMIENTOS m
            JOIN INV_TIPOS_MOVIMIENTOS t ON t.TIPO_MOVIMIENTO_ID = m.TIPO_MOVIMIENTO_ID AND t.TIPO_OPERACION = 'ENTRADA'
            WHERE m.ALMACEN_ID = @v_almacen_id AND m.PRODUCTO_ID = @v_producto_id AND YEAR(m.FECHA_MOVIMIENTO) = @p_anho AND MONTH(m.FECHA_MOVIMIENTO) = @p_mes;
				
            IF @v_entradas IS NULL
				SET @v_entradas = 0;
			
			--SE OBTIENE LAS SALIDAS
            SELECT @v_salidas = SUM(m.CANTIDAD)
            FROM INV_MOVIMIENTOS m
            JOIN INV_TIPOS_MOVIMIENTOS t ON t.TIPO_MOVIMIENTO_ID = m.TIPO_MOVIMIENTO_ID AND t.TIPO_OPERACION = 'SALIDA'
            WHERE m.ALMACEN_ID = @v_almacen_id AND m.PRODUCTO_ID = @v_producto_id AND YEAR(m.FECHA_MOVIMIENTO) = @p_anho AND MONTH(m.FECHA_MOVIMIENTO) = @p_mes;
				
            IF @v_salidas IS NULL 
				SET @v_salidas = 0;
            
			--SE CALCULA EL SALDO FINAL
            SET @v_saldo_final = @v_saldo_inicial + @v_entradas - @v_salidas;
            
            INSERT INTO INV_REPORTES_STOCKS (ANHO, MES, ALMACEN_ID, PRODUCTO_ID, SALDO_INICIAL, ENTRADAS, SALIDAS, SALDO_FINAL) VALUES (@p_anho, @p_mes, @v_almacen_id, @v_producto_id, @v_saldo_inicial, @v_entradas, @v_salidas, @v_saldo_final);
			--FIN: POR ALMACEN Y PRODUCTO OBTENEMOS LOS DATOS

			FETCH NEXT FROM CURSOR_PRODUCTOS INTO @v_producto_id;
		END
		CLOSE CURSOR_PRODUCTOS;		
		FETCH NEXT FROM CURSOR_ALMACENES INTO @v_almacen_id
	END
	CLOSE CURSOR_ALMACENES;
	DEALLOCATE CURSOR_ALMACENES;
	DEALLOCATE CURSOR_PRODUCTOS;
END
GO
