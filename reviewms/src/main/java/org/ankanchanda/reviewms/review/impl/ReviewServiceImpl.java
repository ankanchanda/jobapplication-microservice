package org.ankanchanda.reviewms.review.impl;

import java.util.List;
import org.ankanchanda.reviewms.review.Review;
import org.ankanchanda.reviewms.review.ReviewRepository;
import org.ankanchanda.reviewms.review.ReviewService;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviewsByCompnayId(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElse(null);
    }

    @Override
    public boolean createReview(Long compnayId, Review review) {
        if (compnayId != null && review != null) {
            review.setCompanyId(compnayId);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            reviewRepository.delete(review);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            review.setTitle(updatedReview.getTitle());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

}
