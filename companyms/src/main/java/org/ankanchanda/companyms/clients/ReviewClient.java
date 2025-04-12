package org.ankanchanda.companyms.clients;

import java.util.List;

import org.ankanchanda.companyms.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "REVIEWMS")
public interface ReviewClient {

    /**
     * Fetches reviews for a given company ID.
     *
     * @param companyId the ID of the company for which to fetch reviews
     * @return a list of reviews associated with the specified company ID
     */
    @GetMapping("/reviews")
    List<Review> getReviewsByCompanyId(@RequestParam Long companyId);
}
