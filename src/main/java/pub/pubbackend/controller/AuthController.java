package pub.pubbackend.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pub.pubbackend.model.User;
import pub.pubbackend.services.NotificationService;
import pub.pubbackend.repository.AuthRepository;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")

public class AuthController {
    @Autowired
    AuthRepository authRepository;

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

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/contact")
    public ResponseEntity sendNotification(@RequestBody Map<String, String> data) {
        String email = (String) data.get("email");
        String text = (String) data.get("message");

        String returnMessage = notificationService.sendEmail(email, text);

        if (returnMessage.equals("success")) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
