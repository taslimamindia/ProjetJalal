package ma.fst.jawal.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data @AllArgsConstructor
@NoArgsConstructor @ToString
public class LoginResponse {
	private List<String> roles;
	private String token;
}
