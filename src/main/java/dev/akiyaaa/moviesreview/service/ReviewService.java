package dev.akiyaaa.moviesreview.service;

import dev.akiyaaa.moviesreview.models.DTO.request.ReviewRequest;
import dev.akiyaaa.moviesreview.models.Movie;
import dev.akiyaaa.moviesreview.models.Review;
import dev.akiyaaa.moviesreview.models.User;
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
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    
    public List<Review> getAll(){
        return reviewRepository.findAll();
    }
    public Review createReview(ReviewRequest reviewRequest){
        User user = new User();
        user.setId(reviewRequest.getUserId());
        
        // Create the Review object and set its properties
        Review review = new Review();
        review.setBody(reviewRequest.getReviewContent());
        review.setCreated(LocalDateTime.now());
        review.setUpdated(LocalDateTime.now());
        review.setIsUpdated(Boolean.FALSE);
        review.setUser(user);
        
        // Insert the Review into the repository
        review = reviewRepository.insert(review);
        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(reviewRequest.getImdbId()))
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