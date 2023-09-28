package dev.akiyaaa.moviesreview.controllers;

import dev.akiyaaa.moviesreview.models.Review;
import dev.akiyaaa.moviesreview.service.ReviewService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> content){
        return new ResponseEntity<Review>(reviewService.createReview(content.get("reviewContent"), content.get("imdbId")), HttpStatus.CREATED);
    }
    
    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable ObjectId reviewId, @RequestBody Map<String, String> content){
        return new ResponseEntity<Review>(reviewService.updateReview(reviewId, content.get("reviewContent")),HttpStatus.OK);
    }
    
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable ObjectId reviewId){
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
