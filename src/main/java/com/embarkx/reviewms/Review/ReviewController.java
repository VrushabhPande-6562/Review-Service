package com.embarkx.reviewms.Review;

import com.embarkx.reviewms.Review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewMessageProducer reviewMessageProducer;
    private ReviewService reviewService;


    public ReviewController(ReviewService reviewService , ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer= reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addReviews(@RequestParam Long companyId, @RequestBody Review review){
      boolean isReviewSaved = reviewService.addReview(companyId, review);
      if(isReviewSaved){
      reviewMessageProducer.sendMessage(review);

          return new ResponseEntity<>("review added successfully" , HttpStatus.OK);
      }else{
          return new ResponseEntity<>("review not saved" , HttpStatus.NOT_FOUND);
      }
    }
        @GetMapping("/{reviewId}")
        public ResponseEntity<Review> getReview(@PathVariable Long reviewId){
        return new ResponseEntity<>(reviewService.getReview(reviewId),HttpStatus.OK);
        }

        @PutMapping("/{reviewId}")
        public ResponseEntity<String> updateReview(@PathVariable Long reviewId,
                                                   @RequestBody Review review){

        boolean isReviewUpdated = reviewService.updateReview(reviewId,review);

        if(isReviewUpdated){
            return new ResponseEntity<>("review updated successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("review not updated", HttpStatus.NOT_FOUND);
        }
        }

        @DeleteMapping("/{reviewId}")
        public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
        boolean isReviewDeleted = reviewService.deleteReview(reviewId);
        if(isReviewDeleted){
            return new ResponseEntity<>("Reviews deleted successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Reviews Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/averageRating")
    public Double getAverageRating(@RequestParam Long companyId){
        List<Review> reviewList = reviewService.getAllReviews(companyId);
        return reviewList.stream().mapToDouble(Review::getRating).average()
                .orElse(0.0);


    }
}
