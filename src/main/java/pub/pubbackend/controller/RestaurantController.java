package pub.pubbackend.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pub.pubbackend.model.Restaurant;
import pub.pubbackend.repository.RestaurantRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")

public class RestaurantController {
    @Autowired
    RestaurantRepository restaurantRepository;

    //Register restaurant
    @PostMapping("/register-restaurant")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        try {

            Restaurant _restaurant = restaurantRepository
                    .save(new Restaurant(restaurant.getUserId(), restaurant.getRestaurantName()));
            return new ResponseEntity<>(_restaurant, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
