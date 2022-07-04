package ma.fst.jawal.repositories;

import ma.fst.jawal.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    public Authority findByRole(String role);
}
