package ar.com.tandilweb.byo.backend.Model.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ar.com.tandilweb.byo.backend.Model.BaseRepository;
import ar.com.tandilweb.byo.backend.Model.domain.Profile;

@Repository
public class ProfileRepository extends BaseRepository<Profile, Long>{
	
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
		// TODO Función pendiente
		return null;
	}

	@Override
	public void update(Profile record) {
		// TODO Función pendiente
		
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
        		rs.getString("summary")
        		// TODO: a esto le falta poner el country (hay que mapear el de linkedin con el de la db)
        		);
    }
}