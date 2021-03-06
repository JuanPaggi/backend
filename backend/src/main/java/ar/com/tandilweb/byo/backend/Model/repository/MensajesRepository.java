package ar.com.tandilweb.byo.backend.Model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ar.com.tandilweb.byo.backend.Model.BaseRepository;
import ar.com.tandilweb.byo.backend.Model.domain.Mensajes;

@Repository
public class MensajesRepository extends BaseRepository<Mensajes, Long> {
	
	public static Logger logger = LoggerFactory.getLogger(FriendshipsRepository.class);
	
	@Override
	public Mensajes create(final Mensajes record) {
		try {
			final String sql = "INSERT INTO mensajes"
					+ "(id_sender, id_target, messages, fecha, is_viewed) VALUES(?,?,?,?,?)";
			KeyHolder holder = new GeneratedKeyHolder();
			
			jdbcTemplate.update(new PreparedStatementCreator() {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	                ps.setDouble(1, record.id_sender);
	                ps.setDouble(2, record.id_target);
	                ps.setString(3, record.message);
	                ps.setTimestamp(4, new java.sql.Timestamp(record.fecha.getTime()));
	                ps.setBoolean(5, record.is_viewed);
	                return ps;
	            }
	        }, holder);
			
			record.id_message = holder.getKey().longValue();
			return record;
		} catch(DataAccessException e) {
			logger.error("MensajesRepository :: create",e);
			return null;
		}
	}

	@Override
	public void update(Mensajes record) {
		try {
			final String sql = "UPDATE mensajes SET id_sender = ?, id_target = ?, messages = ?, fecha = ?, is_viewed = ?"
					+" WHERE id_sender = ? AND id_target = ?";
			jdbcTemplate.update(sql, new Object[] {
					record.id_sender,
					record.id_target,
					record.message,
					record.fecha,
					record.is_viewed,
					record.id_sender,
					record.id_target
			});
		} catch(DataAccessException e) {
			logger.debug("MensajesRepository :: update", e);
		}
	}

	@Override
	public Mensajes findById(Long id) {
		try {
	    	return jdbcTemplate.queryForObject(
	                "SELECT * FROM mensajes WHERE id_mensaje = ?",
	                new MensajesRowMapper(),
	                new Object[]{ id }
	    			);
		} catch(DataAccessException e) {
			logger.debug("MensajesRepository :: findById", e);
			return null;
		}
	}
	
	public void delete(long id_sender, long id_target) {
		try {
			final String sql = "DELETE FROM mensajes WHERE id_sender = ? AND id_target = ?";
			jdbcTemplate.update(sql,new Object[]{ id_sender, id_target });
		} catch(DataAccessException e) {
			logger.debug("MensajesRepository :: delete", e);
		}
	}
	
	public List<Mensajes> getChatWith(long id_me, long id_target) {
		try {
	    	return jdbcTemplate.query(
	                "SELECT * FROM ( SELECT * FROM mensajes AS m " + 
	                "WHERE (m.id_sender = ? AND m.id_target = ?) OR (m.id_target = ? AND m.id_sender = ?) ORDER BY m.fecha DESC, m.id_mensaje DESC LIMIT 30 ) AS r ORDER BY r.fecha ASC, r.id_mensaje ASC", 
	                new MensajesRowMapper(),
	                new Object[]{ id_me, id_target, id_me, id_target });
		} catch(DataAccessException e) {
			logger.debug("MensajesRepository :: getChatWith", e);
			return null;
		}
	}
	
	class MensajesRowMapper implements RowMapper<Mensajes>
	{
	    public Mensajes mapRow(ResultSet rs, int rowNum) throws SQLException {
	        return new Mensajes(
	        		rs.getLong("id_mensaje"),
	        		rs.getLong("id_sender"),
	        		rs.getLong("id_target"),
	        		rs.getString("messages"),
	        		rs.getTimestamp("fecha"),
	        		rs.getBoolean("is_viewed")
	        		);
	    }
	}

}
