package ar.com.tandilweb.byo.backend.Model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
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
import ar.com.tandilweb.byo.backend.Model.domain.EventGpsData;
import ar.com.tandilweb.byo.backend.Model.domain.GpsData;
import ar.com.tandilweb.byo.backend.Model.domain.Users;

@Repository
public class GpsDataRepository extends BaseRepository<GpsData, Long>{
	
	public static Logger logger = LoggerFactory.getLogger(GpsDataRepository.class);

	@Override
	public GpsData create(final GpsData record) {
		try {
			final String sql = "INSERT INTO gps_data"
					+ "(latitude, longitude, date_recorded) VALUES(?,?,?)";
			KeyHolder holder = new GeneratedKeyHolder();
			
			jdbcTemplate.update(new PreparedStatementCreator() {
	            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	                ps.setDouble(1, record.getLatitude());
	                ps.setDouble(2, record.getLongitude());
	                ps.setDate(3, new java.sql.Date(record.getDate_recorded().getTime()));
	                return ps;
	            }
	        }, holder);
			
			record.setId_gps_record(holder.getKey().longValue());
			return record;
		} catch(DataAccessException e) {
			logger.error("GpsDataRepository :: create",e);
			return null;
		}
	}

	@Override
	public void update(GpsData record) {
		try {
			final String sql = "UPDATE gps_data SET latitude = ?, longitude = ?, date_recorded = ? WHERE id_gps_record = ?";
			jdbcTemplate.update(sql, new Object[] {
					record.getLatitude(),
					record.getLongitude(),
					record.getDate_recorded(),
					record.getId_gps_record()
			});
		} catch(DataAccessException e) {
			logger.error("GpsDataRepository :: update",e);
		}
	}

	@Override
	public GpsData findById(Long id) {
		try {
			return jdbcTemplate.queryForObject(
	                "SELECT * FROM gps_data WHERE id_gps_record = ?",
	                new Object[]{id}, new GpsDataRowMapper());
		} catch(DataAccessException e) {
//			logger.debug("GpsDataRepository :: findById",e);
			return null;
		}
	}
	
	public GpsData findUserGpsData(Long id) {
		try {
			return jdbcTemplate.queryForObject(
	                "SELECT * FROM  gps_data WHERE id_gps_record= (SELECT MAX(id_gps_record) FROM gps_data_users WHERE id_user = ?)",
	                new Object[]{id}, new GpsDataRowMapper());
		} catch(DataAccessException e) {
//			logger.debug("GpsDataRepository :: findUserGpsData",e);
			return null;
		}
		
		
	}
	
	public GpsData findEventGpsData(Long id) {
		try {
			return jdbcTemplate.queryForObject(
	                "SELECT * FROM gps_data WHERE id_gps_record = ?",
	                new Object[]{id}, new GpsDataRowMapper());
		} catch(DataAccessException e) {
//			logger.debug("GpsDataRepository :: findUserGpsData",e);
			return null;
		}
		
		
	}
	
	
	
	public GpsData getOrCreate(double latitude, double longitude) {
		try {
			GpsData data = jdbcTemplate.queryForObject(
	                "SELECT * from gps_data WHERE latitude = ? AND longitude = ?",
	                new Object[]{latitude, longitude}, new GpsDataRowMapper());
			if(data == null || data.getDate_recorded() == null) {
				data = create(new GpsData(latitude, longitude, new Date()));
			}
			return data;
		} catch(DataAccessException e) {
			return create(new GpsData(latitude, longitude, new Date()));
		}
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
	
	public List<Users> getUsersClose(double lat, double lon, long radio, long me) {
		final String sql = "SELECT DISTINCT u.*"
				+ " FROM gps_data g, gps_data_users gu, users u"
				+ " LEFT JOIN friendships"
				+ " ON (friendships.id_user_target = u.id_user AND friendships.id_user_requester = ?)"
				+ " OR (friendships.id_user_requester = u.id_user AND friendships.id_user_target = ?)"
				+ " WHERE g.id_gps_record = gu.id_gps_record"
				+ " AND gu.id_user = u.id_user"
				+ " AND  ( 6371 * acos(cos(radians(?)) * cos(radians(latitude)) * cos(radians(longitude) - radians(?)) + sin(radians(?)) * sin(radians(latitude))))< ?"
				+ " AND ((id_user_requester <> ? AND id_user_target <> ? OR id_user_requester is NULL) AND u.id_user <> ?)";
		return jdbcTemplate.query(sql, new UserRowMapper(),new Object[]{me,me,lat,lon,lat,radio,me,me,me});
	}
	
	public List<EventGpsData> isInsideAnEvent(double lat, double lon) {
		List<EventGpsData>  out = new ArrayList<EventGpsData> ();
		final String sql = 
				"SELECT id_gps_record, (6371 * acos(cos(radians(?)) * cos(radians(latitude)) *" + 
				"cos(radians(longitude) - radians(?)) + sin(radians(?)) * sin(radians(latitude))))" + 
				"AS distance,latitude,longitude FROM gps_data_events ;";
		
	
		try {

			out = jdbcTemplate.query(sql, new GpsDataEventsRowMapper() , new Object[]{lat,lon,lat});
			
		} catch(Exception e) {
			logger.debug("EXCEPTION: " + e.getMessage());
			e.printStackTrace();
			logger.debug("OUT: "+ out.size());
		}
		return out;
		
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

class GpsDataEventsRowMapper implements RowMapper<EventGpsData>
{
    public EventGpsData mapRow(ResultSet rs, int rowNum) throws SQLException {

       return  new EventGpsData(
    		   rs.getLong("id_gps_record"),
        		rs.getDouble("latitude"),
        		rs.getDouble("longitude"),
        		rs.getDouble("distance")
        		);
        
     
    }
}
