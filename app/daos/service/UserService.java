package daos.service;

import daos.DAOFactory;
import models.User;
import wrappers.PersistenceServiceException;

public class UserService extends DAOFactory {
	
	public boolean isExistingUser(String email) throws PersistenceServiceException {
		try {
			return getUserDAO().findByUsername(email) != null;
		} catch (Exception e) {
			throw new PersistenceServiceException("UserService.isExistingUser failed", e);
		}
	}
	
	public void saveUser(User newUser) throws PersistenceServiceException {
		try {
			getUserDAO().save(newUser);
		} catch (Exception e) {
			throw new PersistenceServiceException("UserService.saveUser failed", e);
		}
	}
	
	public User getUserById(Integer userId) throws PersistenceServiceException {
		try {
			return getUserDAO().findById(userId);
		} catch (Exception e) {
			throw new PersistenceServiceException("UserService.getUserById failed", e);
		}
	}

	public User getUserByEmail(String email) throws PersistenceServiceException {
		try {
			return getUserDAO().findByUsername(email);
		} catch (Exception e) {
			throw new PersistenceServiceException("UserService.getUserByEmail failed", e);
		}
	}

	public User getUserByFacebookID(String fbId) throws PersistenceServiceException {
		try {
			return getUserDAO().findByFacebookID(fbId);
		} catch (Exception e) {
			throw new PersistenceServiceException("UserService.getUserByFacebookID failed", e);
		}
	}

}
