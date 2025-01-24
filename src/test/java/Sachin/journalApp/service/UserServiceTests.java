package Sachin.journalApp.service;

import Sachin.journalApp.JournalEntryRepository.UserRepository;
import Sachin.journalApp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;
    @Test
    public void testFindByUserName(){
        User user = userRepository.findByUserName("Sachin");
        assertTrue(!user.getJournalEntries().isEmpty()); // That user's journal entry is not empty

    }
}
