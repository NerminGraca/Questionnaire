package daos.service;

import daos.DAOFactory;
import models.Question;
import wrappers.PersistenceServiceException;

public class QuestionService extends DAOFactory {
	
	public Question getQuestionById(Integer qid) throws PersistenceServiceException {
		try {
			return getQuestionDAO().findById(qid);
		} catch (Exception e) {
			throw new PersistenceServiceException("QuestionService.getQuestionById failed", e);
		}
	}

}
