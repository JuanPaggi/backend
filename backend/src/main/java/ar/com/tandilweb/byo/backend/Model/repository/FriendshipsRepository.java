package ar.com.tandilweb.byo.backend.Model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ar.com.tandilweb.byo.backend.Model.BaseRepository;
import ar.com.tandilweb.byo.backend.Model.domain.Friendships;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.utils.SpCall;
import ar.com.tandilweb.byo.backend.utils.SpCaller;

@Repository
public class FriendshipsRepository extends BaseRepository<Friendships, Long>{
	
	@Autowired
	private SpCaller spCaller;
	@Autowired
	private UserRepository userRepository;
	
	public static Logger logger = LoggerFactory.getLogger(FriendshipsRepository.class);
	
	public List<Friendships> getRequestsSendedReceivedBy(long id) {
		try {
	    	return jdbcTemplate.query(
	                "SELECT * FROM friendships " + 
	                "WHERE (id_user_requester = ? OR id_user_target = ?) AND is_accepted = false AND is_rejected = false", 
	                new FriendshipsRowMapper(),
	                new Object[]{ id, id });
		} catch(DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Friendships> getFriendsAccepted(long id) {
		try {
	    	return jdbcTemplate.query(
	                "SELECT * FROM friendships " + 
	                "WHERE (id_user_requester = ? OR id_user_target = ?) AND is_accepted = true", 
	                new FriendshipsRowMapper(),
	                new Object[]{ id, id });
		} catch(DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int getLastNotificationsNumber(long idTarget) {
		try {
	    	return jdbcTemplate.queryForObject(
	                "SELECT COUNT(*) FROM friendships WHERE id_user_target = ? AND is_viewed = false ", 
	                new Object[]{ idTarget },
	                Integer.class);
		} catch(DataAccessException e) {
			e.printStackTrace();
			return -1;
		}
	}
		
	public List<Friendships> getFriendship(long idRequester, long idTarget) {
		try {
	    	return jdbcTemplate.query(
	                "SELECT * FROM friendships " + 
	                "WHERE id_user_requester = ? AND id_user_target = ? AND is_accepted = false", 
	                new FriendshipsRowMapper(),
	                new Object[]{ idRequester, idTarget });
		} catch(DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void markRequestNotificationsAsViewed(long idUser) {
		try {
			final String sql = "UPDATE friendships"
							+ " SET is_viewed = true"
							+ " WHERE id_user_target = ?";
			jdbcTemplate.update(sql, new Object[] {
				idUser
			});
			
		} catch(DataAccessException e) {
			logger.error("FriendshipsRepository :: create", e);
		}
	}
	
	public void reject(long id_requester, long id_target) {
		try {
			final String sql = "UPDATE friendships"
							+ " SET is_rejected = true"
							+ " WHERE id_user_requester = ? AND id_user_target = ?";
			jdbcTemplate.update(sql,new Object[]{ id_requester, id_target });
			
		} catch(DataAccessException e) {
			logger.error("FriendshipsRepository :: reject", e);
		}
	}
	
	
	public Friendships create(final Friendships record) {
		
		//CALL `desa_byo`.`sendFriendship`(1, 8,  now(), FALSE,  @ecode, @emsg);
		
				SqlParameter[] paramArray = {
					spCaller.makeInputParameter("p_id_requester", Types.INTEGER),
					spCaller.makeInputParameter("p_id_target", Types.INTEGER),
					spCaller.makeInputParameter("p_fecha", Types.DATE),
					spCaller.makeInputParameter("p_is_premium", Types.BOOLEAN),
					spCaller.makeOutputParameter("out_ecode", Types.TINYINT),
					spCaller.makeOutputParameter("out_emsg", Types.VARCHAR),
					spCaller.makeOutputParameter("out_success", Types.BOOLEAN),
				};
				SpCall call = spCaller.callSp("sendFriendship", paramArray);
				
				Users u =userRepository.findById(record.getId_user_requester());
				Map<String, Object> result = call.execute(
					record.getId_user_requester(), 
					record.getId_user_target(), 
					new Date(),
					u.isPremium()
				);
				logger.debug("DEBUG RESPONSE SPCALLER"+ result.get("out_success"));
				
	//	return (Boolean) result.get("out_success");
				return null;
//		try {
//			final String sql = "INSERT INTO friendships"
//					+ "(id_user_requester, id_user_target, is_accepted, date_emitted, is_viewed)"
//					+ " VALUES(?,?,?,?,?)";
//			KeyHolder holder = new GeneratedKeyHolder();
//			
//			jdbcTemplate.update(new PreparedStatementCreator() {
//	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//	                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//	                ps.setLong(1, record.getId_user_requester());
//	                ps.setLong(2, record.getId_user_target());
//	                ps.setBoolean(3, record.is_accepted());
//	                ps.setDate(4, new java.sql.Date(record.getDate_emitted().getTimeInMillis()));
//	                ps.setBoolean(5, record.is_viewed());
//	                return ps;
//	            }
//	        }, holder);
//			return record;
//		} catch(DataAccessException e) {
//			logger.error("FriendshipsRepository :: create", e);
//			return null;
//		}
	}
	
	public void delete(long id_requester, long id_target) {
		try {
			final String sql = "DELETE FROM friendships WHERE id_user_requester = ? AND id_user_target = ?";
			jdbcTemplate.update(sql,new Object[]{ id_requester, id_target });
		} catch(DataAccessException e) {
			logger.debug("FriendshipsRepository :: delete", e);
		}
	}
	
	@Override
	public void update(Friendships record) {
		try {
			final String sql = "UPDATE friendships SET is_accepted = ?, date_emitted = ?, is_viewed = ?"
					+" WHERE id_user_requester = ? AND id_user_target = ?";
			jdbcTemplate.update(sql, new Object[] {
					record.is_accepted(),
					record.getDate_emitted(),
					record.is_viewed(),
					record.getId_user_requester(),
					record.getId_user_target()				
			});
		} catch(DataAccessException e) {
			logger.debug("FriendshipsRepository :: update", e);
		}
		
	}

	@Override
	public Friendships findById(Long id) {
		try {
			return jdbcTemplate.queryForObject(
	                "select * from users where id_user=?",
	                new Object[]{id}, new FriendshipsRowMapper());
		} catch(DataAccessException e) {
			return null;
		}
	}
	
	class FriendshipsRowMapper implements RowMapper<Friendships>
	{  
	    public Friendships mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Calendar cal = Calendar.getInstance();
	    	cal.setTime(rs.getTimestamp("date_emitted"));
	        return new Friendships(
	        		rs.getLong("id_user_requester"),
	        		rs.getLong("id_user_target"),
	        		rs.getBoolean("is_accepted"),
	        		cal,
	        		rs.getBoolean("is_viewed")
	        		);
	    }
	}
	
}
