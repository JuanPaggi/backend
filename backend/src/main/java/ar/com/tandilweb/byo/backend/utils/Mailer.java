package ar.com.tandilweb.byo.backend.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.smtp.SMTPTransport;

public class Mailer {
	
	public static Logger logger = LoggerFactory.getLogger(Mailer.class);
	
	public static String host = "server1.tandilserver.com";
	public static String from = "mails@tandilweb.com.ar";
	public static String user = "mails@tandilweb.com.ar";
	public static String password = "123456789*";
	
	public static void send(String to, String sub, String msg) {
	    Properties prop = System.getProperties();
	    prop.put("mail.smtp.host", host);
	    prop.put("mail.debug", "true");
	    prop.put("mail.smtp.auth", true);
	    prop.put("mail.smtp.port", "587");
	    prop.put("mail.smtp.ssl.enable", true);
	    prop.put("mail.smtp.starttls.enable", true);
	    prop.put("mail.smtp.ssl.trust", "*");
	    prop.put("mail.smtp.ssl.checkserveridentity", false);
	    
	    Session session = Session.getInstance(prop);
	    
	    MimeMessage message = new MimeMessage(session);
	    try {
	        message.setFrom(new InternetAddress(from));
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	        message.setSubject(sub);
	        message.setText(msg);
	        
	        SMTPTransport t = (SMTPTransport)session.getTransport("smtp");
	        t.connect(host, user, password);
	        t.sendMessage(message, message.getAllRecipients());
	        t.close();
	        
	        System.out.println("Email sent to "+to);
	    } catch (MessagingException ex) {
	    	logger.error("Error enviando mail", ex);
	    }
	}
	
}
