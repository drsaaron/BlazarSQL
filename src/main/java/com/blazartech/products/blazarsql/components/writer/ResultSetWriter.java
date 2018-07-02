/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.writer;

import java.io.IOException;
import java.io.Writer;

/**
 * interface for a component to save query results to a file.
 * @author aar1069
 */
public interface ResultSetWriter {
    
    /**
     * does the writer make use of the query?
     * @return 
     */
    boolean wantsQuery();
    
    /**
     * does the writer make use of result set headers?
     * @return 
     */
    boolean wantsResultSetHeaders();
    
    /**
     * save the query
     * @param writer 
     * @param userID
     * @param serverName
     * @param query
     * @throws IOException 
     */
    void saveQuery(Writer writer, String userID, String serverName, String query) throws IOException;
    
    /**
     * start the data section of the output.
     * @param writer
     * @throws IOException 
     */
    void startData(Writer writer) throws IOException;
    
    /**
     * start the output
     * 
     * @param writer
     * @param title title for the output
     * @throws IOException 
     */
    void start(Writer writer, String title) throws IOException;
    
    /**
     * start the output. a default title is used.
     * 
     * @param writer
     * @throws IOException 
     */
    void start(Writer writer) throws IOException;
    
    /**
     * finish the output
     * 
     * @param writer
     * @throws IOException 
     */
    void finish(Writer writer) throws IOException;
    
    /**
     * add a header to the output.
     * 
     * @param writer
     * @param columnHeaders
     * @throws IOException 
     */
    void addHeader(Writer writer, String[] columnHeaders) throws IOException;
    
    /**
     * add a row of data to the output.
     * 
     * @param writer 
     * @param rowData 
     * @throws IOException 
     */
    void addRow(Writer writer, String[] rowData) throws IOException;
    
    /**
     * end the data section
     * 
     * @param writer
     * @throws IOException 
     */
    void endData(Writer writer) throws IOException;
    
    /**
     * get the string to be used to represent NULL values.
     * 
     * @return 
     */
    String getNullValue();
}
