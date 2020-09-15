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
import pub.pubbackend.model.User;
import pub.pubbackend.repository.RestaurantRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")

public class RestaurantController {
    @Autowired
    RestaurantRepository restaurantRepository;

    //Get restaurant data
    @GetMapping("/profile-restaurant/{id}")
    public ResponseEntity<Restaurant> getResturantByUserId(@PathVariable("id") String id) {
        List <Restaurant> restaurant = restaurantRepository.findByUserId(id);

        System.out.println(restaurant);
        if (restaurant != null) {
            return new ResponseEntity(restaurant.get(0), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Update restaurant data
    @PutMapping("/update-restaurant/")
    public ResponseEntity<User> updateRestaurantInfo(@RequestBody Restaurant restaurantParams) {
        Optional <Restaurant> restaurant = restaurantRepository.findById(restaurantParams.getId());

        if (restaurant.isPresent()) {
            Restaurant _restaurant = restaurant.get();
            _restaurant.setDescription(restaurantParams.getDescription());
            _restaurant.setPhone(restaurantParams.getPhone());
            _restaurant.setRestaurantName(restaurantParams.getRestaurantName());
            _restaurant.setAddress(restaurantParams.getAddress());

            return  new ResponseEntity(restaurantRepository.save(_restaurant), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //List restaurants for homepage
    @GetMapping("/restaurants")
    public ResponseEntity<User> getRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        if (restaurants != null) {
            return new ResponseEntity(restaurants, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
