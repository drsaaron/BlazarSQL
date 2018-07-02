/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.writer.impl;

import com.blazartech.products.blazarsql.Constants;
import com.blazartech.products.blazarsql.components.writer.ResultSetWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author aar1069
 * @version $Id: ResultSetWriterImpl.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log$
 *******************************************************************************/
public abstract class ResultSetWriterImpl implements ResultSetWriter {

    public static final String newLine = System.getProperty("line.separator");
    
    @Override
    public void start(Writer writer) throws IOException {
        start(writer, "Query results from " + Constants.PROGRAM_NAME);
    }
}
