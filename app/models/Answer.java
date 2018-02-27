package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "answer")
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	@Column(name = "id")
	private Integer id;
	
	@Transient
	private Integer questionId;
	
	@Transient
	private List<Integer> offeredAnswerIds;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "question_id")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "user_questionnaire_id")
	private UserQuestionnaire userQuestionnaire;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "question_id")
	private List<SelectedOfferedAnswer> selectedOfferedAnswers;
	
	@Column(columnDefinition = "TEXT", name="text_answer")
	private String textAnswer;
	
	private boolean isYes;
	
	public Answer() {}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public List<Integer> getOfferedAnswerIds() {
		return offeredAnswerIds;
	}
	public void setOfferedAnswerIds(List<Integer> offeredAnswerIds) {
		this.offeredAnswerIds = offeredAnswerIds;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public UserQuestionnaire getUserQuestionnaire() {
		return userQuestionnaire;
	}
	public void setUserQuestionnaire(UserQuestionnaire userQuestionnaire) {
		this.userQuestionnaire = userQuestionnaire;
	}
	public List<SelectedOfferedAnswer> getSelectedOfferedAnswers() {
		return selectedOfferedAnswers;
	}
	public void setSelectedOfferedAnswers(List<SelectedOfferedAnswer> selectedOfferedAnswers) {
		this.selectedOfferedAnswers = selectedOfferedAnswers;
	}
	public String getTextAnswer() {
		return textAnswer;
	}
	public void setTextAnswer(String textAnswer) {
		this.textAnswer = textAnswer;
	}
	public boolean isYes() {
		return isYes;
	}
	public void setisYes(boolean isYes) {
		this.isYes = isYes;
	}

}
