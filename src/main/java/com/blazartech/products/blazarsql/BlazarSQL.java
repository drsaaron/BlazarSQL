/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql;

import com.blazartech.products.blazarsql.components.gui.MainWindow;
import java.awt.EventQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * main program for the new BlazarSQL
 * 
 * @author aar1069
 * @version $Id: BlazarSQL.java 30 2015-04-23 19:52:54Z aar1069 $
 */
@Component
public class BlazarSQL implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BlazarSQL.class);

    @Autowired
    private MainWindow mainWindow;
    
    @Override
    public void run(String... strings) throws Exception {
        EventQueue.invokeLater(() -> {
            mainWindow.setVisible(true);
        });
    }
}
