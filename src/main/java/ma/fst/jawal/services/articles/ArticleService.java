package ma.fst.jawal.services.articles;

import ma.fst.jawal.entities.Article;

import java.util.List;

public interface ArticleService {
    Article add(Article article);
    void update(Article Article);
    void deleteById(Long id);
    void deleteByReference(String reference);
    void deleteByDescription(String description);
    Article loadArticleByReference(String reference);
    Article loadArticleByDescription(String description);
    Article loadArticleById(Long id);
    List<Article> loadArticleAll();
}
