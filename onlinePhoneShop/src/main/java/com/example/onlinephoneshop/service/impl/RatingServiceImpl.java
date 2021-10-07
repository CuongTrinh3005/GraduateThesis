package com.example.onlinephoneshop.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.onlinephoneshop.dto.RatingDTO;
import com.example.onlinephoneshop.entity.Accessory;
import com.example.onlinephoneshop.entity.Phone;
import com.example.onlinephoneshop.entity.Product;
import com.example.onlinephoneshop.entity.Rating;
import com.example.onlinephoneshop.entity.Rating.RatingId;
import com.example.onlinephoneshop.exception.ResourceNotFoundException;
import com.example.onlinephoneshop.repository.RatingRepository;
import com.example.onlinephoneshop.service.PhoneService;
import com.example.onlinephoneshop.service.RatingService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class RatingServiceImpl implements RatingService {
	@Autowired
	RatingRepository ratingRepository;

	@Autowired
	PhoneService phoneService;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<Rating> getAllRatings() {
		return ratingRepository.findAll();
	}

	@Override
	public Optional<Rating> getByRatingId(RatingId id) {
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
	public RatingDTO convertToDTO(Rating rating) throws Throwable {
		String productId = rating.getRatingId().getProductId();
		Object	product = phoneService.getProductById(productId).get();
		Phone phone = null; Accessory accessory = null; RatingDTO dto = null;
		if(product instanceof Phone){
			phone = (Phone) product;
			dto = new RatingDTO(rating.getRatingId().getUserId(), productId,
					phone.getProductName(),phone.getImage(), rating.getCreatedDate()
					,rating.getScore(), rating.getComment());
		}
		else{
			accessory = (Accessory) product;
			dto = new RatingDTO(rating.getRatingId().getUserId(), productId,
					accessory.getProductName(),accessory.getImage(),rating.getCreatedDate()
					,rating.getScore(), rating.getComment());
		}
		return dto;
	}
}