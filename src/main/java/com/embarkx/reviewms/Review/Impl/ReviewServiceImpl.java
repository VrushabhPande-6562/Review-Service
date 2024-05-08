package com.embarkx.reviewms.Review.Impl;

import com.embarkx.reviewms.Review.Review;
import com.embarkx.reviewms.Review.ReviewRepository;
import com.embarkx.reviewms.Review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;

    ReviewServiceImpl(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review){
        if(companyId !=null && review !=null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }else {
            return  false;
        }
    }


    @Override
    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);

    }

    @Override
    public boolean updateReview( Long reviewId, Review updatedReview) {

        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review !=null) {
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            review.setCompanyId(updatedReview.getCompanyId());
            reviewRepository.save(review);
            return true;
        }else {
            return false;
        }
        }


    @Override
    public boolean deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review !=null){
            reviewRepository.delete(review);
            return true;

        }else{
            return false;
        }

    }
}
