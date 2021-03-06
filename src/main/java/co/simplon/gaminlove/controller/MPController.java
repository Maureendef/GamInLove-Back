package co.simplon.gaminlove.controller;

import java.util.ArrayList;
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
import co.simplon.gaminlove.model.MP;
import co.simplon.gaminlove.repository.GeekRepository;
import co.simplon.gaminlove.repository.MPRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Le controller qui gère les endpoint de l'entité MP
 * 
 * @author Maureen, Nicolas, Virgile
 *
 */

@RestController
@RequestMapping(path = "/mp")
@Api(tags = "API pour les opérations CRUD sur les MP.")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Succès"),
		@ApiResponse(code = 400, message = "Mauvaise Requête"),
		@ApiResponse(code = 401, message = "Echec Authentification"),
		@ApiResponse(code = 403, message = "Accès Refusé"), @ApiResponse(code = 500, message = "Problème Serveur") })
@CrossOrigin("*")
public class MPController {

	@Autowired
	private MPRepository mpRepository;

	@Autowired
	private GeekRepository geekRepository;

	/**
	 * Crée un MP.
	 * 
	 * @param un objet MP sous forme Json et l'id du Geek cible
	 */

	@PostMapping(path = "/")
	@ApiOperation(value = "Crée un MP.")
	public MP addNew(@RequestBody MP mp) {
		return mpRepository.save(mp);
	}

	/**
	 * Retourne tous MP.
	 * 
	 * @return une liste de MP
	 */

	@GetMapping(path = "/")
	@ApiOperation(value = "Retourne tous MP.")
	public @ResponseBody Iterable<MP> getAll() {
		return mpRepository.findAll();
	}

	/**
	 * Retourne tous les MP d'un geek selon son id.
	 * 
	 * @return une liste de MP
	 */
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Retourne tous MP d'un geek.")
	public Iterable<MP> getMpByGeek(@PathVariable int id) {
		Optional<Geek> optGeek = geekRepository.findById(id);
		return optGeek.get().getMp();
	}

	/**
	 * Retourne tous MP entre deux geeks.
	 * 
	 * @return une liste de MP
	 */

	@GetMapping(path = "/{idEmetteur}/{idRecepteur}")
	@ApiOperation(value = "Retourne tous MP entre deux Geeks.")
	public ArrayList<?> getAllMP(@PathVariable int idEmetteur, @PathVariable int idRecepteur) {
		ArrayList<?> optMP = mpRepository.ListMp(idEmetteur, idRecepteur);

		return optMP;
	}

	/**
	 * Retourne le MP d'id spécifié.
	 * 
	 * @param id du mp
	 * @return un MP s'il existe.
	 */

//	@GetMapping(path = "/{idMP}")
//	@ApiOperation(value = "Retourne le mp d'id spécifié.")
//	public ResponseEntity<MP> getOne(@PathVariable int idMP) {
//		Optional<MP> optMP = mpRepository.findById(idMP);
//		return optMP.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//	}

	/**
	 * Supprime le MP avec l'id spécifié.
	 * 
	 * @param id
	 * @return code la requête (200 => OK)
	 */

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Supprime le MP avec l'id spécifié.")
	public HttpStatus delOne(@PathVariable int id) {
		Optional<MP> optMP = mpRepository.findById(id);
		if (optMP.isPresent()) {
			mpRepository.deleteById(id);
			return HttpStatus.OK;
		} else {
			return HttpStatus.NOT_FOUND;
		}
	}

}
