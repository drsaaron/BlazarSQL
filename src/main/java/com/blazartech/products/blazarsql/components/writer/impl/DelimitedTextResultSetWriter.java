/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.writer.impl;

import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author aar1069
 * @version $Id: DelimitedTextResultSetWriter.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log$
 *******************************************************************************/
public class DelimitedTextResultSetWriter extends ResultSetWriterImpl {

    private char fieldDelimiter;

    public char getFieldDelimiter() {
        return fieldDelimiter;
    }

    public void setFieldDelimiter(char fieldDelimiter) {
        this.fieldDelimiter = fieldDelimiter;
    }
    
    @Override
    public boolean wantsQuery() {
        return false;
    }

    @Override
    public boolean wantsResultSetHeaders() {
        return false;
    }

    @Override
    public void saveQuery(Writer writer, String userID, String serverName, String query) throws IOException {
    }

    @Override
    public void startData(Writer writer) throws IOException {
    }

    @Override
    public void start(Writer writer, String title) throws IOException {
    }

    @Override
    public void finish(Writer writer) throws IOException {
    }

    @Override
    public void addHeader(Writer writer, String[] columnHeaders) throws IOException {
    }

    @Override
    public void addRow(Writer writer, String[] rowData) throws IOException {
        for (int i = 0; i < rowData.length; i++) {
            String d = rowData[i];
            if (d == null) {
                d = getNullValue();
            }
            writer.write(d);
            if (i < rowData.length - 1) { writer.write(getFieldDelimiter()); }
        }
        writer.write(newLine);
    }

    @Override
    public void endData(Writer writer) throws IOException {
    }

    @Override
    public String getNullValue() {
        return "";
    }
    
}
