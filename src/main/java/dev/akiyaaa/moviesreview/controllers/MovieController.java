package dev.akiyaaa.moviesreview.controllers;

import dev.akiyaaa.moviesreview.models.Movie;
import dev.akiyaaa.moviesreview.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "*")
public class MovieController {
    @Autowired
    private MovieService movieService;
    
    @GetMapping
    public ResponseEntity<List<Movie>> getAll(){
        return new ResponseEntity<List<Movie>>(movieService.getAll(), HttpStatus.OK);
    }
    
    @GetMapping("/{imdbId}")
    public ResponseEntity<Movie> getMovie(@PathVariable String imdbId){
        return new ResponseEntity<Movie>(movieService.getMovieByImdbId(imdbId), HttpStatus.OK);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        return new ResponseEntity<Movie>(movieService.addMovie(movie), HttpStatus.CREATED);
    }
    
    @PutMapping("/{imdbId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> updateMovie(@PathVariable String imdbId, @RequestBody Movie movie){
        return new ResponseEntity<Movie>(movieService.updateMovie(imdbId, movie), HttpStatus.OK);
    }
    
    @DeleteMapping("/{imdbId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMovie(@PathVariable String imdbId){
        movieService.deleteMovie(imdbId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
