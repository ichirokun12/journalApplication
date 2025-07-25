package net.engineeringdigest.journalApp.controller;

// import java.util.List;

// import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.security.core.Authentication;
// import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
// import org.springframework.web.bind.annotation.PostMapping;


// import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/user")
@Tag(name = "User APIs")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;
    


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String userName = authentication.getName();
       User userInDb = userService.findByUserName(userName);
       if(userInDb != null) {
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
       }
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting = " ";
        if (weatherResponse != null ) {
            greeting = " weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("hi " + authentication.getName() + greeting ,HttpStatus.OK);
    }
}
