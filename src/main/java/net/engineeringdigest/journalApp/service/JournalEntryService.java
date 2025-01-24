package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.JournalEntryRepository.JournalEntryRepository;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service// Spring pass hone ke liye

public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(@NotNull JournalEntry journalEntry, String userName){ // to save entity in DB
            try {
                User user = userService.findByUserName(userName); // finding user
                journalEntry.setDate(LocalDateTime.now());
                JournalEntry saved =  journalEntryRepository.save(journalEntry); // saving the journal into local variable
                user.getJournalEntries().add(saved); // adding journal entries
                //user.setUserName(null); // To show the use of transactional annotation
                userService.saveUser(user);

            }catch (Exception e){
                System.out.println(e);
                throw new RuntimeException("An error occurred while saving the entry.",e) ;
            }
    }
    public void saveEntry(JournalEntry journalEntry){ // to save entity in DB
        journalEntryRepository.save(journalEntry);
    }


    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }


    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id)); // This statement is actually removing the entries from user
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }

        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting the entry." , e);

        }
        return removed;
    }

//    public List<JournalEntry> findByUserName(String userName){
//
//    }
}
