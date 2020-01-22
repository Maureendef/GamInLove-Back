package co.simplon.gaminlove.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.gaminlove.model.Action;
import co.simplon.gaminlove.model.Geek;
import co.simplon.gaminlove.repository.ActionRepository;

@RestController
@RequestMapping(path = "/action")
public class ActionController {

	// permet d'initialiser le repo, par le mécanisme d'injection de dépendance
	// (IOC)
	@Autowired
	private ActionRepository actionRepository;

	/**
	 * Crée une nouvelle action avec le type spécifié et l'enregistre en base.
	 * 
	 * @param action, émetteur, récepteur
	 * @return l'action est stockée en base (avec l'id auto-générée)
	 */
	@RequestMapping(path = "/add")
	public Action addNew(@RequestParam String action, Geek emetteur, Geek recepteur) {
		Action newAction = new Action();
		newAction.setAction(action);
		newAction.setEmetteur(emetteur);
		newAction.setRecepteur(recepteur);
		return actionRepository.save(newAction);
	}

	/**
	 * Retourne toutes les actions de la base.
	 * 
	 * @return une liste de heros
	 */
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Action> getAll() {
		return actionRepository.findAll();
	}

	/**
	 * Retourne le hero d'id spécifié.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/get")
	public ResponseEntity<Action> getOne(@RequestParam int id) {
		Optional<Action> optAction = actionRepository.findById(id);
		if (optAction.isPresent()) {
			return ResponseEntity.ok(optAction.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * supprime l'action d'id spécifié.
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/del/{id}")
	public void delOne(@PathVariable int id) {
		Optional<Action> optGeek = actionRepository.findById(id);
		if (optGeek.isPresent()) {
			actionRepository.deleteById(id);
			System.out.println("Action supprimée");
		} else {
			System.out.println("Pas d'action à supprimer");
		}
	}
}