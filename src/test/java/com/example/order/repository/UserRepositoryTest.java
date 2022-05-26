package com.example.order.repository;

import com.example.DbConfiguration;
import com.example.order.pojos.Role;
import com.example.order.pojos.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { DbConfiguration.class },
        loader = AnnotationConfigContextLoader.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void givenUser_whenGetByRoleName_thenGetOk() {
        String username = "username";
        Role role_user = new Role(0L, "ROLE_USER");
        User user = new User(username, "password", role_user);
        userRepository.save(user);

        User userFromDb = userRepository.findByUsername(username).get();


        assertEquals(username, userFromDb.getUsername());
        assertEquals("password", userFromDb.getPassword());
        assertTrue(userFromDb.getRoles().stream().map(Role::getRoleName).anyMatch("ROLE_USER"::equals));
    }
}
