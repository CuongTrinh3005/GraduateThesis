package com.example.onlinephoneshop.service;

import java.util.List;
import java.util.Optional;

import com.example.onlinephoneshop.dto.RatingDTO;
import com.example.onlinephoneshop.entity.Rating;
import com.example.onlinephoneshop.entity.Rating.RatingId;

public interface RatingService {
	List<Rating> getAllRatings();
	Optional<Rating> getByRatingId(RatingId id);
	Rating saveRating(Rating rating);
	Rating updateRating(Rating rating);
	List<Rating> getAllRatingByUserId(String userId);
	List<Rating> getAllRatingByProductId(String productId);
	Boolean checkRatingExist(RatingId ratingId);
	RatingDTO convertToDTO(Rating rating) throws Throwable;
}