package dev.akiyaaa.moviesreview.repositories;

import dev.akiyaaa.moviesreview.models.Movie;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MovieRepository extends MongoRepository<Movie, ObjectId> {
    Optional<Movie> findMovieByImdbId(String imdbId);
    Optional<Movie> findMovieByTitleAndImdbId(String title, String imdbId);
}
