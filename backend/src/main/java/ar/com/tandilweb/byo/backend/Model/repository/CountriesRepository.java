package ar.com.tandilweb.byo.backend.Model.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ar.com.tandilweb.byo.backend.Model.BaseRepository;
import ar.com.tandilweb.byo.backend.Model.domain.Countries;

@Repository
public class CountriesRepository extends BaseRepository<Countries, Long>{
	
	public static Logger logger = LoggerFactory.getLogger(RememberTokensRepository.class);

	@Override
	public Countries create(Countries record) {
		try {
			final String sql = "INSERT INTO countries"
					+ "(name, code) VALUES(?,?)";
			jdbcTemplate.update(sql, new Object[] {
					record.getName(),
					record.getCode()
					});
			return record;
		} catch(DataAccessException e) {
			logger.error("RememberTokensRepository::create", e);
			return null;
		}
	}

	@Override
	public void update(Countries record) {
		try {
			final String sql = "UPDATE countries SET name = ?, code = ? WHERE id_country = ?";
			jdbcTemplate.update(sql, new Object[] {
					record.getName(),
					record.getCode(),
					record.getId_country()
			});
		} catch(DataAccessException e) {
			logger.error("RememberTokensRepository::update", e);
		}
	}

	@Override
	public Countries findById(Long id) {
		try {
			return jdbcTemplate.queryForObject(
                "SELECT * FROM countries WHERE id_country = ?",
                new Object[]{id}, new CountriesRowMapper());
		} catch(DataAccessException e) {
//			logger.debug("RememberTokensRepository::findById", e);
			return null;
		}
	}

}

class CountriesRowMapper implements RowMapper<Countries>
{
    public Countries mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Countries(
        		rs.getLong("id_user"),
        		rs.getString("name"),
        		rs.getString("code")
        		);
    }
}