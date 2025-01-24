package Sachin.journalApp.controller;

import Sachin.journalApp.api.reponse.WeatherResponse;
import Sachin.journalApp.JournalEntryRepository.UserRepository;
import Sachin.journalApp.entity.User;
import Sachin.journalApp.service.UserService;
import Sachin.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController // This is special class where we put all urls/ endpoints
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;


    // We need authenticate  delete and update both controllers

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User  userInDb = userService.findByUserName(userName);
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUserById(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Pune");
        String greeting = "";
        if (weatherResponse != null){
            greeting = " Weather feels like " + weatherResponse.getCurrent().getFeelslike();

        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greeting ,HttpStatus.OK);
    }




}
