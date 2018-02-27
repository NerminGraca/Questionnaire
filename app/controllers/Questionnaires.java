package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import authenticators.AdminAuthenticator;
import authenticators.UserAuthenticator;
import daos.service.AnswerService;
import daos.service.OfferedAnswerService;
import daos.service.QuestionService;
import daos.service.QuestionnaireService;
import daos.service.UserQuestionnaireService;
import enums.QuestionType;
import helpers.SessionHelper;
import models.Answer;
import models.OfferedAnswer;
import models.Question;
import models.Questionnaire;
import models.SelectedOfferedAnswer;
import models.User;
import models.User.Login;
import models.UserQuestionnaire;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.completedpage;
import views.html.errorpage;
import views.html.login;
import views.html.successpage;
import views.html.questionnaire.*;
import views.html.questions.multipleChoiceQuestion;
import views.html.questions.singleChoiceQuestion;
import views.html.questions.textQuestion;
import views.html.questions.yesNoQuestion;
import wrappers.Answers;
import wrappers.PersistenceServiceException;

public class Questionnaires extends Controller {
	
	@Inject FormFactory formFactory;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(Questionnaires.class);
	
	private final QuestionnaireService questionnaireService = new QuestionnaireService();
	private final UserQuestionnaireService userQuestionnaireService = new UserQuestionnaireService();
	private final AnswerService answerService = new AnswerService();
	private final QuestionService questionService = new QuestionService();
	private final OfferedAnswerService offeredAnswerService = new OfferedAnswerService();

	@Security.Authenticated(UserAuthenticator.class)
	public Result questionnaires() {
		try{
			User user = SessionHelper.getAuthenticatedUser();
			if (user != null) {
				List<Questionnaire> qList = questionnaireService.getAllQuestionnaires();
				List<UserQuestionnaire> uqList = userQuestionnaireService.findByUser(user.getId());
				
				for (UserQuestionnaire uq : uqList) {
					qList.remove(uq.getQuestionnaire());
				}
				
				return ok(questionnaires.render(user, qList));
				
			} else {
				return ok(login.render(new Form<Login>(Login.class, null, null, null)));
			}		
		}
		catch(Exception e){
			LOGGER.error("Dashboard render failed", e);
			return badRequest("Dashboard render failed");
		}

    }

	@Security.Authenticated(UserAuthenticator.class)
	public Result questionnaire(Integer id) {
		
		try {
			Questionnaire questionnaire = questionnaireService.getQuestionnaireById(id);
			if (questionnaire != null) {
				User user = SessionHelper.getAuthenticatedUser();
				List<UserQuestionnaire> uqList = userQuestionnaireService.findByUser(user.getId());
				
				boolean finished = uqList.stream().map(UserQuestionnaire::getQuestionnaire).filter(questionnaire::equals).findFirst().isPresent();
				
				if (!finished) {
					return ok(views.html.questionnaire.questionnaire.render(questionnaire, user));
				}
				
				return ok(completedpage.render());
			}
			
			return ok(errorpage.render("Questionnaire does not exists"));
			
		} catch (PersistenceServiceException e) {
			LOGGER.error("Questionnaires.questionnaire failed", e);
			return ok(errorpage.render(e.getMessage()));
		}
		
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result createQuestionnaire() {
		return ok(createQuestionnaire.render());
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result submitQuestionnaire() {
		
			Form<Questionnaire> qForm = formFactory.form(Questionnaire.class);
			Form<Questionnaire> form = qForm.bindFromRequest();
			
			try {
				Questionnaire newQuestionnaire = form.get();
				
				questionnaireService.saveQuestionnaire(newQuestionnaire);
				
				return redirect(routes.Questionnaires.successpage("Questionnaire successfully created."));
				
			} catch(PersistenceServiceException e) {
				LOGGER.error("Questionnaires.submitQuestionnaire failed", e);
				return ok(errorpage.render(e.getMessage()));
			}
		
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result editQuestionnaire(Integer qId) {
		
		try {
			Questionnaire questionnaire = questionnaireService.getQuestionnaireById(qId);
			
			return ok(editQuestionnaire.render(questionnaire));
		} catch (PersistenceServiceException e) {
			LOGGER.error("Questionnaires.editQuestionnaire failed", e);
			return ok(errorpage.render(e.getMessage()));
		}
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result updateQuestionnaire(Integer qId) {
		
		Form<Questionnaire> qForm = formFactory.form(Questionnaire.class);
		Form<Questionnaire> form = qForm.bindFromRequest();
		
		try {
			Questionnaire updatedQuestionnaire = form.get();
			updatedQuestionnaire.setId(qId);
			
			questionnaireService.updateQuestionnaire(updatedQuestionnaire);
			
			return redirect(routes.Questionnaires.successpage("Questionnaire successfully edited."));
			
		} catch (PersistenceServiceException e) {
			LOGGER.error("Questionnaires.updateQuestionnaire failed", e);
			return ok(errorpage.render(e.getMessage()));
		}
		
		
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result deleteQuestionnaire(Integer qId) {
		
		try {
			questionnaireService.deleteQuestionnaire(qId);
			
			return redirect(routes.Questionnaires.questionnaires());
		} catch (PersistenceServiceException e) {
			LOGGER.error("Questionnaires.deleteQuestionnaire failed", e);
			return ok(errorpage.render(e.getMessage()));
		}
		
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result finishQuestionnaire() {
		
		Form<Answers> answersForm = formFactory.form(Answers.class);
		Form<Answers> form = answersForm.bindFromRequest();
		
		Answers answers = form.get();
		
		try {
			
			Questionnaire questionnaire = questionnaireService.getQuestionnaireById(answers.getQuestionnaireId());
			User user = SessionHelper.getAuthenticatedUser();
			
			UserQuestionnaire userQuestionnaire = new UserQuestionnaire();
			userQuestionnaire.setUser(user);
			userQuestionnaire.setQuestionnaire(questionnaire);
			
			userQuestionnaireService.save(userQuestionnaire);
			
			for (Answer answer : answers.getAnswers()) {
				
				Question question = questionService.getQuestionById(answer.getQuestionId());
				
				answer.setQuestion(question);
				answer.setUserQuestionnaire(userQuestionnaire);
				
				List<Integer> offeredAnswerIds = answer.getOfferedAnswerIds();
				if (offeredAnswerIds != null && !offeredAnswerIds.isEmpty()) {
					List<SelectedOfferedAnswer> selectedOfferedAnswers = new ArrayList<SelectedOfferedAnswer>();
					for (Integer i : offeredAnswerIds) {
						if (i != null) {
							OfferedAnswer offeredAnswer = offeredAnswerService.findById(i);
							
							SelectedOfferedAnswer selectedOfferedAnswer = new SelectedOfferedAnswer();
							selectedOfferedAnswer.setOfferedAnswer(offeredAnswer);
							
							selectedOfferedAnswers.add(selectedOfferedAnswer);
						}
					}
					answer.setSelectedOfferedAnswers(selectedOfferedAnswers);
				}
				
				answerService.saveAnswer(answer);
				
			}
			
			return redirect(routes.Questionnaires.successpage("Questionnaire successfully finished."));
			
		} catch (PersistenceServiceException e) {
			LOGGER.error("Questionnaires.finishQuestionnaire failed", e);
			return ok(errorpage.render(e.getMessage()));
		}
		
	}

	@Security.Authenticated(UserAuthenticator.class)
	public Result appendQuestion(Integer index, String selectedType) {
		
			QuestionType qt = QuestionType.valueOf(selectedType);
			
			if (qt.equals(QuestionType.TEXT_QUESTION)) {
				return ok(textQuestion.render(index, qt.name()));
			} else if (qt.equals(QuestionType.YES_NO_QUESTION)) {
				return ok(yesNoQuestion.render(index, qt.name()));
			} else if (qt.equals(QuestionType.SINGLE_CHOICE_QUESTION)) {
				return ok(singleChoiceQuestion.render(index, qt.name()));
			} else if (qt.equals(QuestionType.MULTIPLE_CHOICE_QUESTION)) {
				return ok(multipleChoiceQuestion.render(index, qt.name()));
			}
		
			return badRequest("Append question failed");
	}
	
	@Security.Authenticated(UserAuthenticator.class)
	public Result successpage(String message) {
		return ok(successpage.render(message));
	}

	@Security.Authenticated(AdminAuthenticator.class)
	public Result adminPanel() {
		
		try {
			List<Questionnaire> qList = questionnaireService.getAllQuestionnaires();
			List<UserQuestionnaire> uqList = userQuestionnaireService.findAll();
			
			return ok(views.html.admin.adminPanel.render(qList, uqList));
		} catch (PersistenceServiceException e) {
			LOGGER.error("Questionnaires.adminPanel failed", e);
			return ok(errorpage.render(e.getMessage()));
		}
		
	}
	
	@Security.Authenticated(AdminAuthenticator.class)
	public Result questionnaireResults(Integer uqId) {
		
		try {
			UserQuestionnaire userQuestionnaire = userQuestionnaireService.findById(uqId);
			
			List<Answer> answers = answerService.findByUserQuestionnaire(uqId);
			
			return ok(questionnaireResults.render(userQuestionnaire, answers));
		} catch (PersistenceServiceException e) {
			LOGGER.error("Questionnaires.questionnaireResults failed", e);
			return ok(errorpage.render(e.getMessage()));
		}
		
	}

}
