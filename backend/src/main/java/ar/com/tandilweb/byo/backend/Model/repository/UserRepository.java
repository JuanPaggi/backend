package ar.com.tandilweb.byo.backend.Model.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ar.com.tandilweb.byo.backend.Model.BaseRepository;
import ar.com.tandilweb.byo.backend.Model.domain.Users;

@Repository
public class UserRepository extends BaseRepository<Users, Long>{

    //@Transactional(readOnly=true) esto es para el isolation de las consultas (el bloqueo por funcion)
	public Users findBylinkedinId(String linkedin_id) {
    	return jdbcTemplate.queryForObject(
                "select * from users where linkedin_id=?",
                new Object[]{linkedin_id}, new UserRowMapper());
	}
	
	public Users findByemail(String email) {
		return jdbcTemplate.queryForObject(
                "select * from users where email=?",
                new Object[]{email}, new UserRowMapper());
	}
	
	public Users findById(Long id) {
		return jdbcTemplate.queryForObject(
                "select * from users where id_user=?",
                new Object[]{id}, new UserRowMapper());
	}
	
	@Override
	public Users create(Users record) {
		// TODO Auto-generated method stub
		final String sql = "INSERT INTO users"
				+ "(first_name, last_name, email, password, last_login, signup_date,"
				+ " linkedin_id, busco, ofrezco, picture_url, is_premium, salt_jwt, completoByO,"
				+ " locked, failed_login_attempts, unlock_account_code) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder holder = new GeneratedKeyHolder();
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
				record.getUnLockAccountCode()
		}, holder);
		record.setId_user(holder.getKey().longValue());
		return record;
	}

	@Override
	public void update(Users record) {
		// TODO Auto-generated method stub
		final String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ?, last_login = ?, signup_date = ?,"
				+ " linkedin_id = ?, busco, ofrezco = ?, picture_url = ?, is_premium = ?, salt_jwt = ?, completoByO = ?, locked = ?,"
				+ " failed_login_attempts = ?, unlock_account_code = ? WHERE id_usuario = ?";
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
        		rs.getBoolean("completeByO"),
        		rs.getBoolean("locked"),
        		rs.getInt("failed_login_attempts"),
        		rs.getString("unlock_account_code")
        		);
    }
}