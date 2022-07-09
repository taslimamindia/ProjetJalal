package ma.fst.jawal.business;

import ma.fst.jawal.entities.Article;
import ma.fst.jawal.entities.Category;
import ma.fst.jawal.requests.ArticleRequest;
import ma.fst.jawal.services.articles.ArticleServiceImp;
import ma.fst.jawal.services.categories.CategoryServiceImp;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = {"api/stockservice"})
//@CrossOrigin // a oublie pour l'instant
public class StockService {
//	private final AccountImp accountService;
//    private UserRepository userRepository;
    private final ArticleServiceImp articleServiceImp;
    private final CategoryServiceImp categoryServiceImp;

    public StockService(ArticleServiceImp articleServiceImp, CategoryServiceImp categoryServiceImp) {
        this.articleServiceImp = articleServiceImp;
        this.categoryServiceImp = categoryServiceImp;
    }

    // ********************************** Treating of articles ***************************************
    @GetMapping(path = "/article/{id}")
    public Article getArticle(@PathVariable Long id) {
        return articleServiceImp.loadArticleById(id);
    }

    @PostMapping(path = "/article")
    public void addArticle(@RequestBody ArticleRequest articleRequest) {
        System.out.println(articleRequest);
        if(articleRequest != null && articleRequest.getCategory() != null && articleRequest.getArticle() != null) {
            Category category = categoryServiceImp.loadCategoryByIntitule(articleRequest.getCategory().getIntitule());
            Article article = articleRequest.getArticle();
            if(category != null) {
                article = articleServiceImp.add(article);
                if(article != null) {
                    categoryServiceImp.addArticleToCategory(category.getId(), article.getId());
                }
            }
        }
    }

    @PutMapping(path = "/article")
    public void putArticle(@RequestBody Article article) {
        articleServiceImp.update(article);
    }

    @DeleteMapping(path = "/article/{id}")
    public void deleteArticle(@PathVariable Long id) {
        articleServiceImp.deleteById(id);
    }

    @GetMapping(path = "/articles")
//    @PostAuthorize("hasAuthority('RESPONSABLE')")
    public List<Article> getArticles() {
        return articleServiceImp.loadArticleAll();
    }

    // ***************************** Treating of Categories *********************************
    @PostMapping(path = "/category")
    public Category postCategory(@RequestBody Category category) {
        categoryServiceImp.add(category);
        Category categoryResponse = categoryServiceImp.loadCategoryByIntitule(category.getIntitule());
        if(categoryResponse.getArticles() == null) {
            categoryResponse.setArticles(new ArrayList<>());
            return categoryResponse;
        }
        return categoryResponse;
    }

    @GetMapping(path = "/categories")
    public List<Category> getCategories() {
        return categoryServiceImp.loadCategoryAll();
    }
}
