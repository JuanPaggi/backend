package ar.com.tandilweb.byo.backend.utils;

import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import com.sun.mail.smtp.SMTPTransport;

public class Mailer {
	
	public static Logger logger = LoggerFactory.getLogger(Mailer.class);
	
	@Value("${mailer.host}")
	public String host = "";
	
	@Value("${mailer.from}")
	public String from = "";
	
	@Value("${mailer.user}")
	public String user = "";
	
	@Value("${mailer.password}")
	public String password = "";
	
	public void send(String to, String sub, String msg) {
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
	        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
	        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
	        CommandMap.setDefaultCommandMap(mc);
	        SMTPTransport t = (SMTPTransport)session.getTransport("smtp");
	        t.connect(host, user, password);
	        message.setContent(msg, "text/html");
	        t.sendMessage(message, message.getAllRecipients());
	        t.close();
	        
	        System.out.println("Email sent to "+to);
	    } catch (MessagingException ex) {
	    	logger.error("Error enviando mail", ex);
	    }
	}
	
}
