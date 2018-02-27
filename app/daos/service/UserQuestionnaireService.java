package daos.service;

import java.util.List;

import daos.DAOFactory;
import models.UserQuestionnaire;
import wrappers.PersistenceServiceException;

public class UserQuestionnaireService extends DAOFactory {
	
	public void save(UserQuestionnaire userQuestionnaire) throws PersistenceServiceException {
		try {
			getUserQuestionnaireDAO().save(userQuestionnaire);
		} catch (Exception e) {
			throw new PersistenceServiceException("UserQuestionnaireService.save failed", e);
		}
		
	}
	
	public List<UserQuestionnaire> findByUser(Integer userId) throws PersistenceServiceException {
		try {
			return getUserQuestionnaireDAO().findByUser(userId);
		} catch (Exception e) {
			throw new PersistenceServiceException("UserQuestionnaireService.findByUser failed", e);
		}
		
	}
	
	public List<UserQuestionnaire> findAll() throws PersistenceServiceException {
		try {
			return getUserQuestionnaireDAO().findAll();
		} catch (Exception e) {
			throw new PersistenceServiceException("UserQuestionnaireService.findAll failed", e);
		}
		
	}

	public UserQuestionnaire findById(Integer uqId) throws PersistenceServiceException {
		try {
			return getUserQuestionnaireDAO().findById(uqId);
		} catch (Exception e) {
			throw new PersistenceServiceException("UserQuestionnaireService.findById failed", e);
		}
	}

}
