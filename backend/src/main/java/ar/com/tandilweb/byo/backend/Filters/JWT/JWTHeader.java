package ar.com.tandilweb.byo.backend.Filters.JWT;

import javax.servlet.http.HttpServletRequest;

import ar.com.tandilweb.byo.backend.Model.domain.Users;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.autoconfigure.DefaultGlobalContext;

public class JWTHeader {
	
	private boolean trusted;
	private Users user;
	private JWTUnpackedData data;
	
	public static JWTHeader getHeader(ResolutionEnvironment resolutionEnvironment) {
		JWTHeader out = new JWTHeader();
		try {
			DefaultGlobalContext dgc = resolutionEnvironment.dataFetchingEnvironment.getContext();
			HttpServletRequest request = dgc.getServletRequest();
			out.setUser((Users)request.getAttribute("jwtUserOrigin"));
			out.setData((JWTUnpackedData)request.getAttribute("jwtParsed"));
			out.setTrusted((Boolean)request.getAttribute("jwtTrusted"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	public boolean isTrusted() {
		return trusted;
	}

	public void setTrusted(Boolean trusted) {
		this.trusted = trusted;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public JWTUnpackedData getData() {
		return data;
	}

	public void setData(JWTUnpackedData data) {
		this.data = data;
	}
	
	

}
