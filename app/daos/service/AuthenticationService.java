package daos.service;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import daos.DAOFactory;
import helpers.BaseHelper;
import models.ApiKey;
import models.User;
import wrappers.PersistenceServiceException;

public class AuthenticationService extends DAOFactory {
	
	public ApiKey getOrGenerateAccessToken(User user) throws PersistenceServiceException {
		try {
			ApiKey apiKey = getApiKeyDAO().getByUserId(user.getId());
			if (apiKey != null) {
				if (apiKey.isTokenExpired()) {
					apiKey.setToken(generateUniqueAccessToken());
				}
				getApiKeyDAO().update(apiKey);
			} else {
				apiKey = new ApiKey(user, generateUniqueAccessToken());
				getApiKeyDAO().save(apiKey);
			}
			return apiKey;
		} catch (Exception e) {
			throw new PersistenceServiceException("ApiKeyService.generateResponseWithToken failed", e);
		}
	}
	
	private String generateUniqueAccessToken() throws IOException {
		String token = BaseHelper.random32Char();
		ApiKey apikey = getApiKeyDAO().getByToken(token);
		if (apikey != null) {
			token = generateUniqueAccessToken();
		}
		return token;
	}
	
	public User getAuthenticatedUser(String username, String password) throws PersistenceServiceException {
		try {
			User user = getUserDAO().findByUsername(username);
			if (user != null) {
				if (BCrypt.checkpw(password, user.getPasswordDigest())) {
					return user;
				}
			}
			return null;
		} catch (Exception e) {
			throw new PersistenceServiceException("ApiKeyService.getAuthenticatedUser failed", e);
		}
	}
	
	public User getAuthenticatedUser(String token) throws PersistenceServiceException {
		try {
			ApiKey apiKey = getApiKeyDAO().getByToken(token);
			return apiKey != null ? apiKey.getUser() : null;
		} catch (Exception e) {
			throw new PersistenceServiceException("ApiKeyService.getAuthenticatedUser failed", e);
		}
	}

}
