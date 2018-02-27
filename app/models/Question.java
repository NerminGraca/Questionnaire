package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import enums.QuestionType;

@Entity
@Table(name = "question")
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	@Column(name = "id")
	private Integer id;
	
	private Integer index;
	
	@Column(columnDefinition = "TEXT")
	private String text;
	
	private QuestionType type;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "question_id")
	private List<OfferedAnswer> offeredAnswers;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "question_id")
	private List<Answer> answers;
	
	public Question() {}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public QuestionType getType() {
		return type;
	}
	public void setType(QuestionType type) {
		this.type = type;
	}
	public List<OfferedAnswer> getOfferedAnswers() {
		return offeredAnswers;
	}
	public void setOfferedAnswers(List<OfferedAnswer> offeredAnswers) {
		this.offeredAnswers = offeredAnswers;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

}
