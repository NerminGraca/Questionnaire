package daos;

public class DAOFactory {
	
	private UserDAO userDao;
	private ApiKeyDAO apiKeyDao;
	private QuestionaireDAO questionaireDAO;
	private UserQuestionnaireDAO userQuestionnaireDAO;
	private AnswerDAO answerDAO;
	private QuestionDAO questionDAO;
	private OfferedAnswerDAO offeredAnswerDAO;
	private PasswordResetDAO passwordResetDAO;

	public UserDAO getUserDAO() {
		return userDao == null ? userDao = new UserDAO() : userDao;
	}
	
	public ApiKeyDAO getApiKeyDAO() {
		return apiKeyDao == null ? apiKeyDao = new ApiKeyDAO() : apiKeyDao;
	}
	
	public QuestionaireDAO getQuestionaireDAO() {
		return questionaireDAO == null ? questionaireDAO = new QuestionaireDAO() : questionaireDAO;
	}
	
	public UserQuestionnaireDAO getUserQuestionnaireDAO() {
		return userQuestionnaireDAO == null ? userQuestionnaireDAO = new UserQuestionnaireDAO() : userQuestionnaireDAO;
	}
	
	public AnswerDAO getAnswerDAO() {
		return answerDAO == null ? answerDAO = new AnswerDAO() : answerDAO;
	}
	
	public QuestionDAO getQuestionDAO() {
		return questionDAO == null ? questionDAO = new QuestionDAO() : questionDAO;
	}
	
	public OfferedAnswerDAO getOfferedAnswerDAO() {
		return offeredAnswerDAO == null ? offeredAnswerDAO = new OfferedAnswerDAO() : offeredAnswerDAO;
	}
	
	public PasswordResetDAO getPasswordResetDAO() {
		return passwordResetDAO == null ? passwordResetDAO = new PasswordResetDAO() : passwordResetDAO;
	}

}
