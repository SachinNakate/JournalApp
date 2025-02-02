package Sachin.journalApp.service;

import Sachin.journalApp.JournalEntryRepository.UserRepository;
import Sachin.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service// Spring pass hone ke liye
//@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    // instead above statement we will use @Slf4j

    public boolean saveNewUser(User user){ // to save entity in DB
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;

        }catch (Exception e){
//            log.info("Hello");
            return true;

        }

    }
    public void saveAdmin(User user){ // to save entity in DB
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);

    }
    public void saveUser(User user){ // to save entity in DB
        userRepository.save(user);

    }


    public List<User> getAll(){
        return userRepository.findAll();
    }
    public Optional<User> getById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);

    }

    public User findByUserName(String userName){ // to get username
        return userRepository.findByUserName(userName);
    }
}
