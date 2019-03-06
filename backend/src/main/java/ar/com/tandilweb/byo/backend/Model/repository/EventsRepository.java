package ar.com.tandilweb.byo.backend.Model.repository;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ar.com.tandilweb.byo.backend.Model.BaseRepository;
import ar.com.tandilweb.byo.backend.Model.domain.Events;
import ar.com.tandilweb.byo.backend.Model.domain.GpsData;
import ar.com.tandilweb.byo.backend.Model.domain.Stands;
import ar.com.tandilweb.byo.backend.Model.domain.StandsCheckin;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.VCardEvento;

@Repository
public class EventsRepository extends BaseRepository<Events, Long>{
	public static Logger logger = LoggerFactory.getLogger(FriendshipsRepository.class);
	@Autowired
	GpsDataRepository gpsDR = new GpsDataRepository();
	
	
	public List<Events> getEvents() {
		try {
	    	return 	jdbcTemplate.query(
	                "SELECT * FROM events " , 
	                new EventsRowMapper(),
	                new Object[]{  });
		} catch(DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<StandsCheckin> getCheckins(long id_user) {
		try {
	    	return jdbcTemplate.query(
	                "SELECT * FROM stands_checkin WHERE id_user = ? ", 
	                new checkinsRowMapper(),
	                new Object[]{ id_user });
		} catch(DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	
	public List<Stands> getStands(Long event) {
	
		try {	
			return jdbcTemplate.query("select * from stands where id_event = ?", new StandsRowMapper(), new Object[] {event});
		
		} catch (DataAccessException e) {
			e.printStackTrace();			
			return null;
		}
	}

	
	





	@Override
	public Events create(Events record) {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public void update(Events record) {
		// TODO Auto-generated method stub
		
	}

		
	


	@Override
	public Events findById(Long id) {
		try {
			return jdbcTemplate.queryForObject("select * from events where id_event=?", new Object[] { id },
					new EventsRowMapper());
		} catch (DataAccessException e) {
			return null;
		}
	}
	
	class EventsRowMapper implements RowMapper<Events>
	{  EventsRepository er = new EventsRepository();
	    public Events mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Calendar cal_start = Calendar.getInstance();
	    	cal_start.setTime(rs.getTimestamp("start_date"));
	     	Calendar cal_end = Calendar.getInstance();
	    	cal_end.setTime(rs.getTimestamp("end_date"));
	    	logger.debug("CALENDARR:: :: : ::"+rs.getLong("id_gps_record"));
			GpsData gps = this.findEventGpsData(rs.getLong("id_gps_record"));
			logger.debug("GPS:: :: : ::"+gps.getLatitude());
	    	
	        return new Events(
	        		rs.getLong("id_event"),
	        		cal_start,
	        		cal_end,
	        		rs.getString("name"),
	        		rs.getString("logo"),
	        		gps,
	        		rs.getString("location_description"),
	        		getStands(rs.getLong("id_event"))
	        		);
	    }

	    	private List<Stands> getStands(Long event) {
	    		
	    		try {	
	    			return  jdbcTemplate.query("select * from stands where id_event = ?", new StandsRowMapper(), new Object[] {event});
	    		} catch (DataAccessException e) {
	    			e.printStackTrace();			
	    			return null;
	    		}
	    	}
	    	private GpsData findEventGpsData(Long id) {
	    		try {
	    			return jdbcTemplate.queryForObject(
	    	                "SELECT * FROM gps_data WHERE id_gps_record = ?",
	    	                new Object[]{id}, new GpsDataRowMapper());
	    		} catch(DataAccessException e) {
//	    			logger.debug("GpsDataRepository :: findUserGpsData",e);
	    			return null;
	    		}
	    		
	    		
	    	}
	    	
	}
	
	class StandsRowMapper implements RowMapper<Stands>
	{  
	    public Stands mapRow(ResultSet rs, int rowNum) throws SQLException {
	        return new Stands(
	        		rs.getLong("id_stand"),
	        		rs.getLong("id_event"),
	        		rs.getString("name"),
	        		rs.getString("logo"),
	        		rs.getInt("id_user_organizer")
	        		);
	    }
	   	    
	}
	
	class checkinsRowMapper implements RowMapper<StandsCheckin>
	{  
	    public StandsCheckin mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Calendar date = Calendar.getInstance();
	    	date.setTime(rs.getTimestamp("date_checkin"));
	        return new StandsCheckin(
	        		rs.getLong("id_stand"),
	        		rs.getLong("id_user"),
	        		date
	        		);
	    }
	    
	    
	}
	class GpsDataRowMapper implements RowMapper<GpsData>
	{
	    public GpsData mapRow(ResultSet rs, int rowNum) throws SQLException {
	        return new GpsData(
	        		rs.getLong("id_gps_record"),
	        		rs.getDouble("longitude"), //hay que ver por que queda al reves
	        		rs.getDouble("latitude"),
	        		rs.getDate("date_recorded")
	        		);
	    }
	}

	
	

}
