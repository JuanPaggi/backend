DELIMITER $$
CREATE TRIGGER desa_byo.trig_free_firsts_users BEFORE INSERT ON desa_byo.users
FOR EACH ROW
BEGIN
declare free int(10);
	set new.is_premium = false;
    SET free := CAST((select valor from desa_byo.general_configurations where id = 8) as unsigned);
    IF ( free > 0 ) THEN
    set NEW.is_premium = true;
    update desa_byo.general_configurations set valor = cast((free-1) as char) where id = 8;
    END IF; 
END$$