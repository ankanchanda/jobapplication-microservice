package org.ankanchanda.reviewms.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviewsByCompnayId(Long companyId);

    Review findReviewById(Long reviewId);

    boolean createReview(Long companyId, Review review);

    boolean deleteReview(Long reviewId);

    boolean updateReview(Long reviewId, Review review);
}
