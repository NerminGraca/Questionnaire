package daos.service;

import java.util.List;

import daos.DAOFactory;
import models.Answer;
import wrappers.PersistenceServiceException;

public class AnswerService extends DAOFactory {
	
	public void saveAnswer(Answer answer) throws PersistenceServiceException {
		try {
			getAnswerDAO().save(answer);
		} catch (Exception e) {
			throw new PersistenceServiceException("AnswerService.saveAnswer failed", e);
		}
	}
	
	public void saveAnswers(List<Answer> answers) throws PersistenceServiceException {
		try {
			getAnswerDAO().saveAll(answers);
		} catch (Exception e) {
			throw new PersistenceServiceException("AnswerService.saveAnswers failed", e);
		}
	}

	public List<Answer> findByUserQuestionnaire(Integer uqId) throws PersistenceServiceException {
		try {
			return getAnswerDAO().findByUserQuestionnaire(uqId);
		} catch (Exception e) {
			throw new PersistenceServiceException("AnswerService.findByUserQuestionnaire failed", e);
		}
	}

}
