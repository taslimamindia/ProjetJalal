package ma.fst.jawal.services.categories;

import ma.fst.jawal.entities.Article;
import ma.fst.jawal.entities.Category;
import ma.fst.jawal.repositories.CategoryRepository;
import ma.fst.jawal.services.articles.ArticleServiceImp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImp implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final ArticleServiceImp articleServiceImp;

    public CategoryServiceImp(CategoryRepository categoryRepository, ArticleServiceImp articleServiceImp) {
        this.categoryRepository = categoryRepository;
        this.articleServiceImp = articleServiceImp;
    }

    @Override
    public void add(Category category) {
        System.out.println(category);
        categoryRepository.save(category);
    }

    @Override
    public void addArticleToCategory(Long category_id, Long article_id) {
        Article a = articleServiceImp.loadArticleById(article_id);
        Category category = this.loadCategoryById(category_id);
        if (category != null && a != null) {
            category.getArticles().add(a);
        }

    }

    @Override
    public void update(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        if(categoryRepository.findById(id).isPresent()) {
            Category category = categoryRepository.findById(id).get();
            categoryRepository.delete(category);
        }
    }

    @Override
    public void delete(String intitule) {
        if(categoryRepository.findByIntitule(intitule) != null) {
            Category category = categoryRepository.findByIntitule(intitule);
            categoryRepository.delete(category);
        }
    }

    @Override
    public Category loadCategoryByIntitule(String intitule) {
        return categoryRepository.findByIntitule(intitule);
    }

    @Override
    public Category loadCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }

    @Override
    public List<Category> loadCategoryAll() {
        return categoryRepository.findAll();
    }
}
