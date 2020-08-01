package com.techelevator.FlowerADay;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {
	
	private final String SMTP_SERVER = "smtp.mailtrap.io";
	private final String USERNAME = "";
	private final String PASSWORD = "";
	
	private final String EMAIL_FROM = "jeff.t.manns@gmail.com";
	private final String EMAIL_TO = "jeff.t.manns@gmail.com";
	
	private final String EMAIL_SUBJECT = "Your Flower for Today!";
	
	

	public String getSMTP_SERVER() {
		return SMTP_SERVER;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public String getEMAIL_FROM() {
		return EMAIL_FROM;
	}

	public String getEMAIL_TO() {
		return EMAIL_TO;
	}

	public String getEMAIL_SUBJECT() {
		return EMAIL_SUBJECT;
	}
	
	public void sendEmail() {
		
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);  //possibly needs " "
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", SMTP_SERVER);
		prop.put("mail.smtp.port", "25");
		prop.put("mail.smtp.ssl.trust", SMTP_SERVER);
		
		Session session = Session.getInstance(prop, new Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});
		
		Message message = new MimeMessage(session);
		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		Multipart multipart = new MimeMultipart();
		try {
			message.setFrom(new InternetAddress(EMAIL_FROM));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));
			message.setSubject(EMAIL_SUBJECT);
			
			String currentMessage = "This is my test email using JavaMailer!";
			
			mimeBodyPart.setContent(currentMessage, "text/html");
			
			multipart.addBodyPart(mimeBodyPart);
			
			message.setContent(multipart);
			
			Transport.send(message);
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
	}
	
	

}
