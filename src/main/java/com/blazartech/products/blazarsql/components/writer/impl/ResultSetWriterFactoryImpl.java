/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.writer.impl;

import com.blazartech.products.blazarsql.components.writer.ResultSetWriter;
import com.blazartech.products.blazarsql.components.writer.ResultSetWriterFactory;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author aar1069
 * @version $Id: ResultSetWriterFactoryImpl.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log$
 *******************************************************************************/
public class ResultSetWriterFactoryImpl implements ResultSetWriterFactory {

    private Map<String, ResultSetWriter> writerMap;

    public Map<String, ResultSetWriter> getWriterMap() {
        return writerMap;
    }

    public void setWriterMap(Map<String, ResultSetWriter> writerMap) {
        this.writerMap = writerMap;
    }
    
    @Override
    public Collection<String> getSupportedWriterTypes() {
        return getWriterMap().keySet();
    }

    @Override
    public ResultSetWriter getResultSetWriter(String writerType) {
        return getWriterMap().get(writerType);
    }
    
}
