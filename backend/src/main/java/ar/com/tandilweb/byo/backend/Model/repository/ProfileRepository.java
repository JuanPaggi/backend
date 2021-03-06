package ar.com.tandilweb.byo.backend.Model.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ar.com.tandilweb.byo.backend.Model.BaseRepository;
import ar.com.tandilweb.byo.backend.Model.domain.Profile;

@Repository
public class ProfileRepository extends BaseRepository<Profile, Long>{
		
	public static Logger logger = LoggerFactory.getLogger(ProfileRepository.class);
	
	@Override
	public Profile findById(Long id_usuario) { 
		try {
	    	return jdbcTemplate.queryForObject(
	                "SELECT * FROM profile WHERE id_user=?",
	                new Object[]{id_usuario}, new ProfileRowMapper());
		} catch(DataAccessException e) {
			return null;
		}
	}

	@Override
	public Profile create(Profile record) {
		try {
			final String sql = "INSERT INTO profile"
					+ "(`id_user`,`headline`,`industry`,`country`,`location`,`linkedin_url`,`summary`) VALUES(?,?,?,?,?,?,?)";
			jdbcTemplate.update(sql, new Object[] {
					record.getId_user(),
					record.getHeadline(),
					record.getIndustry(),
					1,//country
					record.getLocation(),
					record.getLinkedin_url(),
					record.getSummary()
					});
			return record;
		} catch(DataAccessException e) {
			logger.error("RememberTokensRepository::create", e);
			return null;
		}
	}

	@Override
	public void update(Profile record) {
		try {
			final String sql = "UPDATE profile SET headline = ?, industry = ?, location = ?, linkedin_url = ?, summary = ?, current_position = ?, company_name = ?"
							+ " WHERE id_user = ?";
			jdbcTemplate.update(sql, new Object[] {
					record.getHeadline(),
					record.getIndustry(),
					record.getLocation(),
					record.getLinkedin_url(),
					record.getSummary(),
					record.getCurrent_position(),
					record.getCompany_name(),
					record.getId_user()
			});
		} catch(DataAccessException e) {
			logger.debug("ProfileRepository :: update", e);
		}
		
	}

}

class ProfileRowMapper implements RowMapper<Profile>
{
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Profile(
        		rs.getLong("id_user"),
        		rs.getString("headline"),
        		rs.getString("industry"),
        		rs.getString("location"),
        		rs.getString("linkedin_url"),
        		rs.getString("summary"),
        		rs.getString("current_position"),
        		rs.getString("company_name")
        		// TODO: a esto le falta poner el country (hay que mapear el de linkedin con el de la db)
        		);
    }
}