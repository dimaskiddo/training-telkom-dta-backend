package co.id.telkom.digitalent.controller;

import co.id.telkom.digitalent.model.MovieModel;
import co.id.telkom.digitalent.response.BuilderResponse;
import co.id.telkom.digitalent.response.DataResponse;
import co.id.telkom.digitalent.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(value="/api/v1/movies", produces={"application/json"})
@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public void createMovie(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam("title") String title,
                            @RequestParam("genre") String genre,
                            @RequestParam("year") String year) throws IOException {
        MovieModel movieModel = new MovieModel();
        DataResponse<MovieModel> dataResponse = new DataResponse<>();

        movieModel.setMovieTitle(title);
        movieModel.setMovieGenre(genre);
        movieModel.setMovieReleaseYear(year);

        dataResponse.setCode(HttpServletResponse.SC_CREATED);
        dataResponse.setStatus(BuilderResponse.SUCCESS);
        dataResponse.setData(movieService.createMovieModel(movieModel));

        BuilderResponse.responseWriter(response, dataResponse);
    }

    @GetMapping("/{id}")
    public void getEmployeeById(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable int id) throws IOException {
        DataResponse<MovieModel> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_CREATED);
        dataResponse.setStatus(BuilderResponse.SUCCESS);
        dataResponse.setData(movieService.getMovieById(id));

        BuilderResponse.responseWriter(response, dataResponse);
    }

}
