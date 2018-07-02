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
public final class ProcessingStage {
    
    /** Holds value of property name. */
    private String name;
    
    public static final ProcessingStage WAITING = new ProcessingStage("Waiting");
    
    public static final ProcessingStage RUNNING = new ProcessingStage("running");
    
    public static final ProcessingStage FORMATTING = new ProcessingStage("formatting");
    
    /** Creates a new instance of ProcessingStage
     * @param name */
    protected ProcessingStage(String name) {
        setName(name);
    }
    
    /** Getter for property name.
     * @return Value of property name.
     *
     */
    public String getName() {
        return this.name;
    }
    
    /** Setter for property name.
     * @param name New value of property name.
     *
     */
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
}
