package daos.service;

import java.io.IOException;

import daos.DAOFactory;
import helpers.BaseHelper;
import models.PasswordReset;
import wrappers.PersistenceServiceException;

public class PasswordResetService extends DAOFactory {
	
	public PasswordReset getPasswordResetByToken(String token) throws PersistenceServiceException {
		try {
			return getPasswordResetDAO().getByToken(token);
		} catch (Exception e) {
			throw new PersistenceServiceException("PasswordResetService.getPasswordResetByToken failed", e);
		}
		
	}
	
	public String savePasswordReset(PasswordReset pr) throws PersistenceServiceException {
		
		try {
			String token = generateUniqueAccessToken();
			pr.setToken(token);
			getPasswordResetDAO().save(pr);
			
			return token;
		} catch (IOException e) {
			throw new PersistenceServiceException("PasswordResetService.savePasswordReset failed", e);
		}
		
	}
	
	private String generateUniqueAccessToken() throws IOException {
		String token = BaseHelper.random32Char();
		PasswordReset ps = getPasswordResetDAO().getByToken(token);
		if (ps != null) {
			token = generateUniqueAccessToken();
		}
		return token;
	}

}
