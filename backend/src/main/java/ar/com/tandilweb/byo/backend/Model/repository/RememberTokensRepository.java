package ar.com.tandilweb.byo.backend.Model.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ar.com.tandilweb.byo.backend.Model.BaseRepository;
import ar.com.tandilweb.byo.backend.Model.domain.RememberTokens;

@Repository
public class RememberTokensRepository extends BaseRepository<RememberTokens, Long>{
	
	public static Logger logger = LoggerFactory.getLogger(RememberTokensRepository.class);

	@Override
	public RememberTokens create(RememberTokens record) {
		try {
			final String sql = "INSERT INTO remember_tokens"
					+ "(id_user, unlock_key, request_date, attempts) VALUES(?,?,?,?)";
			jdbcTemplate.update(sql, new Object[] {
					record.getId_user(),
					record.getUnlock_key(),
					record.getRequest_date(),
					record.getAttempts() 
					});
			return record;
		} catch(DataAccessException e) {
			logger.error("RememberTokensRepository::create", e);
			return null;
		}
	}

	@Override
	public void update(RememberTokens record) {
		try {
			final String sql = "UPDATE remember_tokens SET unlock_key = ?, request_date = ?, attempts = ? WHERE id_user = ?";
			jdbcTemplate.update(sql, new Object[] {
					record.getUnlock_key(),
					record.getRequest_date(),
					record.getAttempts(),
					record.getId_user()
			});
		} catch(DataAccessException e) {
			logger.error("RememberTokensRepository::update", e);
		}
	}

	@Override
	public RememberTokens findById(Long id) {
		try {
			return jdbcTemplate.queryForObject(
                "select * from remember_tokens where id_user = ?",
                new Object[]{id}, new RememberTokensRowMapper());
		} catch(DataAccessException e) {
//			logger.debug("RememberTokensRepository::findById", e);
			return null;
		}
	}

}

class RememberTokensRowMapper implements RowMapper<RememberTokens>
{
    public RememberTokens mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RememberTokens(
        		rs.getLong("id_user"),
        		rs.getString("unlock_key"),
        		rs.getDate("request_date"),
        		rs.getInt("attempts")
        		);
    }
}