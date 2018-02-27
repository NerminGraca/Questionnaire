package helpers;

import com.google.inject.ImplementedBy;

@ImplementedBy(EmailServiceImpl.class)
public interface EmailService {
	void sendEmail(String email, String message);
}
