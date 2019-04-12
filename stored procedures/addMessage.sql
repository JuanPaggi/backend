-- Procedure addMessage(id_sender, id_target, message, fecha, is_premium, @message, @ecode, @emsg)
-- Codigos devueltos:
-- 1 - LIMITE DE MENSAJES
-- 2 - LIMITE DE CHATS
DELIMITER $$
USE desa_byo$$
DROP PROCEDURE addMessage$$
CREATE PROCEDURE addMessage(
    in p_id_sender int(11),
    in p_id_target int(11),
    in p_message varchar(255),
    in p_fecha datetime,
    in p_is_premium boolean,
    out out_message_id int(11),
    out out_ecode tinyint(3),
    out out_emsg varchar(32)
)
BEGIN
	DECLARE totalMensajes int(3);
    DECLARE totalChats int(3);
	DECLARE maxMensajes DOUBLE;
    DECLARE maxChats DOUBLE;
    DECLARE chatIniciado int(11);
    CASE p_is_premium
    WHEN TRUE THEN
        INSERT INTO mensajes(id_sender, id_target, messages, fecha, is_viewed) 
            VALUES(p_id_sender, p_id_target, p_message, p_fecha, FALSE);
        SET out_message_id = LAST_INSERT_ID();
        SELECT count(last_message_id) INTO chatIniciado FROM chats WHERE (id_user_requester = p_id_sender AND id_user_sender = p_id_target) OR (id_user_requester = p_id_target AND id_user_sender = p_id_sender);
		IF chatIniciado > 0 THEN 
			UPDATE chats SET last_message_id = out_message_id WHERE (id_user_requester = p_id_sender AND id_user_sender = p_id_target) OR (id_user_requester = p_id_target AND id_user_sender = p_id_sender);
		ELSE
			INSERT INTO chats(id_user_requester, id_user_sender, last_message_id) 
                        VALUES(p_id_target, p_id_sender, out_message_id);
		END IF;
    ELSE
        SELECT mensajes, chats INTO totalMensajes, totalChats FROM interacciones WHERE id_user = p_id_sender;
        SELECT valor INTO maxMensajes FROM general_configurations WHERE id = 4;
        IF maxMensajes <= totalMensajes THEN
            SET out_ecode = 1;
            SET out_emsg = 'LIMITE DE MENSAJES';
        ELSE
            SELECT count(last_message_id) INTO chatIniciado FROM chats WHERE (id_user_requester = p_id_sender AND id_user_sender = p_id_target) OR (id_user_requester = p_id_target AND id_user_sender = p_id_sender);
            if chatIniciado > 0 THEN 
                INSERT INTO mensajes(id_sender, id_target, messages, fecha, is_viewed) 
                    VALUES(p_id_sender, p_id_target, p_message, p_fecha, FALSE);
                SET out_message_id = LAST_INSERT_ID();
                UPDATE chats SET last_message_id = out_message_id WHERE (id_user_requester = p_id_sender AND id_user_sender = p_id_target) OR (id_user_requester = p_id_target AND id_user_sender = p_id_sender);
                UPDATE interacciones SET mensajes = mensajes + 1 WHERE id_user = p_id_sender;
            ELSE
                SELECT valor INTO maxChats FROM general_configurations WHERE id = 7;
                IF totalChats >= maxChats THEN
                    SET out_ecode = 2;
                    SET out_emsg = 'LIMITE DE CHATS';
                ELSE
                    INSERT INTO mensajes(id_sender, id_target, messages, fecha, is_viewed) 
                    VALUES(p_id_sender, p_id_target, p_message, p_fecha, FALSE);
                    SET out_message_id = LAST_INSERT_ID();
                    INSERT INTO chats(id_user_requester, id_user_sender, last_message_id) 
                        VALUES(p_id_target, p_id_sender, out_message_id);
                    UPDATE interacciones SET mensajes = mensajes + 1, chats = chats + 1 WHERE id_user = p_id_sender;
                END IF;
            END IF;
        END IF;
    END CASE;
END $$
DELIMITER ;