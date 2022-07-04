package ma.fst.jawal.requests;

import lombok.ToString;

@ToString
public class AuthenticationRequest {
	
	private String login;
	private String password;
	
	public String getLogin() {
		return login;
	}	
	public String getPassword() {
		return password;
	}
}
