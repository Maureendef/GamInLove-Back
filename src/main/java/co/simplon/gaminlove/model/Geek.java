package co.simplon.gaminlove.model;

import java.util.Collection;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 * Une simple classe pour représenter un Geek.
 * 
 * @author Maureen, Nicolas, Virgile
 *
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Geek {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NonNull
	private int age;
	@Column(unique=true)
	@NonNull
	private String pseudo;
	@NonNull
	private String password;
	@NonNull
	private String ville;
	@NonNull
	private String sexe;
	@NonNull
	private String typeCompte;
	@Column(unique=true)
	@NonNull
	private String email;
	@NonNull
	@JsonIgnore
	private String token;
	@JsonIgnore
	@OneToMany(orphanRemoval=true)
	private Set<Photo> photos;
	@JsonIgnore
	@OneToMany(orphanRemoval=true)
	private Set<Recherche> recherches;
	@JsonIgnore
	@OneToMany(orphanRemoval=true)
	private Set<Coop> coop;
	@JsonIgnore
	@OneToMany(orphanRemoval=true)
	private Set<Action> action;
	@JsonIgnore
	@OneToMany(orphanRemoval=true)
	private Collection<MP> mp;
	@ManyToMany
	private Set<Jeu> jeux;
	@ManyToMany
	private Set<Event> event;

}
