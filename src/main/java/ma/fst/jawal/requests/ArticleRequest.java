package ma.fst.jawal.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.fst.jawal.entities.Article;
import ma.fst.jawal.entities.Category;

@Data @AllArgsConstructor @NoArgsConstructor
public class ArticleRequest {
    private Article article;
    private Category category;
}
