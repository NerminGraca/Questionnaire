package helpers;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.Configuration;

@Singleton
public class EmailServiceImpl implements EmailService {
	
	private final Configuration config;
	private final static Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Inject
    public EmailServiceImpl(Configuration config) {
        this.config = config;
    }
	
	public void sendEmail(String email, String message) {
		
		try {
			HtmlEmail mail = new HtmlEmail();
			mail.setSubject("Password reset");
			mail.setFrom("mop.questionnaire@gmail.com");
			mail.addTo(email);
			mail.setMsg(message);
			mail.setHtmlMsg(
					"<p>Click on provided link to reset password :</p>"
					+ message);
			mail.setHostName("smtp.gmail.com");
			mail.setStartTLSEnabled(true);
			mail.setSSLOnConnect(true);
			mail.setAuthenticator(new DefaultAuthenticator(
					config.getString("email.email"),
					config.getString("email.password")
			));
			mail.send();
		} catch (EmailException e) {
			LOGGER.error("MailHelper.sendEmail failed", e);
		}
	}

}
