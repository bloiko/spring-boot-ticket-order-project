package com.example.repository;

import com.example.DbConfiguration;
import com.example.order.pojos.Role;
import com.example.order.repository.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { DbConfiguration.class },
        loader = AnnotationConfigContextLoader.class)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Transactional
    public void givenRole_whenGetByRoleName_thenGetOk() {
        Role role_admin = new Role(0L, "ROLE_ADMIN");
        roleRepository.save(role_admin);

        Role role = roleRepository.findByRoleName("ROLE_ADMIN").get();


        assertEquals("ROLE_ADMIN", role.getRoleName());
        assertEquals("ROLE_ADMIN", role.getAuthority());
    }
}
