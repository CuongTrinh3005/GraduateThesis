package com.example.onlinephoneshop.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.onlinephoneshop.dto.RatingDTO;
import com.example.onlinephoneshop.entity.Rating;
import com.example.onlinephoneshop.entity.Rating.RatingId;
import com.example.onlinephoneshop.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ratings")
public class RatingController {
	@Autowired
	RatingService ratingService;

	@GetMapping
	public Optional<Rating> getRatingById(@RequestParam String userId, @RequestParam String productId){
		RatingId ratingId = new RatingId(userId, productId);
		return ratingService.getByRatingId(ratingId);
	}
	
	@GetMapping("user/{userId}")
	public List<Rating> getUserRatings(@PathVariable String userId){
		return ratingService.getAllRatingByUserId(userId);
	}
	
	@PostMapping
	public Rating saveRating(@Valid @RequestBody Rating rating){
		return ratingService.saveRating(rating);
	}
	
	@PutMapping
	public ResponseEntity<Rating> updateRating(@Valid @RequestBody Rating rating) {
		return new ResponseEntity<Rating>(ratingService.updateRating(rating), HttpStatus.OK);
	}

	@GetMapping("check-exist")
	public Boolean isRatingExist(@RequestParam String userId, @RequestParam String productId){
		RatingId ratingId = new RatingId(userId, productId);
		return ratingService.checkRatingExist(ratingId);
	}
}