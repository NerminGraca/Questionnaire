package models;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import helpers.DateHelper;

@Entity
@Table(name = "api_keys")
public class ApiKey implements ModelUpdater {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	@SequenceGenerator(name = "id", sequenceName = "MY_ENTITY_SEQ")
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "access_token")
	private String token;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "expires_at")
	private Timestamp expiresAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;

	@Transient
	private String userId;

	public ApiKey() {
	}
	
	public ApiKey(User user, String token) {
		this.user = user;
		this.token = token;
	}

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getToken() {
		return this.token;
	}
	public void setToken(String accessToken) {
		this.token = accessToken;
	}
	public Timestamp getCreatedAt() {
		return this.createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getExpiresAt() {
		return this.expiresAt;
	}
	public void setExpiresAt(Timestamp expiresAt) {
		this.expiresAt = expiresAt;
	}
	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public String getUserId() {
		return userId == null && user != null ? userId = user.getId().toString() : userId;
	}
	
	public void prepareForSaveOrUpdate() {
		Timestamp time = new Timestamp(DateHelper.getCurrentUTCDate().getTime());
		if (createdAt == null) {
			createdAt = time;
		}
		updatedAt = time;
		if (expiresAt == null) {
			expiresAt = new Timestamp(DateHelper.getDateFromReferenceDate(time.getTime(), 5));
		}
	}
	
	@Transient
	public boolean isTokenExpired() {
		return DateHelper.getCurrentUTCDate().getTime() > expiresAt.getTime();
	}

}
