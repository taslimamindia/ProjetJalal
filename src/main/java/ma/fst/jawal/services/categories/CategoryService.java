package ma.fst.jawal.services.categories;

import ma.fst.jawal.entities.Category;

import java.util.List;

interface CategoryService {
    void add(Category category);
    void addArticleToCategory(Long category_id, Long article_id);
    void update(Category category);
    void delete(Long id);
    void deleteByIntitule(String intitule);
    Category loadCategoryByIntitule(String intitule);
    Category loadCategoryById(Long id);
    List<Category> loadCategoryAll();
}
