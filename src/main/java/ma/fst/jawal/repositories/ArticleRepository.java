package ma.fst.jawal.repositories;

import ma.fst.jawal.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query(value = "select R from Article R where R.description = :x")
    Article findByDescription(@Param("x") String description);

    @Query(value = "select R from Article R where R.reference = :x")
    Article findByReference(@Param("x") String reference);

    @Query(value = "select R from Article R where R.categorie.id = :x")
    List<Article> findByCategory(@Param("x") Long categoryId);
}
