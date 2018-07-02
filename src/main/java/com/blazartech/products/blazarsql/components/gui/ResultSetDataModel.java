/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.gui;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aar1069
 * @version $Id: ResultSetDataModel.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log$
 *******************************************************************************/
public final class ResultSetDataModel extends AbstractTableModel {
    
    private static final Logger logger = LoggerFactory.getLogger(ResultSetDataModel.class);
    
    private List<String> _headers = new ArrayList<>();
    
    private List<List<Object>> _data = new ArrayList<>();
    
    private List<Class> _columnClasses = new ArrayList<>();
    
    private static final String NULL_FIELD = "NULL";
    
    private static final String NULL_COLUMN = "Col";
    
    /** Holds value of property updateTableRowCount. */
    private int updateTableRowCount = 128;
    
    /** Creates a new instance of ResultSetDataModel
     * @param results
     * @throws java.sql.SQLException */
    public ResultSetDataModel(ResultSet results) throws SQLException {
        // get the header information.
        int _nullColumnCounter = 0;
        ResultSetMetaData meta = results.getMetaData();
        int ncolumns = meta.getColumnCount();
        for (int i = 0; i < ncolumns; i++) {
            String head = meta.getColumnName(i + 1);
            if (head == null || head.equals("")) { 
                head = NULL_COLUMN + " (" + ++_nullColumnCounter + ")"; 
            }
            _headers.add(head);
            
            try {
                Class columnClass = Class.forName(meta.getColumnClassName(i+1));
                _columnClasses.add(columnClass);
            } catch (ClassNotFoundException e) {
                logger.error("class not found: " + e.getMessage(), e);
                _columnClasses.add(String.class);
            }
        }

        // Now the data.
        int rowNumber = 0;
        while (results.next()) {
            List<Object> row = new ArrayList<>();
            for (int c = 0; c < ncolumns; c++) {
                Object val = results.getObject(c+1);
                row.add(val);
            }
            _data.add(row);
            
            if (++rowNumber % getUpdateTableRowCount() == 0) {
                fireTableStructureChanged();
            }
        }

        // done.
        fireTableStructureChanged();
    }
    
    @Override
    public int getColumnCount() {
        return _headers.size();
    }
    
    @Override
    public int getRowCount() {
        return _data.size();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<Object> row = _data.get(rowIndex);
        return row.get(columnIndex);
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    @Override
    public String getColumnName(int column) {
        return _headers.get(column);
    }
    
    @Override
    public Class getColumnClass(int columnIndex) {
        return _columnClasses.get(columnIndex);
    }
    
    /** Getter for property updateTableRowCount.
     * @return Value of property updateTableRowCount.
     *
     */
    public int getUpdateTableRowCount() {
        return this.updateTableRowCount;
    }
    
    /** Setter for property updateTableRowCount.
     * @param updateTableRowCount New value of property updateTableRowCount.
     *
     */
    public void setUpdateTableRowCount(int updateTableRowCount) {
        this.updateTableRowCount = updateTableRowCount;
    }
    
}
