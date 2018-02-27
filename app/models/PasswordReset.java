package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import helpers.DateHelper;

@Entity
@Table(name = "password_resets")
public class PasswordReset implements ModelUpdater {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	@SequenceGenerator(name = "id", sequenceName = "MY_ENTITY_SEQ")
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "token")
	private String token;

	@Column(name = "expires_at")
	private Timestamp expiresAt;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Timestamp getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(Timestamp expiresAt) {
		this.expiresAt = expiresAt;
	}
	
	public void prepareForSaveOrUpdate() {
		Timestamp time = new Timestamp(DateHelper.getCurrentUTCDate().getTime());
		if (expiresAt == null) {
			expiresAt = new Timestamp(DateHelper.getDateFromReferenceDateInMinutes(time.getTime(), 5));
		}
	}
	
	@Transient
	public boolean isTokenExpired() {
		return DateHelper.getCurrentUTCDate().getTime() > expiresAt.getTime();
	}

}
