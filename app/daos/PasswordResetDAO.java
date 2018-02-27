package daos;

import models.PasswordReset;

public class PasswordResetDAO extends EbeanBaseDAO<PasswordReset> {

	public PasswordReset getByToken(String token) {
		return super.findUniqueByProperty("token", token);
	}

}
