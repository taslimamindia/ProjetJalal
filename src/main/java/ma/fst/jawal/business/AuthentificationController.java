package ma.fst.jawal.business;

import ma.fst.jawal.config.JWTTokenHelper;
import ma.fst.jawal.requests.AuthenticationRequest;
import ma.fst.jawal.responses.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthentificationController {

    private final AuthenticationManager authenticationManager;

    private final JWTTokenHelper jWTTokenHelper;

    public AuthentificationController(AuthenticationManager authenticationManager, JWTTokenHelper jWTTokenHelper) {
        this.authenticationManager = authenticationManager;
        this.jWTTokenHelper = jWTTokenHelper;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getLogin(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user=(User)authentication.getPrincipal();
        String jwtToken=jWTTokenHelper.generateToken(user.getUsername());
        LoginResponse response = new LoginResponse();
        response.setToken(jwtToken);
        response.setRoles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }

}

