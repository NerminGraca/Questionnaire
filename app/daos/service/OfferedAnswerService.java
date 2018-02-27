package daos.service;

import daos.DAOFactory;
import models.OfferedAnswer;
import wrappers.PersistenceServiceException;

public class OfferedAnswerService extends DAOFactory {
	
	public void saveOfferedAnswer(OfferedAnswer offeredAnswer) throws PersistenceServiceException {
		try {
			getOfferedAnswerDAO().save(offeredAnswer);
		} catch (Exception e) {
			throw new PersistenceServiceException("OfferedAnswerService.saveOfferedAnswer failed", e);
		}
	}
	
	public OfferedAnswer findById(Integer id) throws PersistenceServiceException {
		try {
			return getOfferedAnswerDAO().findById(id);
		} catch (Exception e) {
			throw new PersistenceServiceException("OfferedAnswerService.findById failed", e);
		}
	}

}
