package ma.fst.jawal.business;

import ma.fst.jawal.entities.Article;
import ma.fst.jawal.entities.Category;
import ma.fst.jawal.requests.ArticleRequest;
import ma.fst.jawal.services.articles.ArticleServiceImp;
import ma.fst.jawal.services.categories.CategoryServiceImp;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = {"api/stockservice"})
//@CrossOrigin // a oublie pour l'instant
public class StockService {
    private final ArticleServiceImp articleServiceImp;
    private final CategoryServiceImp categoryServiceImp;

    public StockService(ArticleServiceImp articleServiceImp, CategoryServiceImp categoryServiceImp) {
        this.articleServiceImp = articleServiceImp;
        this.categoryServiceImp = categoryServiceImp;
    }

    // ********************************** Treating of articles ***************************************

    @PostMapping(path = "/article") // Terminate
    public Article addArticle(@RequestBody ArticleRequest articleRequest) {
        System.out.println(articleRequest);
        if(articleRequest != null && articleRequest.getArticle() != null) {
            Article article = articleRequest.getArticle();
            article = articleServiceImp.add(article);
            if(article != null && articleRequest.getCategory() != null) {
                Category category = categoryServiceImp.loadCategoryByIntitule(articleRequest.getCategory().getIntitule());
                if(category != null) {
                    categoryServiceImp.addArticleToCategory(category.getId(), article.getId());
                }
            }
            return article;
        }
        return null;
    }

    @PutMapping(path = "/article") // Terminate
    public Article updateArticle(@RequestBody Article article) {
        articleServiceImp.update(article);
        return articleServiceImp.loadArticleById(article.getId());
    }

    @DeleteMapping(path = "/article/{id}") // Terminate
    public void deleteArticle(@PathVariable Long id) {
        articleServiceImp.deleteById(id);
    }

    @GetMapping(path = "/article/{id}") // Terminate
    public Article getArticle(@PathVariable Long id) {
        return articleServiceImp.loadArticleById(id);
    }

    @GetMapping(path = "/articles") // Terminate
//    @PostAuthorize("hasAuthority('RESPONSABLE')")
    public List<Article> getArticles() {
        return articleServiceImp.loadArticleAll();
    }




    // ***************************** Treating of Categories *********************************
    @PostMapping(path = "/category") // Terminate
    public Category addCategory(@RequestBody Category category) {
        categoryServiceImp.add(category);
        return categoryServiceImp.loadCategoryByIntitule(category.getIntitule());
    }

    @PutMapping(path = "/category") // Terminate
    public Category updateCategory(@RequestBody Category category) {
        categoryServiceImp.update(category);
        return categoryServiceImp.loadCategoryByIntitule(category.getIntitule());
    }

    @DeleteMapping(path = "/category/{id}") // Terminate
    public Category deleteCategory(@PathVariable Long id) {
        Category category = categoryServiceImp.loadCategoryById(id);
        if (category != null) {
            articleServiceImp.ejectToCategory(category.getId());
            categoryServiceImp.delete(id);
            return category;
        }
        return null;
    }

    @GetMapping(path = "/categories") // Terminate
    public List<Category> getCategories() {
        return categoryServiceImp.loadCategoryAll();
    }
}
