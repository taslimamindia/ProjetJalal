package ma.fst.jawal.business;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import ma.fst.jawal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.fst.jawal.config.JWTTokenHelper;
import ma.fst.jawal.entities.User;
import ma.fst.jawal.requests.AuthenticationRequest;
import ma.fst.jawal.responses.LoginResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthentificationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenHelper jWTTokenHelper;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getLogin(),
                        authenticationRequest.getPassword()
                )
        );

//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        User user=(User)authentication.getPrincipal();
//        System.out.println(user);
//        String jwtToken=jWTTokenHelper.generateToken(user.getUsername());

        LoginResponse response = new LoginResponse();
//        response.setToken(jwtToken);
//        response.setRoles(user.getAuthorities());
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/auth/checklogin")
//    public boolean checkLogin(@RequestBody AuthenticationRequest login) {
//        User user = userRepository.findByLogin(login.getLogin());
//        if(user == null) return false;
//        return true;
//    }
}

