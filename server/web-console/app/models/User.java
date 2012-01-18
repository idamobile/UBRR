package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name="ida_users")
public class User extends Model{
	
	@Column(name="username")	
	public String username;
	
	@Column(name="password")
	public String password;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public static boolean connect(String username, String password) {
		if (User.count() == 0) {
			return "admin".equals(username) && "admin".equals(password);
		}
		
		return !User.find("byUsernameAndPassword", username, password).fetch().isEmpty();
	}
	
	@Override
	public String toString() {
		return username;
	}
	
	
	
	
}
