SELECT   r.ANHO,  
         r.MES,  
         t.TIENDA_ID,  
         t.NOMBRE,
         t.DIRECCION,
         t.TIENDA_PRINCIPAL,
         t.COMISION,
         v.VENDEDOR_ID,  
         v.PATERNO,
         v.MATERNO,
         v.NOMBRES,
         r.TOTAL_VENTAS,  
         r.TOTAL_DESCUENTOS          
FROM VEN_REPORTES_VENDEDORES r
JOIN VEN_TIENDAS t ON t.TIENDA_ID = r.TIENDA_ID
JOIN VEN_VENDEDORES v ON v.VENDEDOR_ID = r.VENDEDOR_ID AND v.TIENDA_ID = r.TIENDA_ID
WHERE r.ANHO = 2025 and r.MES = 1;