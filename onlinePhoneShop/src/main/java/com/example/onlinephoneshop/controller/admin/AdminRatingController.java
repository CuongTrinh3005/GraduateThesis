package com.example.onlinephoneshop.controller.admin;

import com.example.onlinephoneshop.dto.RatingDTO;
import com.example.onlinephoneshop.entity.Rating;
import com.example.onlinephoneshop.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/ratings")
public class AdminRatingController {
    @Autowired
    RatingService ratingService;

    @GetMapping
    public List<RatingDTO> getAllRatings() throws Throwable{
        List<Rating> ratingList = ratingService.getAllRatingsOrderByUpdatedDate();
        List<RatingDTO> dtoList = new ArrayList<>();
        for(Rating rating:ratingList){
            RatingDTO ratingDTO = ratingService.convertToDTO(rating);
            dtoList.add(ratingDTO);
        }
        return dtoList;
    }
}
