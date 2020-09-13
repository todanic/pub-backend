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
import pub.pubbackend.model.Tutorial;
import pub.pubbackend.model.User;
import pub.pubbackend.repository.AuthRepository;
import pub.pubbackend.repository.RestaurantRepository;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")

public class AuthController {
    @Autowired
    AuthRepository authRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    //Register user
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {

            User _user = authRepository
                    .save(new User(user.getEmail(), user.getName(), user.getRestaurantName(), user.getPassword()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Delete user
    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            authRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get user profile
    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getProfileById(@PathVariable("id") long id) {
        Optional<User> profileData = authRepository.findById(id);

        if (profileData.isPresent()) {
            return new ResponseEntity<>(profileData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/profile-restaurant/{id}")
    public ResponseEntity<Restaurant> getResturantByUserId(@PathVariable("id") String id) {
       List <Restaurant> restaurant = restaurantRepository.findByUserId(id);

        System.out.println(restaurant);
        if (restaurant != null) {
            return new ResponseEntity(restaurant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<List<User>> login(@RequestBody User userParam) {
        try {
            List<User> user = authRepository.findUserByEmailAndPassword(userParam.getEmail(), userParam.getPassword());

            if (user.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Update user profile
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateProfile(@PathVariable("id") long id, @RequestBody User user) {
        Optional<User> authData = authRepository.findById(id);

        if (authData.isPresent()) {
            User _user = authData.get();
            _user.setEmail(user.getEmail());
            _user.setName(user.getName());
            _user.setRestaurantName(user.getRestaurantName());
            _user.setPassword(user.getPassword());
            return new ResponseEntity<>(authRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
