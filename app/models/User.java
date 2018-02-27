package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	@Column(name = "id")
	private Integer id;
	
	@Required(message = "Please enter first name")
	private String firstname;
	
	@Required(message = "Please enter last name")
	private String lastname;
	
	@Email
	@Required(message = "Please enter email")
	private String email;

	@Transient
	@Required(message = "Please enter password")
	private String password;
	
	@Transient
    @Required(message = "Please enter password confirmation")
    private String passwordConfirmation;
	
	@Column(name = "password_digest")
    private String passwordDigest;
	
	@Column(name = "is_admin")
	private boolean isAdmin;
	
	@Column(name = "facebook_id")
	private String facebookId;
	
	public User() {}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	public String getPasswordDigest() {
		return passwordDigest;
	}
	public void setPasswordDigest(String passwordDigest) {
		this.passwordDigest = passwordDigest;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	@Transient
    public boolean isPasswordMatched() {
        return password != null && password.equals(passwordConfirmation);
    }

	public void setFacebookUserame(String fbUsername) {
		if (!fbUsername.isEmpty()) {
			String[] split = fbUsername.split(" ");
			if (split.length > 1) {
				firstname = split[0];
				lastname = split[1];
			} else if (split.length > 0) {
				firstname = split[0];
			}
		}
		
	}

	public static class Login {
        
		@Required(message = "Please enter email")
        private String email;
        @Required(message = "Please enter password")
        private String password;

        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }

    }
	
	public static class EmailReset {
		
		@Email
		@Required(message = "Please enter email")
        private String email;

		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
	}
	
	public static class NewPassword {
		
		@Required(message = "Please enter password")
		private String password;
		
	    @Required(message = "Please enter password confirmation")
	    private String passwordConfirmation;

		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getPasswordConfirmation() {
			return passwordConfirmation;
		}
		public void setPasswordConfirmation(String passwordConfirmation) {
			this.passwordConfirmation = passwordConfirmation;
		}
		
		public boolean isPasswordMatched() {
	        return password != null && password.equals(passwordConfirmation);
	    }
	    
	}
	
	
}
