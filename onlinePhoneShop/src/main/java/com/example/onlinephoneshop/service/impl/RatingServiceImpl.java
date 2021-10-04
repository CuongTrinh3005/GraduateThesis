package com.example.onlinephoneshop.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.onlinephoneshop.dto.RatingDTO;
import com.example.onlinephoneshop.entity.Rating;
import com.example.onlinephoneshop.entity.Rating.RatingId;
import com.example.onlinephoneshop.exception.ResourceNotFoundException;
import com.example.onlinephoneshop.repository.RatingRepository;
import com.example.onlinephoneshop.service.RatingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class RatingServiceImpl implements RatingService {
	@Autowired
	RatingRepository ratingRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<Rating> getAllRatings() {
		return ratingRepository.findAll();
	}

	@Override
	public Optional<Rating> getByRatingId(Rating.RatingId id) {
		Rating rating = ratingRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("rating id " + id + " not found"));
		return Optional.of(rating);
	}

	@Override
	public Rating saveRating(Rating rating) {
		return ratingRepository.save(rating);
	}

	@Override
	public Rating updateRating(Rating rating) {
		Optional<Rating> ratingOpt = getByRatingId(rating.getRatingId());
		Rating existedRating = ratingOpt.get();

		existedRating.setScore(rating.getScore());
		existedRating.setComment(rating.getComment());

		return ratingRepository.save(existedRating);
	}

	@Override
	public List<Rating> getAllRatingByUserId(String userId) {
		return ratingRepository.findByRatingIdUserId(userId);
	}

	@Override
	public Boolean checkRatingExist(RatingId ratingId) {
		return ratingRepository.existsByRatingId(ratingId);
	}

	@Override
	public List<Rating> getAllRatingByProductId(String productId) {
		return ratingRepository.findByRatingIdProductId(productId);
	}

	@Override
	public RatingDTO convertToDTO(Rating rating) {
		return modelMapper.map(rating, RatingDTO.class);
	}
}