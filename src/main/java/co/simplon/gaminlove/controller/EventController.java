package co.simplon.gaminlove.controller;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.gaminlove.model.Catalogue;
import co.simplon.gaminlove.model.Event;
import co.simplon.gaminlove.model.Geek;
import co.simplon.gaminlove.repository.EventRepository;
import co.simplon.gaminlove.repository.GeekRepository;

@RestController
@RequestMapping(path = "/event")
@CrossOrigin("*")
public class EventController {

	@Autowired
	private EventRepository eventRepository;

//	@Autowired
//	private GeekRepository geekRepository;

	@RequestMapping("/add")
	public Event addNew(@RequestParam String nom, @RequestParam String lieu, @RequestParam Date date) {
		Event newEvent = new Event();
		newEvent.setNom(nom);
		newEvent.setLieu(lieu);
		newEvent.setDate(date);
		return eventRepository.save(newEvent);
	}

	@GetMapping(path = "/update/{id}/{listeParticipant}")
	public Event updateOne(@PathVariable int id, @PathVariable Collection<Geek> listeParticipant) {
		Event updateEvent = eventRepository.findById(id).get();
		updateEvent.setListeParticipant(listeParticipant);
		/*Optional<Geek> optCatalogue = geekRepository.findById(geek.getId());
		Geek updateCatalogue = geekRepository.findById(geek.getId()).get();
		if (optCatalogue.isPresent()) {
			updateCatalogue.add(newEvent);
		}*/
		return eventRepository.save(updateEvent);
		
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Event> getAll() {
		return eventRepository.findAll();
	}

	@GetMapping(path = "/get/{id}")
	public ResponseEntity<Event> getOne(@PathVariable int id) {
		Optional<Event> optEvent = eventRepository.findById(id);
		if (optEvent.isPresent()) {
			return ResponseEntity.ok(optEvent.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(path = "/get/{nom}")
	public ResponseEntity<Event> getName(@PathVariable String nom) {
		Optional<Event> optEvent = eventRepository.findByNom(nom);
		if (optEvent.isPresent()) {
			return ResponseEntity.ok(optEvent.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(path = "/update")
	public Event updateOne(@PathVariable int id, @PathVariable Collection<Geek> listeParticipant,
			@PathVariable String nom, @PathVariable String lieu, @PathVariable Date date) {
		Optional<Event> optEvent = eventRepository.findById(id);
		Event updateEvent = eventRepository.findById(id).get();
		if (optEvent.isPresent()) {
			updateEvent.setListeParticipant(listeParticipant);
			updateEvent.setNom(nom);
			updateEvent.setLieu(lieu);
			updateEvent.setDate(date);
			return eventRepository.save(updateEvent);
		} else {
			return updateEvent;
		}
	}

	@RequestMapping("/del/{id}")
	public void delOne(@PathVariable int id) {
		Optional<Event> optEvent = eventRepository.findById(id);
		if (optEvent.isPresent()) {
			eventRepository.deleteById(id);
			System.out.println("Action supprimée");
		} else {
			System.out.println("Pas d'action à supprimer");
		}
	}

}
