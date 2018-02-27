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

@Entity
@Table(name = "questionnaire")
public class Questionnaire {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	@Column(name = "id")
	private Integer id;
	
	private String title;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "questionnaire_id")
	private List<Question> questions;
	
	public Questionnaire() {	
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}
