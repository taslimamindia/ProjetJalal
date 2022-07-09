package ma.fst.jawal.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class User implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id @Column(length = 150)
	private String login;
	private String nom;
	private String prenom;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String pwd;
	private Date created_at;
	private Date update_at;
	private Boolean active;
	private String ressetPasswordToken;

	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Authority> authorities = new ArrayList<>();

//	@ManyToOne
//	@JoinColumn(name="idDep", insertable=false,updatable=false )
//	private Departement departement;
//	@OneToMany(mappedBy = "fournisseur",fetch = FetchType.LAZY)
//	private List<Ressource> fournisseurs;
//
//	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
//	private List<Ressource_d> ressource_ds ;

//	@OneToMany(mappedBy = "personnel",fetch = FetchType.LAZY)
//	private List<Panne> pannes;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		User user = (User) o;
		return login != null && Objects.equals(login, user.login);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}