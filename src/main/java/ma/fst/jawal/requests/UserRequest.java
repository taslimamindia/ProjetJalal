package ma.fst.jawal.requests;

import lombok.Data;
import lombok.ToString;

@Data @ToString
public class UserRequest {
    private String login;
    private String password;
    private String newPassword;
    private String firstName;
    private String lastName;
    private String email;
}
