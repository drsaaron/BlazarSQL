/*
 * ProcessingStage.java
 *
 * Created on April 13, 2004, 2:04 PM
 */

package com.blazartech.products.blazarsql.components.gui;

/**
 *
 * @author  AAR1069
 */
public enum ProcessingStage {
    
    WAITING("Waiting"), RUNNING("running"), FORMATTING("formatting");
    
    /** Holds value of property name. */
    private final String name;
    
    /** Creates a new instance of ProcessingStage
     * @param name */
    private ProcessingStage(String name) {
        this.name = name;
    }
    
    /** Getter for property name.
     * @return Value of property name.
     *
     */
    public String getName() {
        return this.name;
    }
    
    
}
