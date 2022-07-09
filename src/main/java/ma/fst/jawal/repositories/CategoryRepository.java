package ma.fst.jawal.repositories;

import ma.fst.jawal.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select R from Category R where R.intitule = :x")
    Category findByIntitule(@Param("x") String intitule);
}
