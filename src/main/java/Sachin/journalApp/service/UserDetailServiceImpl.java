package Sachin.journalApp.service;

import Sachin.journalApp.JournalEntryRepository.UserRepository;
import Sachin.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user != null){
             return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                     // following line getting the user list which is coming while creating user
                    .roles(user.getRoles().toArray(new String[0])) // converting list to array and then data type which you want.
                    // we passed zero ro resize the list
                    .build();


        }
        throw new UsernameNotFoundException("User not found with username: " + username);

    }
}
