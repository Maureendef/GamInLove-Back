package co.simplon.gaminlove.repository;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import co.simplon.gaminlove.model.Event;
import org.springframework.stereotype.Repository;

/**
 * Le repository Events, l'héritage de CRUD donne des méthodes de base : save,
 * findById, findAll, etc....
 *
 * @author Maureen, Nicolas, Virgile
 *
 */
@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

	 Collection<Event> findAllByNom(String nom);
	
}

