/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.writer;

import java.util.Collection;

/**
 *
 * @author aar1069
 */
public interface ResultSetWriterFactory {
    
    /**
     * get the types of result set writers supported.
     * 
     * @return 
     */
    Collection<String> getSupportedWriterTypes();
    
    /**
     * get the writer appropriate to the given type.  
     * 
     * @param writerType must be one of the strings returned by @see getSupportedWriterTypes
     * @return 
     */
    ResultSetWriter getResultSetWriter(String writerType);
}
