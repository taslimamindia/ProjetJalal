package ma.fst.jawal.requests;

import lombok.Data;
import lombok.ToString;

@ToString @Data
public class AuthenticationRequest {
	private String login;
	private String password;
}
