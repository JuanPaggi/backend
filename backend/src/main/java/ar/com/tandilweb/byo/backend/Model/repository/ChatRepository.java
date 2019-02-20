package ar.com.tandilweb.byo.backend.Model.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ar.com.tandilweb.byo.backend.Model.BaseRepository;
import ar.com.tandilweb.byo.backend.Model.domain.Chats;

@Repository
public class ChatRepository extends BaseRepository<Chats, Long>{
	
	public static Logger logger = LoggerFactory.getLogger(ChatRepository.class);

	@Override
	public Chats create(Chats record) {
		try {
			final String sql = "INSERT INTO chats"
					+ "(id_user_requester, id_user_sender, last_message_id) VALUES(?,?,?)";
			jdbcTemplate.update(sql, new Object[] {
					record.getId_user_requester(),
					record.getId_user_sender(),
					record.getLast_message_id()
					});
			return record;
		} catch(DataAccessException e) {
			logger.error("ChatRepository::create", e);
			return null;
		}
	}

	@Override
	public void update(Chats record) {
		try {
			final String sql = "UPDATE chats SET last_message_id = ? WHERE id_user_requester = ? AND id_user_sender = ?";
			jdbcTemplate.update(sql, new Object[] {
					record.getLast_message_id(),
					record.getId_user_requester(),
					record.getId_user_sender()
			});
		} catch(DataAccessException e) {
			logger.error("ChatRepository::update", e);
		}
	}

	@Override
	@Deprecated
	public Chats findById(Long id_me) {
		return null;
	}
	
	public List<Chats> findAllById(Long id_me) {
		try {
			return jdbcTemplate.query("SELECT * FROM chats WHERE id_user_requester = ? OR id_user_sender = ?",
					new ChatRowMapper(), new Object[] { id_me, id_me });
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Chats findAssoc(Long id_me, Long id_target) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM chats WHERE (id_user_requester = ? AND id_user_sender = ?) OR (id_user_requester = ? AND id_user_sender = ?)",
					new ChatRowMapper(), new Object[] { id_me, id_target, id_target, id_me });
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	class ChatRowMapper implements RowMapper<Chats>
	{
	    public Chats mapRow(ResultSet rs, int rowNum) throws SQLException {
	        return new Chats(
	        		rs.getLong("id_user_requester"),
	        		rs.getLong("id_user_sender"),
	        		rs.getLong("last_message_id")
	        		);
	    }
	}

}
