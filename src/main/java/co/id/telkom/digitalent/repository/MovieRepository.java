package co.id.telkom.digitalent.repository;

import co.id.telkom.digitalent.model.MovieModel;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<MovieModel, Integer> {

}
