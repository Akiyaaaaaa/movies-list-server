package dev.akiyaaa.moviesreview.controllers;

import dev.akiyaaa.moviesreview.models.DTO.request.ReviewRequest;
import dev.akiyaaa.moviesreview.models.Review;
import dev.akiyaaa.moviesreview.service.ReviewService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    
    @GetMapping
    public ResponseEntity<List<Review>> getAll(){
        return  new ResponseEntity<List<Review>>(reviewService.getAll(), HttpStatus.OK);
    }
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest reviewRequest){
        return new ResponseEntity<Review>(reviewService.createReview(reviewRequest), HttpStatus.CREATED);
    }
    
    @PutMapping("/{reviewId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Review> updateReview(@PathVariable ObjectId reviewId, @RequestBody Map<String, String> content){
        return new ResponseEntity<Review>(reviewService.updateReview(reviewId, content.get("reviewContent")),HttpStatus.OK);
    }
    
    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteReview(@PathVariable ObjectId reviewId){
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
