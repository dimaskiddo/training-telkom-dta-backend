package co.id.telkom.digitalent.controller;

import co.id.telkom.digitalent.model.MovieModel;
import co.id.telkom.digitalent.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value="/api/v1/movies", produces={"application/json"})
@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public MovieModel createMovie(@RequestParam("title") String title,
                                  @RequestParam("genre") String genre,
                                  @RequestParam("release_year") String releaseYear) {
        MovieModel movieModel = new MovieModel();
        movieModel.setMovieTitle(title);
        movieModel.setMovieGenre(genre);
        movieModel.setMovieReleaseYear(releaseYear);

        return movieService.createMovieModel(movieModel);
    }

    @GetMapping("/{id}")
    public MovieModel getEmployeeById(@PathVariable int id) {
        return movieService.getMovieById(id);
    }

}
