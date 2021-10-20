package com.example.onlinephoneshop.repository;

import java.util.List;

import com.example.onlinephoneshop.entity.Rating;
import com.example.onlinephoneshop.entity.Rating.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId> {
	List<Rating> findByRatingIdUserId(String userId);
	List<Rating> findByRatingIdProductId(String productId);
	Boolean existsByRatingId(RatingId ratingId);
	List<Rating> findAllByOrderByUpdatedDateDesc();
}