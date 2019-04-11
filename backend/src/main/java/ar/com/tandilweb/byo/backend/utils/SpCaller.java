package ar.com.tandilweb.byo.backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class SpCaller {
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	protected String name;
	
	public SpCaller() { }
	
	public SpCall callSp(String name, SqlParameter[] params) {
		SpCall sp = new SpCall(jdbcTemplate, name);
		sp.setParameters(params);
		sp.compile();
		return sp;
	}
	
	public SqlParameter makeInputParameter(String value, int type) {
		return new SqlParameter(value, type);
	}
	
	public SqlOutParameter makeOutputParameter(String value, int type) {
		return new SqlOutParameter(value, type);
	}

}
