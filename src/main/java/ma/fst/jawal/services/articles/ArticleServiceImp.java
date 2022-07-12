package ma.fst.jawal.services.articles;

import ma.fst.jawal.entities.Article;
import ma.fst.jawal.repositories.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service @Transactional
public class ArticleServiceImp implements ArticleService{

    private final ArticleRepository articleRepository;

    public ArticleServiceImp(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article add(Article article) {
        article.setCreated_at(new Date());
        article.setUpdated_at(new Date());
        articleRepository.save(article);
        return articleRepository.findByDescription(article.getDescription());
    }

    @Override
    public void update(Article article) {
        article.setUpdated_at(new Date());
        articleRepository.save(article);
    }

    @Override
    public void deleteById(Long id) {
        if(articleRepository.findById(id).isPresent()) {
            articleRepository.delete(articleRepository.findById(id).get());
        }
    }

    @Override
    public void deleteByReference(String reference) {
        if(articleRepository.findByReference(reference) != null) {
            articleRepository.delete(articleRepository.findByReference(reference));
        }
    }

    @Override
    public void deleteByDescription(String description) {
        if(articleRepository.findByDescription(description) != null) {
            articleRepository.delete(articleRepository.findByDescription(description));
        }
    }

    @Override
    public Article loadArticleByReference(String reference) {
        return articleRepository.findByReference(reference);
    }

    @Override
    public Article loadArticleByDescription(String description) {
        return articleRepository.findByDescription(description);
    }

    @Override
    public Article loadArticleById(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article.orElse(null);
    }

    @Override
    public List<Article> loadArticleAll() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> loadByCategory(Long categoryId) {
        return articleRepository.findByCategory(categoryId);
    }

    @Override
    public void ejectToCategory(Long id) {
        List<Article> articles = this.loadByCategory(id);
        for(Article article : articles) {
            article.setCategorie(null);
            this.update(article);
        }
    }
}
