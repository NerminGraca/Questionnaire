package daos;

import models.ApiKey;

public class ApiKeyDAO extends EbeanBaseDAO<ApiKey> {
	
	public ApiKey getByUserId(Integer userId) {
		return super.findUniqueByProperty("user.id", userId);
	}
	
	public ApiKey getByToken(String token) {
		return super.findUniqueByProperty("token", token);
	}

}
