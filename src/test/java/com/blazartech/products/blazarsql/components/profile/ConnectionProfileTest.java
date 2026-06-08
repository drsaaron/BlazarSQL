/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.blazartech.products.blazarsql.components.profile;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author scott
 */
@ExtendWith(SpringExtension.class)
public class ConnectionProfileTest {
    
    private static final Logger logger = LoggerFactory.getLogger(ConnectionProfileTest.class);
    
    @TestConfiguration
    public static class ConnectionProfileTestConfiguration {
        
    }
    
    public ConnectionProfileTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of compareTo method, of class ConnectionProfile.
     */
    @Test
    public void testCompareTo() {
        logger.info("compareTo");
        
        ConnectionProfile p1 = new ConnectionProfile();
        p1.setServerType("Type1");
        p1.setUserID("user1");
        p1.setServerName("server1");
        p1.setDatabaseName("database1");
        
        ConnectionProfile p2 = new ConnectionProfile();
        p2.setServerType("Type1");
        p2.setUserID("user1");
        p2.setServerName("server1");
        p2.setDatabaseName("database2");
        
        ConnectionProfile p3 = new ConnectionProfile();
        p3.setServerType("Type1");
        p3.setUserID("user1");
        p3.setServerName("server2");
        p3.setDatabaseName("database0");
        
        ConnectionProfile p4 = new ConnectionProfile();
        p4.setServerType("Type1");
        p4.setUserID("user2");
        p4.setServerName("server0");
        p4.setDatabaseName("database1");
        
        ConnectionProfile p5 = new ConnectionProfile();
        p5.setServerType("Type2");
        p5.setUserID("user1");
        p5.setServerName("server1");
        p5.setDatabaseName("database1");
        
        int p1p2 = p1.compareTo(p2); // compare on server type
        int p1p3 = p1.compareTo(p3); // compare on userID
        int p1p4 = p1.compareTo(p4); // compare on server name
        int p1p5 = p1.compareTo(p5); // compare on database name
        
        assertTrue(p1p2 < 0);
        assertTrue(p1p3 < 0);
        assertTrue(p1p4 < 0);
        assertTrue(p1p5 < 0);
    }

    
}
