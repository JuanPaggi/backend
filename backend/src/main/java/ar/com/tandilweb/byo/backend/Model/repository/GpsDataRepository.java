package ar.com.tandilweb.byo.backend.Model.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import ar.com.tandilweb.byo.backend.Model.BaseRepository;
import ar.com.tandilweb.byo.backend.Model.domain.GpsData;

public class GpsDataRepository extends BaseRepository<GpsData, Long>{

	@Override
	public GpsData create(GpsData record) {
		final String sql = "INSERT INTO gps_data"
				+ "(latitude, longitude, date_recorded) VALUES(?,?,?,?)";
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, new Object[] {
				record.getLatitude(),
				record.getLongitude(),
				record.getDate_recorded()
				});
		record.setId_gps_record(holder.getKey().longValue());
		return record;
	}

	@Override
	public void update(GpsData record) {
		final String sql = "UPDATE gps_data SET latitude = ?, longitude = ?, date_recorded = ? WHERE id_gps_record = ?";
		jdbcTemplate.update(sql, new Object[] {
				record.getLatitude(),
				record.getLongitude(),
				record.getDate_recorded(),
				record.getId_gps_record()
		});
	}

	@Override
	public GpsData findById(Long id) {
		return jdbcTemplate.queryForObject(
                "SELECT * FROM gps_data WHERE id_gps_record = ?",
                new Object[]{id}, new GpsDataRowMapper());
	}
	
	public GpsData getOrCreate(double latitude, double longitude) {
		GpsData data = jdbcTemplate.queryForObject(
                "SELECT * from gps_data WHERE latitude = ? AND longitude = ?",
                new Object[]{latitude, longitude}, new GpsDataRowMapper());
		if(data == null) {
			data = create(new GpsData(latitude, longitude, new Date()));
		}
		return data;
	}
	
	public void assocPositionWithUser(long idGpsRecord, long id_user) {
		try {
			final String sql = "INSERT INTO gps_data_users(id_gps_record, id_user) VALUES(?,?)";
			jdbcTemplate.update(sql, new Object[] {
				idGpsRecord,
				id_user
				// actualizar fecha
			});
		} catch(DataAccessException e) {
			// existing association (actualizar fecha)
		}
	}

}

class GpsDataRowMapper implements RowMapper<GpsData>
{
    public GpsData mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new GpsData(
        		rs.getLong("id_gps_record"),
        		rs.getDouble("latitude"),
        		rs.getDouble("longitude"),
        		rs.getDate("date_recorded")
        		);
    }
}