/*
 * SingleExtensionFileFilter.java
 *
 * Created on April 13, 2004, 9:09 AM
 */

package com.blazartech.products.blazarsql.components.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author  AAR1069
 */
public class SingleExtensionFileFilter extends FileFilter {
    
    /** Holds value of property extension. */
    private String extension;
    
    /** Holds value of property description. */
    private String description;
    
    /** Get the extension on a file name, <I>e.g.</I> gif for file1.gif.
     * @param file The file.
     * @return The extension.
     */        
    public String getExtension(File file) {
        String ext = null;
        String s = file.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    /** Creates a new instance of SingleExtensionFileFilter */
    public SingleExtensionFileFilter() {
    }
    
    @Override
    public boolean accept(File f) {
        String fileExtension = getExtension(f);
        return (f.isDirectory() || (fileExtension != null && fileExtension.equals(getExtension())));
    }
    
    /** Getter for property extension.
     * @return Value of property extension.
     *
     */
    public String getExtension() {
        return this.extension;
    }
    
    /** Setter for property extension.
     * @param extension New value of property extension.
     *
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }
    
    /** Getter for property description.
     * @return Value of property description.
     *
     */
    @Override
    public String getDescription() {
        return this.description;
    }
    
    /** Setter for property description.
     * @param description New value of property description.
     *
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}
