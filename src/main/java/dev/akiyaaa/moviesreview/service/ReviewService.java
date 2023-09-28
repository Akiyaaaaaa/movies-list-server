package dev.akiyaaa.moviesreview.service;

import dev.akiyaaa.moviesreview.models.Movie;
import dev.akiyaaa.moviesreview.models.Review;
import dev.akiyaaa.moviesreview.repositories.ReviewRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public Review createReview(String reviewContent, String imdbId){
        Review review = reviewRepository.insert(new Review(reviewContent, LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE));
        
        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviews").value(review))
                .first();
        return review;
    }
    
    public Review updateReview(ObjectId reviewId, String updateContent){
        Review getReview = reviewRepository.findById(reviewId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
        getReview.setBody(updateContent);
        getReview.setUpdated(LocalDateTime.now());
        getReview.setIsUpdated(true);
        return reviewRepository.save(getReview);
    }
    
    public void deleteReview(ObjectId reviewId){
        Review getReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
        reviewRepository.delete(getReview);
    }
}