//package net.engineeringdigest.journalApp.service;
//
//import net.engineeringdigest.journalApp.JournalEntryRepository.UserRepository;
//import net.engineeringdigest.journalApp.entity.User;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//import java.util.ArrayList;
//
//import static org.mockito.Mockito.*;
//
//
//public class UserDetailServiceImplTest {
//    @InjectMocks
//    private UserDetailServiceImpl userDetailService;
//
//
//    @Mock
//    private UserRepository userRepository;
//
//    // following method used to initialize all the mocks as we are not using @autowired
//    @BeforeAll
//    void setUp(){
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void loadUserByUsernameTest(){
//        // when findByUserName is called give following username and password as user dummy input
//        // So that it will not fetch the db and time will be saved
//        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("Sachin").password("dgsafdgfg").roles(new ArrayList<>()).build());
//        UserDetails user = userDetailService.loadUserByUsername("Sachin");
//        Assertions.assertNotNull(user);
//    }
//
//}
