package wrappers;

import java.util.List;

import models.Answer;

public class Answers {
	
	private Integer questionnaireId;
	private List<Answer> answers;

	public Integer getQuestionnaireId() {
		return questionnaireId;
	}
	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

}
