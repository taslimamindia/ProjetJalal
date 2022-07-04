package ma.fst.jawal.responses;

import lombok.Data;
import ma.fst.jawal.entities.Authority;

import java.util.List;

@Data
public class LoginResponse {
	private List<Authority> roles;
	private String token;
}
