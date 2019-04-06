package ar.com.tandilweb.byo.backend.Model.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ar.com.tandilweb.byo.backend.Model.BaseRepository;
import ar.com.tandilweb.byo.backend.Model.domain.Events;
import ar.com.tandilweb.byo.backend.Model.domain.GpsData;
import ar.com.tandilweb.byo.backend.Model.domain.Stands;
import ar.com.tandilweb.byo.backend.Model.domain.StandsCheckin;

@Repository
public class EventsRepository extends BaseRepository<Events, Long>{
	public static Logger logger = LoggerFactory.getLogger(FriendshipsRepository.class);
	
	@Autowired
	GpsDataRepository gpsDR;
	
	
	public List<Events> getEvents(double lat, double lon) {
		List<Events> out = new ArrayList<Events>();

		try {
	    	out= 	jdbcTemplate.query(
	                "SELECT * FROM events " , 
	                new EventsRowMapper(),
	                new Object[]{  });
		} catch(DataAccessException e) {
			e.printStackTrace();
			return null;
		}
		
		
		return out;
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
	
	
	public void setCheckin(long id_stand, long id_user) {
		try {
			final String sql = "INSERT INTO stands_checkin(id_stand,id_user,date_checkin) VALUES(?,?,?);";
			jdbcTemplate.update(sql, new Object[] { id_stand, id_user, new Date()});
		} catch (DataAccessException e) {
			logger.debug("EventsRepository :: setCheckin", e);
		}
	}
	public boolean verificarCodigoSinUso(long id_codigo, long id_user) {
		try {
			final String sql = "select exists (select *  from codigos_externos_usados where id_codigo = ? and id_user = ?);";
			return jdbcTemplate.queryForObject(sql, new Object[] { id_codigo, id_user,}, Boolean.class);
		} catch (DataAccessException e) {
			logger.debug("EventsRepository :: setCheckin", e);
		}
		return false;
	}
	public Long getIdCodigoEIdEvento(String codigo, long id_evento) {
		try {
			final String sql = "SELECT id_codigo FROM codigos_externos "
					+ "WHERE codigo_acceso = ? AND id_event = ?";
			return jdbcTemplate.queryForObject(sql, Long.class, new Object[] {codigo, id_evento});
			
		} catch (DataAccessException e) {
			logger.debug("EventsRepository :: getIdCodigo", e);
		}
		return 0l;
	}
	public void setCodigoComoUsado(Long id_codigo, long id_user, String date) {
		try {
			final String sql = "INSERT INTO codigos_externos_usados(id_codigo,id_user,fecha_activacion) VALUES(?,?,?);";
			jdbcTemplate.update(sql, new Object[] { id_codigo, id_user, date});
		} catch (DataAccessException e) {
			logger.debug("EventsRepository :: setCodigoComoUsado", e);
		}
		
	}
	public List<Long> getEventosRegistrado(long id_user) {
		try {
			final String sql = "SELECT id_event FROM codigos_externos ce INNER JOIN codigos_externos_usados ceu ON ce.id_codigo = ceu.id_codigo WHERE ceu.id_user = ?;";
			return jdbcTemplate.query(sql, new RowMapper<Long>(){ public Long mapRow(ResultSet rs, int rowNum) throws SQLException { return rs.getLong("id_event"); } }, new Object[] {id_user});
			
		} catch (DataAccessException e) {
			logger.debug("EventsRepository :: getEventosRegistrado", e);
		}
		return null;
	}
	
	
	
	class EventsRowMapper implements RowMapper<Events>
	{  EventsRepository er = new EventsRepository();
	    public Events mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Calendar cal_start = Calendar.getInstance();
	    	cal_start.setTime(rs.getTimestamp("start_date"));
	     	Calendar cal_end = Calendar.getInstance();
	    	cal_end.setTime(rs.getTimestamp("end_date"));

	    	
	        return new Events(
	        		rs.getLong("id_event"),
	        		cal_start,
	        		cal_end,
	        		rs.getString("name"),
	        		rs.getString("logo"),
	        		rs.getDouble("radio"),
	        		rs.getString("location_description"),
	        		getStands(rs.getLong("id_event")),
	        		rs.getLong("id_gps_record"),
	        		rs.getString("url"),
	        		rs.getString("nombre_lugar")
	        		);
	    }

	    	private List<Stands> getStands(Long event) {
	    		
	    		try {	
	    			return  jdbcTemplate.query("select * from stands where id_event = ?", new StandsRowMapper(), new Object[] {event});
	    		} catch (DataAccessException ex) {
	    			ex.printStackTrace();			
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
