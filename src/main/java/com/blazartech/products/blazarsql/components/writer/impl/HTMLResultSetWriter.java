/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.writer.impl;

import com.blazartech.products.blazarsql.Constants;
import com.blazartech.products.blazarsql.components.writer.ResultSetWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

/**
 *
 * @author aar1069
 * @version $Id: HTMLResultSetWriter.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log$
 *******************************************************************************/
public class HTMLResultSetWriter extends ResultSetWriterImpl implements ResultSetWriter {

    @Override
    public boolean wantsQuery() {
        return true;
    }

    @Override
    public boolean wantsResultSetHeaders() {
        return true;
    }

    @Override
    public void saveQuery(Writer writer, String userID, String serverName, String query) throws IOException {
        query = query.replaceAll("\n", newLine);
        writer.write("<!-- Query: (user = " + userID + ", server = " + serverName + 
                     ")" + newLine + newLine + query + newLine + "-->" + newLine);
    }

    @Override
    public void startData(Writer writer) throws IOException {
        writer.write("<table>" + newLine);
    }

    @Override
    public void start(Writer writer, String title) throws IOException {
        writer.write("<html>" + newLine);
        writer.write("<head>" + newLine);
        writer.write("  <title>" + title + "</title>" + newLine);
        writer.write("  <style>" + newLine);
        writer.write("root { display: block; }" + newLine);
        writer.write("table { border-style: inset; border-width: 1px; }" + newLine);
        writer.write("thead th { font-weight: bold; background-color: #ccccff; border-style: inset;  border-width: 1px  }" + newLine);
        writer.write("tr td { background-color: #FFFACD /* lemonchiffon */; border-style: inset;  border-width: 1px  }" + newLine);
        writer.write("  </style>" + newLine);
        writer.write("</head>" + newLine);
        writer.write("<!-- Created by " + Constants.PROGRAM_NAME + ", version " + Constants.PROGRAM_VERSION +
                     ", &copy; " + Constants.COPYRIGHT_RANGE +
                     " by " + Constants.AUTHOR + ", at " + (new Date()).toString() + "-->" + newLine);
        writer.write("<body>" + newLine);
    }

    @Override
    public void finish(Writer writer) throws IOException {
        writer.write("</body></html>" + newLine);
    }

    @Override
    public void addHeader(Writer writer, String[] columnHeaders) throws IOException {
        writer.write("<thead>");
        for (String h : columnHeaders) {
            if (h == null) { h = ""; }
            writer.write("<th>" + h);
        }
        writer.write("</thead>" + newLine);
    }

    @Override
    public void addRow(Writer writer, String[] rowData) throws IOException {
        writer.write("<tr>");
        for (String h : rowData) {
            if (h == null) { h = getNullValue(); }
            writer.write("<td>" + h);
        }
        writer.write("</tr>" + newLine); 
    }

    @Override
    public void endData(Writer writer) throws IOException {
        writer.write("</table>" + newLine);
    }

    @Override
    public String getNullValue() {
        return "NULL";
    }
    
}
