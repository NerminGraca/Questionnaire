package daos;

import com.avaje.ebean.Expr;

import models.User;

public class UserDAO extends EbeanBaseDAO<User> {
	
	public User findByUsername(String email) {
		return super.findUniqueByExpression(Expr.eq("email", email));
	}

	public User findByFacebookID(String fbId) {
		return super.findUniqueByExpression(Expr.eq("facebook_id", fbId));
	}

}
