update patient , 
     (SELECT b.id as id, a.name10 as blockgroup FROM borders2 as a ,patient as b    
 WHERE ST_CONTAINS(a.shape2, ST_GeometryFromText( CONCAT( 'POINT(', b.longitude, ' ', b.latitude, ')' ) ,1)  )
         ) t1
	set patient.blockgroup = t1.blockgroup
    where patient.id = t1.id;


