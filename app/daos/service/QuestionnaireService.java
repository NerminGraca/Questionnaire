package daos.service;

import java.util.List;

import daos.DAOFactory;
import models.Questionnaire;
import wrappers.PersistenceServiceException;

public class QuestionnaireService extends DAOFactory {
	
	public List<Questionnaire> getAllQuestionnaires() throws PersistenceServiceException {
		try {
			return getQuestionaireDAO().findAll();
		} catch (Exception e) {
			throw new PersistenceServiceException("QuestionnaireService.getAllQuestionnaires failed", e);
		}
	}
	
	public void saveQuestionnaire(Questionnaire newQuestionnaire) throws PersistenceServiceException {
		try {
			getQuestionaireDAO().save(newQuestionnaire);
		} catch (Exception e) {
			throw new PersistenceServiceException("QuestionnaireService.getAllQuestionnaires failed", e);
		}
		
	}
	
	public Questionnaire getQuestionnaireById(Integer qId) throws PersistenceServiceException {
		try {
			return getQuestionaireDAO().findById(qId);
		} catch (Exception e) {
			throw new PersistenceServiceException("QuestionnaireService.getQuestionnaireById failed", e);
		}
	}
	
	public void deleteQuestionnaire(Integer qId) throws PersistenceServiceException {
		try {
			getQuestionaireDAO().deleteById(qId);
		} catch (Exception e) {
			throw new PersistenceServiceException("QuestionnaireService.deleteQuestionnaire failed", e);
		}
	}
	
	public void updateQuestionnaire(Questionnaire questionnaire) throws PersistenceServiceException {
		try{
			getQuestionaireDAO().update(questionnaire);
		} catch (Exception e) {
			throw new PersistenceServiceException("QuestionnaireService.updateQuestionnaire failed", e);
		}
	}

}
