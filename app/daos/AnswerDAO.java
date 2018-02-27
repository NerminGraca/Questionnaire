package daos;

import java.util.List;

import com.avaje.ebean.Expr;

import models.Answer;

public class AnswerDAO extends EbeanBaseDAO<Answer> {

	public List<Answer> findByUserQuestionnaire(Integer uqId) {
		return super.findByExpression(Expr.eq("user_questionnaire_id", uqId));
	}

}
