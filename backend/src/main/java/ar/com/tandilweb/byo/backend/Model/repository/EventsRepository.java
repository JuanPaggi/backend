package ar.com.tandilweb.byo.backend.Model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ar.com.tandilweb.byo.backend.Model.BaseRepository;
import ar.com.tandilweb.byo.backend.Model.domain.Events;
import ar.com.tandilweb.byo.backend.Model.domain.Stands;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.VCardEvento;

@Repository
public class EventsRepository extends BaseRepository<Events, Long>{
	public static Logger logger = LoggerFactory.getLogger(FriendshipsRepository.class);
	
	
	
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
	public Integer getCheckin(Integer id_stand, long id_user) {
		try {
	    	return jdbcTemplate.queryForObject(
	                "SELECT COUNT(*) FROM stands_checkin WHERE id_stand = ? AND id_user = ? ", 
	                new Object[]{ id_stand, id_user },
	                Integer.class);
		} catch(DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public List<Stands> getStands(Long event) {
		
		try {	
			return  jdbcTemplate.query("select * from stands where id_event = ?", new StandsRowMapper(), new Object[] {event});
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
	    	String fecha_start = this.cambiarFecha(cal_start);
	    	String fecha_end = this.cambiarFecha(cal_end);
	    	
	    	//List<Stands> stands = er.getStands(rs.getLong("id_event"));
	        return new Events(
	        		rs.getLong("id_event"),
	        		cal_start,
	        		cal_end,
	        		fecha_start,
	        		fecha_end,
	        		rs.getString("name"),
	        		rs.getString("logo"),
	        		rs.getInt("id_gps_record"),
	        		rs.getString("location_description")//,
	        	//	stands
	        		);
	    }
	    	private String cambiarFecha(Calendar fecha) {
    	
	    		   String[] CurrntMonth = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" };
	    		   return fecha.get(Calendar.DAY_OF_MONTH) + " de " + CurrntMonth[fecha.get(Calendar.MONTH)] + " de " + fecha.get(Calendar.YEAR);
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

	
	

}
