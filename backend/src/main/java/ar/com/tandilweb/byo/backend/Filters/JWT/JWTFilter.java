package ar.com.tandilweb.byo.backend.Filters.JWT;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JWTFilter implements Filter {

	private static final Logger log = LoggerFactory.getLogger(JWTFilter.class);

	public void destroy() {
		// TODO Auto-generated method stub

	}

	class ExceptionJWT extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		log.debug("Filter JWT Appl");
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		// httpRequest.getHeaderNames();
		String jwToken = httpRequest.getHeader("Authorization");
		String identity = httpRequest.getHeader("Identity");
		try {
			if(identity == null) throw new ExceptionJWT();
			if(jwToken == null) throw new ExceptionJWT();
			// secret traerlo usando el identity
			String key = new String(Base64.encodeBase64("secret".getBytes()));
			Jws<Claims> jt = Jwts.parser().setSigningKey(key).parseClaimsJws(jwToken);
			Claims data = jt.getBody();
			if(!identity.equals(data.getSubject())) throw new ExceptionJWT();
			JWTUnpackedData uD = new JWTUnpackedData();
			uD.setExpiration(data.getExpiration()); // fecha de expiración
			uD.setId(data.getId()); // tracking
			uD.setIssuedAt(data.getIssuedAt()); // fecha de creación
			uD.setIssuer(data.getIssuer()); // canal
			uD.setNotBefore(data.getNotBefore()); // fecha actual
			uD.setSubject(data.getSubject()); // identificador de usuario.
			httpRequest.setAttribute("jwtParsed", uD);
			filterChain.doFilter(httpRequest, httpResponse);
		} catch (ExceptionJWT e) {
			// TODO Auto-generated catch block
			log.error("JWT token isnt detected in headers");
			httpResponse.setHeader("Authorization-Requested", "jwt.0.14.1");
			httpResponse.setStatus(403);
		} catch (SignatureException e) {
			log.error("JWT token Signature Exception", e);
			httpResponse.setHeader("Authorization-Requested", "jwt.0.14.1");
			httpResponse.setStatus(403);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		log.debug("Filter JWT Initialized");
	}

}