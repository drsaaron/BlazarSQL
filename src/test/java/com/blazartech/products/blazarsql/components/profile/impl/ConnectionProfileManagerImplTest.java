/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.blazartech.products.blazarsql.components.profile.impl;

import com.blazartech.products.blazarsql.components.profile.ConnectionProfile;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author scott
 */
@ExtendWith(SpringExtension.class)
public class ConnectionProfileManagerImplTest {
    
    private static final Logger logger = LoggerFactory.getLogger(ConnectionProfileManagerImplTest.class);
    
    private static class TestConnectionProfileManagerImpl extends ConnectionProfileManagerImpl {

        public static final ConnectionProfile DEFAULT_CONNECTION_PROFILE = new ConnectionProfile();
        public static final ConnectionProfile NON_DEFAULT_CONNECTION_PROFILE = new ConnectionProfile();
        
        static {
            DEFAULT_CONNECTION_PROFILE.setDefaultProfile(true);
            NON_DEFAULT_CONNECTION_PROFILE.setDefaultProfile(false);
        }
        
        @Override
        public Collection<ConnectionProfile> getProfiles() {
            return List.of(DEFAULT_CONNECTION_PROFILE, NON_DEFAULT_CONNECTION_PROFILE);
        }

        @Override
        protected void persistProfiles() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        protected void loadProfiles() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
        
    }
    
    @TestConfiguration
    public static class ConnectionProfileManagerImplTestConfiguration {
        
        @Bean
        public ConnectionProfileManagerImpl instance() {
            return new TestConnectionProfileManagerImpl();
        }
    }
    
    @Autowired
    private ConnectionProfileManagerImpl instance;
            
    public ConnectionProfileManagerImplTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    /**
     * Test of getDefaultProfile method, of class ConnectionProfileManagerImpl.
     */
    @Test
    public void testGetDefaultProfile() {
        logger.info("getDefaultProfile");
        
        ConnectionProfile expResult = TestConnectionProfileManagerImpl.DEFAULT_CONNECTION_PROFILE;
        ConnectionProfile result = instance.getDefaultProfile();
        assertEquals(expResult, result);
    }
    
}
