package co.id.telkom.digitalent.service;

import co.id.telkom.digitalent.model.MovieModel;
import co.id.telkom.digitalent.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieModel createMovieModel(MovieModel movieModel) {
        return movieRepository.save(movieModel);
    }

    public MovieModel getMovieById(int id) {
        return movieRepository.findById(id).get();
    }
}
