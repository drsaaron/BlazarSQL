/*
 * TableColumnListTableModel.java
 *
 * Created on April 14, 2004, 9:23 AM
 */

package com.blazartech.products.blazarsql.components.gui;

import com.blazartech.products.blazarsql.components.dataobjects.TableColumnDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 * Table model to describe the columns in a database table.
 * @author Dr. Scott E. Aaron
 * @version $Id: TableColumnListTableModel.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log: TableColumnListTableModel.java,v $
/* Revision 1.3  2007/07/17 20:34:17  AAR1069
/* Some code clean up.  Use more imports rather than fully qualified class names.
/*
/* Revision 1.2  2006/10/05 18:34:24  aar1069
/* Added javadoc.
/*
 *******************************************************************************/

public class TableColumnListTableModel extends DefaultTableModel {
        
    private static final String[] HEADERS = new String[] { "Name", "Type" };
    private final List<TableColumnDescriptor> _data = new ArrayList<>();
    
    /**
     * Creates a new instance of TableColumnListTableModel
     * @param columnList list of columns in the database table.
     */
    public TableColumnListTableModel(Collection<TableColumnDescriptor> columnList) {
        super();
        _data.addAll(columnList);
        fireTableStructureChanged();
    }
    
    /**
     * Is this cell editable. No.
     * @param row row number
     * @param column column number
     * @return false
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    /**
     * Get the number of columns in the UI table.
     * @return 
     */
    @Override
    public int getColumnCount() {
        return HEADERS.length;
    }
    
    /**
     * Get the number of rows in the UI table.
     * @return 
     */
    @Override
    public int getRowCount() {
        return (_data == null) ? 0 : _data.size();
    }
    
    /**
     * Get the value to render in a given cell.
     * @param row row number
     * @param column column number
     * @return value
     */
    @Override
    public Object getValueAt(int row, int column) {
        TableColumnDescriptor d = _data.get(row);
        switch (column) {
            case 0:
                return d.getName();
            case 1:
                return d.getTypeName();
        }
        return null;
    }
    
    /**
     * Get a column header for the UI table.
     * @param column column number
     * @return column header
     */
    @Override
    public String getColumnName(int column) {
        return HEADERS[column];
    }
    
}
