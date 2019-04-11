package ar.com.tandilweb.byo.backend.utils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.StoredProcedure;

public class SpCall extends StoredProcedure {
	
	public SpCall(JdbcTemplate jdbcTemplate, String name) {
		super(jdbcTemplate, name);
		setFunction(false);
	}
	
}
