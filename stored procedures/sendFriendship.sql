-- Procedure sendFriendship(id_requester, id_target, fecha, is_premium, @ecode, @emsg)
-- Codigos devueltos:
-- 3 - LIMITE DE SOLICITUDES DE AMISTAD

DELIMITER $$
USE desa_byo$$
DROP PROCEDURE sendFriendship$$
CREATE PROCEDURE sendFriendship(
    in p_id_requester int(11),
    in p_id_target int(11),
    in p_fecha datetime,
    in p_is_premium boolean,
    out out_ecode tinyint(3),
    out out_emsg varchar(32)
)
BEGIN
	DECLARE totalSolicitudes int(3);
	DECLARE maxSolicitudes DOUBLE;

    CASE p_is_premium
    WHEN TRUE THEN
        INSERT INTO friendships(id_user_requester,id_user_target, is_accepted, date_emitted, is_viewed) 
            VALUES(p_id_requester, p_id_target, FALSE, p_fecha, FALSE);

       INSERT INTO desa_byo.interacciones(id_user, mensajes, solicitudes, chats)
				VALUES (p_id_requester, 0, 1, 0)
			ON DUPLICATE KEY UPDATE
			solicitudes = solicitudes+1;
	
    ELSE
        SELECT solicitudes INTO totalSolicitudes FROM interacciones WHERE id_user = p_id_requester;
        SELECT valor INTO maxSolicitudes FROM general_configurations WHERE id = 5;
        IF maxSolicitudes <= totalSolicitudes THEN
            SET out_ecode = 3;
            SET out_emsg = 'LIMITE DE SOLICITUDES DE AMISTAD';
        ELSE
           INSERT INTO friendships(id_user_requester,id_user_target, is_accepted, date_emitted, is_viewed) 
            VALUES(p_id_requester, p_id_target, FALSE, p_fecha, FALSE);

			INSERT INTO desa_byo.interacciones(id_user, mensajes, solicitudes, chats)
				VALUES (p_id_requester, 0, 1, 0)
			ON DUPLICATE KEY UPDATE
			solicitudes = solicitudes+1;
        END IF;
    END CASE;
END $$
DELIMITER ;