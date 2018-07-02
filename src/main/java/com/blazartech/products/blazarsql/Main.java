/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author AAR1069
 */
@SpringBootApplication
@ImportResource("classpath:SpringXMLConfig.xml")
@ComponentScan("com.blazartech")
@PropertySource("classpath:blazarSQL.properties")
public class Main {
    
    public static void main(String[] args) {
        //ApplicationContext context = SpringApplication.run(Main.class, args);
        ApplicationContext context = new SpringApplicationBuilder(Main.class).headless(false).run(args);
    }
}
