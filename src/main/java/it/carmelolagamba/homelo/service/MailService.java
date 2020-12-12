package it.carmelolagamba.homelo.service;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import it.carmelolagamba.homelo.config.EmailProperties;
import it.carmelolagamba.homelo.dto.CheckDetails;
import it.carmelolagamba.homelo.dto.CheckInfo;

@Component
@EnableConfigurationProperties({ EmailProperties.class })
public class MailService {

	private Logger logger = LoggerFactory.getLogger(MailService.class);

	@Autowired
	private EmailProperties emailProperties;

	public void send(CheckInfo info, List<String> to) {
		logger.debug("Start sending mail");

		String subject = "[Homelo]: Allarme in corso";

		StringBuilder builder = new StringBuilder();
		builder.append("Ciao, <br><br>");
		builder.append(
				"i sensori dell'appartamento " + info.getHome() + ", hanno rilevato i seguenti allarmi:<br><br>");

		for (CheckDetails detail : info.getInfo()) {
			builder.append("<b>Stanza:</b> " + detail.getRoom() + "<br>");
			builder.append("<b>Sensore:</b> " + detail.getMetric().getValue() + "<br>");
			builder.append("<b>Data:</b> " + detail.getDate() + "<br>");
			builder.append("<b>Valore ricevuto:</b> " + detail.getCurrentValue() + "<br>");
			builder.append("<hr><br>");
		}
		
		builder.append("A presto,<br>Homelo");

		_send(to, subject, builder.toString());
		
		logger.debug("End sending mail");
	}

	private void _send(List<String> to, String subject, String content) {
		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.put("mail.smtp.host", emailProperties.getSmtpHost());
		properties.put("mail.smtp.port", emailProperties.getSmtpPort());
		properties.put("mail.smtp.ssl.enable", emailProperties.getSmtpSSLEnabled());
		properties.put("mail.smtp.auth", emailProperties.getSmtpAuth());

		// Get the Session object.// and pass username and password
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailProperties.getUser(), emailProperties.getPassword());
			}

		});

		// Used to debug SMTP issues
		session.setDebug(false);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(emailProperties.getUser()));

			for (String _to : to) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(_to));
			}

			message.setSubject(subject);
			message.setContent(content, "text/html");

			logger.info("Sending email {} to {}", subject, to);
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
