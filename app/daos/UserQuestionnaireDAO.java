package daos;

import java.util.List;

import com.avaje.ebean.Expr;

import models.UserQuestionnaire;

public class UserQuestionnaireDAO extends EbeanBaseDAO<UserQuestionnaire> {
	
	public List<UserQuestionnaire> findByUser(Integer userId) {
		return super.findByExpression(Expr.eq("user_id", userId));
	}

}
