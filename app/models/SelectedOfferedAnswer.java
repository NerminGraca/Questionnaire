package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "selected_offered_answer")
public class SelectedOfferedAnswer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	@Column(name = "id")
	private Integer id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "offered_answer_id")
	private OfferedAnswer offeredAnswer;
	
	public SelectedOfferedAnswer() {}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public OfferedAnswer getOfferedAnswer() {
		return offeredAnswer;
	}
	public void setOfferedAnswer(OfferedAnswer offeredAnswer) {
		this.offeredAnswer = offeredAnswer;
	}

}
