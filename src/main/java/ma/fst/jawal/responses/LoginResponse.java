package ma.fst.jawal.responses;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
	private List<String> roles;
	private String token;
}
