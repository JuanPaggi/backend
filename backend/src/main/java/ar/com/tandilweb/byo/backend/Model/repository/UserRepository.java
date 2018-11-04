package ar.com.tandilweb.byo.backend.Model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ar.com.tandilweb.byo.backend.Model.BaseRepository;
import ar.com.tandilweb.byo.backend.Model.domain.Users;

@Repository
public class UserRepository extends BaseRepository<Users, Long>{
	
	public static Logger logger = LoggerFactory.getLogger(UserRepository.class);

    //@Transactional(readOnly=true) esto es para el isolation de las consultas (el bloqueo por funcion)
	public Users findBylinkedinId(String linkedin_id) {
		try {
	    	return jdbcTemplate.queryForObject(
	                "select * from users where linkedin_id=?",
	                new Object[]{linkedin_id}, new UserRowMapper());
		} catch(DataAccessException e) {
			logger.debug("UserRepository :: findByLinkedinID");
			return null;
		}
	}
	
	public Users findByemail(String email) {
		try {
			return jdbcTemplate.queryForObject(
	                "select * from users where email=?",
	                new Object[]{email}, new UserRowMapper());
		} catch(DataAccessException e) {
			logger.debug("UserRepository :: findByEmail");
			return null;
		}
	}
	
	public Users findById(Long id) {
		try {
			return jdbcTemplate.queryForObject(
	                "select * from users where id_user=?",
	                new Object[]{id}, new UserRowMapper());
		} catch(DataAccessException e) {
			logger.debug("UserRepository :: findById",e);
			return null;
		}
	}
	
	@Override
	public Users create(final Users record) {
		try {
			final String sql = "INSERT INTO users"
					+ "(first_name, last_name, email, password, last_login, signup_date,"
					+ " linkedin_id, busco, ofrezco, picture_url, is_premium, salt_jwt, completoByO,"
					+ " locked, failed_login_attempts, unlock_account_code) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			KeyHolder holder = new GeneratedKeyHolder();

			jdbcTemplate.update(new PreparedStatementCreator() {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	                ps.setString(1, record.getFirst_name());
	                ps.setString(2, record.getLast_name());
	                ps.setString(3, record.getEmail());
	                ps.setString(4, record.getPassword());
	                ps.setDate(5, new java.sql.Date(record.getLast_login().getTime()));
	                ps.setDate(6, new java.sql.Date(record.getSignup_date().getTime()));
	                ps.setString(7, record.getLinkedin_id());
	                ps.setString(8, record.getBusco());
	                ps.setString(9, record.getOfrezco());
	                ps.setString(10, record.getPicture_url());
	                ps.setBoolean(11, record.isPremium());
	                ps.setString(12, record.getSalt_jwt());
	                ps.setBoolean(13, record.isCompletoByO());
	                ps.setBoolean(14, record.isLocked());
	                ps.setInt(15, record.getFailedLoginAttempts());
	                ps.setString(16, record.getUnLockAccountCode());
	                return ps;
	            }
	        }, holder);
			record.setId_user(holder.getKey().longValue());
			return record;
		} catch(DataAccessException e) {
			logger.error("UserRepository :: create", e);
			return null;
		}
	}

	@Override
	public void update(Users record) {
		try {
			final String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ?, last_login = ?, signup_date = ?,"
					+ " linkedin_id = ?, busco = ?, ofrezco = ?, picture_url = ?, is_premium = ?, salt_jwt = ?, completoByO = ?, locked = ?,"
					+ " failed_login_attempts = ?, unlock_account_code = ? WHERE id_user = ?";
			jdbcTemplate.update(sql, new Object[] {
					record.getFirst_name(),
					record.getLast_name(),
					record.getEmail(),
					record.getPassword(),
					record.getLast_login(),
					record.getSignup_date(),
					record.getLinkedin_id(),
					record.getBusco(),
					record.getOfrezco(),
					record.getPicture_url(),
					record.isPremium(),
					record.getSalt_jwt(),
					record.isCompletoByO(),
					record.isLocked(),
					record.getFailedLoginAttempts(),
					record.getUnLockAccountCode(),
					record.getId_user()
			});
		} catch(DataAccessException e) {
			logger.debug("UserRepository :: update", e);
		}
	}

}

class UserRowMapper implements RowMapper<Users>
{
    public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Users(
        		rs.getLong("id_user"),
        		rs.getString("first_name"),
        		rs.getString("last_name"),
        		rs.getString("email"),
        		rs.getString("password"),
        		rs.getDate("last_login"),
        		rs.getDate("signup_date"),
        		rs.getString("linkedin_id"),
        		rs.getString("busco"),
        		rs.getString("ofrezco"),
        		rs.getString("picture_url"),
        		rs.getBoolean("is_premium"),
        		rs.getString("salt_jwt"),
        		rs.getBoolean("completoByO"),
        		rs.getBoolean("locked"),
        		rs.getInt("failed_login_attempts"),
        		rs.getString("unlock_account_code")
        		);
    }
}