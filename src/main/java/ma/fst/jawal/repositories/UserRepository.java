package ma.fst.jawal.repositories;

import ma.fst.jawal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
	public User findByLogin(String Login);
}
