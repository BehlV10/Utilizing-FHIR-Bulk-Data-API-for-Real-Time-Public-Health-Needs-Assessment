DELIMITER //
CREATE PROCEDURE UpdatePatientBlockgroup()
BEGIN
set SQL_SAFE_UPDATES = 0;

update patient p1,
     (SELECT b.id as id, b.source_system_id as sid, a.name10 as blockgroup FROM borders2 as a ,patient as b
		WHERE ST_CONTAINS(a.shape2, ST_GeometryFromText( CONCAT( 'POINT(', b.longitude, ' ', b.latitude, ')' ) ,1)  )
	 ) t1
set p1.blockgroup = t1.blockgroup
where (p1.id   = t1.id and p1.source_system_id  = t1.sid);

set SQL_SAFE_UPDATES = 1;
END //
DELIMITER ;