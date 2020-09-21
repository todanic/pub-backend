package pub.pubbackend.controller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import pub.pubbackend.model.Restaurant;
import pub.pubbackend.model.User;
import pub.pubbackend.model.Photo;
import pub.pubbackend.repository.RestaurantRepository;
import pub.pubbackend.repository.PhotoRepository;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")

public class RestaurantController {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    PhotoRepository photoRepository;

    public static String uploadDirectory = System.getProperty("user.dir")+"/photos";

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

    //Get restaurant data by user id
    @GetMapping("/profile-restaurant/{id}")
    public ResponseEntity<Restaurant> getResturantByUserId(@PathVariable("id") String id) {
        List <Restaurant> restaurant = restaurantRepository.findByUserId(id);

        if (restaurant != null) {
            return new ResponseEntity(restaurant.get(0), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/restaurant-profile/{id}")
    public ResponseEntity<Restaurant> getResturantById(@PathVariable("id") Long id) {
        Optional <Restaurant> restaurant = restaurantRepository.findById(id);

        if (restaurant != null) {
            return new ResponseEntity(restaurant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Update restaurant data
    @PutMapping("/update-restaurant/")
    public ResponseEntity updateRestaurantInfo(@RequestBody Restaurant restaurantParams) {
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

    //Upload restaurant profile image
    @RequestMapping(value = "/update-restaurant-photo/" , method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> updateRestaurantInfo(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "restaurantId") String id) {
        Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());

        //Write img to photos folder
        try {
            byte[] bytes = file.getBytes();
            Files.write(fileNameAndPath, bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        //Encode image as base64
        String imageBase64 = "";

        try {
            imageBase64 = Base64.getEncoder().encodeToString(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        //Save photo to the db
        try {
            Photo _photo = new Photo();
            _photo.setRestaurantId(id);
            _photo.setPath(fileNameAndPath.toString());
            _photo.setFilename(file.getOriginalFilename());
            photoRepository.save(_photo);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(imageBase64, HttpStatus.OK);
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
