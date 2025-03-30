package org.ankanchanda.reviewms.review;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    
    @GetMapping
    public ResponseEntity<List<Review>> findAll(@RequestParam Long companyId) {
        return ResponseEntity.ok(reviewService.getAllReviewsByCompnayId(companyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> findReviewById(@PathVariable Long id) {
        Review review = reviewService.findReviewById(id);
        if (review == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(review);
    }
    
    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review) {
        boolean success = reviewService.createReview(companyId, review);
        if(success) {
            return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        boolean success = reviewService.deleteReview(id);
        if(success) {
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);        
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateReview(@PathVariable Long id, @RequestBody Review review) {
        boolean success = reviewService.updateReview(id, review);
        if(success) {
            return new ResponseEntity<>("Review updated successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
    }
}
