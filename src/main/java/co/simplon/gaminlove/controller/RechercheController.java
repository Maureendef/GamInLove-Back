package co.simplon.gaminlove.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import co.simplon.gaminlove.model.Geek;
import co.simplon.gaminlove.model.Recherche;
import co.simplon.gaminlove.repository.GeekRepository;
import co.simplon.gaminlove.repository.RechercheRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Le controller qui gère les endpoint de l'entité Recherche
 * 
 * @author Maureen, Nicolas, Virgile
 *
 */

@RestController
@RequestMapping("/recherche")
@Api(tags = "API pour les opérations CRUD sur les Recherches.")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Succès"),
		@ApiResponse(code = 400, message = "Mauvaise Requête"),
		@ApiResponse(code = 401, message = "Echec Authentification"),
		@ApiResponse(code = 403, message = "Accès Refusé"), @ApiResponse(code = 500, message = "Problème Serveur") })
public class RechercheController {

	// permet d'initialiser le repo, par le mécanisme d'injection de dépendance
	// (IOC)
	private final RechercheRepository rechercheRepository;

	private final GeekRepository geekRepository;

	public RechercheController(RechercheRepository rechercheRepository, GeekRepository geekRepository) {
		this.rechercheRepository = rechercheRepository;
		this.geekRepository = geekRepository;
	}

	/**
	 * Crée une nouvelle recherche.
	 * 
	 * @param recherche un objet recherche sous forme Json
	 * @return la recherche crée (avec id auto-généré)
	 */
	
	@PostMapping(path = "/")
	@ApiOperation(value = "Crée une nouvelle recherche.")
	public ResponseEntity<Recherche> addNew(@RequestBody Recherche recherche) {
		Optional<Geek> optGeek = geekRepository.findById(recherche.getId());
		if (optGeek.isPresent()) {
			optGeek.get().getRecherches().add(recherche);
			rechercheRepository.save(recherche);
			geekRepository.save(optGeek.get());
		}
		return ResponseEntity.ok(recherche);
	}

	/**
	 * Retourne la recherche pour l'id spécifié.
	 * 
	 * @return une liste de recherche
	 */
	
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Retourne la recherche pour l'id spécifié.")
	public ResponseEntity<Recherche> getOne(@PathVariable int id) {
		return rechercheRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * Retourne toutes les recherches.
	 * 
	 * @return une liste de recherche
	 */
	
	@GetMapping(path = "/")
	@ApiOperation(value = "Retourne toutes les recherches.")
	public @ResponseBody Iterable<Recherche> getAll() {
		return rechercheRepository.findAll();
	}

	/**
	 * Supprime la recherche pour l'id spécifié.
	 * 
	 * @param idGeek,idRecherche de la recherche a supprimer
	 * @return code la requête (200 => OK)
	 */
	
	@DeleteMapping("/{idGeek}/{idRecherche}")
	@ApiOperation(value = "Supprime la recherche pour l'id spécifié.")
	public HttpStatus delOne(@PathVariable int idGeek, @PathVariable int idRecherche) {
		Optional<Geek> optGeek = geekRepository.findById(idGeek);
		Optional<Recherche> optRecherche = rechercheRepository.findById(idRecherche);
		if (optGeek.isPresent() && optRecherche.isPresent()) {
			optGeek.get().getRecherches().remove(optRecherche.get());
			rechercheRepository.deleteById(idRecherche);
			geekRepository.save(optGeek.get());
			return HttpStatus.OK;
		} else {
			return HttpStatus.NOT_FOUND;
		}
	}

	/**
	 * Retourne les Geek pour les critères renseignés
	 * 
	 * @return un ou plusieurs Geek(s)
	 */

	@PostMapping(path = "/search")
    @ApiOperation(value = "Retourne les Geek pour les critères renseignés")
    public List<Geek> getCity(@RequestBody Recherche recherche) {
        return rechercheRepository.findCity(recherche.getSexe(), recherche.getVille(), recherche.getAgeMin(), recherche.getAgeMax(), recherche.getJeu());
    }

}
