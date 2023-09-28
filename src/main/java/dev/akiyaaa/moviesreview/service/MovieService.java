package dev.akiyaaa.moviesreview.service;

import dev.akiyaaa.moviesreview.models.Movie;
import dev.akiyaaa.moviesreview.repositories.MovieRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    
    public List<Movie> getAll(){
        return movieRepository.findAll();
    }
    public Movie getMovieByImdbId(String imdbId){
        return movieRepository.findMovieByImdbId(imdbId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
    }
    
    public Movie addMovie(Movie movie){
        Optional<Movie> checkMovie = movieRepository.findMovieByTitleAndImdbId(movie.getTitle(), movie.getImdbId());
        if (checkMovie.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A movie with the same title and IMDb ID already exists");
        }
        return movieRepository.save(movie);
    }
    
    public Movie updateMovie(String imdbId, Movie movie){
        Movie existingMovie = getMovieByImdbId(imdbId);
        
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setPlots(movie.getPlots());
        existingMovie.setReleaseDate(movie.getReleaseDate());
        existingMovie.setTrailerLink(movie.getTrailerLink());
        existingMovie.setPoster(movie.getPoster());
        existingMovie.setGenres(movie.getGenres());
        existingMovie.setBackdrops(movie.getBackdrops());
        
        return movieRepository.save(existingMovie);
    }
    
    public void deleteMovie(String imdbId){
        Movie existingMovie = movieRepository.findMovieByImdbId(imdbId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        movieRepository.delete(existingMovie);
        
    }
}
