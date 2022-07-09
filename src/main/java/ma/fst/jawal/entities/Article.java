package ma.fst.jawal.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor @ToString
public class Article implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String description;
    @Column(unique = true, nullable = false)
    private String reference;
    private Integer quantity;
    private Double priceU;
    private Date created_at;
    private Date updated_at;
//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categorie_ID")
    private Category categorie;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Article article = (Article) o;
        return id != null && Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
