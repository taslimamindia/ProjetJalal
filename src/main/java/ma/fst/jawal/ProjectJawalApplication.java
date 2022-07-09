package ma.fst.jawal;

import ma.fst.jawal.entities.Authority;
import ma.fst.jawal.entities.User;
import ma.fst.jawal.repositories.UserRepository;
import ma.fst.jawal.services.accounts.AccountImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Date;

@SpringBootApplication
public class ProjectJawalApplication {
	@Autowired
	UserRepository userRepository;
	@Autowired
	AccountImp accountImp;

	public static void main(String[] args) {
		SpringApplication.run(ProjectJawalApplication.class, args);
	}

	@PostConstruct
	public void init() {
		User user = userRepository.findByLogin("diallo");
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		if(user == null) {
			// Add user.
			user = new User();
			user.setLogin("diallo");
			user.setActive(true);
			user.setCreated_at(new Date());
			user.setUpdate_at(new Date());
			user.setPwd(encode.encode("1234"));
			accountImp.addUser(user);

			// Add roles.
			accountImp.addRole(new Authority(null, "ADMIN"));
			accountImp.addRole(new Authority(null, "USER"));

			// Affect Role to a user.
			accountImp.addRoleToUser("diallo", "ADMIN");
			accountImp.addRoleToUser("diallo", "USER");
		} else {
			user.setUpdate_at(new Date());
			user.setPwd(encode.encode("1234"));
			userRepository.save(user);
		}
	}
}
