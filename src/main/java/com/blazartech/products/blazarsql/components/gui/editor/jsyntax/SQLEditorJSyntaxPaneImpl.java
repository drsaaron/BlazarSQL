/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.gui.editor.jsyntax;

import com.blazartech.products.blazarsql.components.gui.editor.SQLEditor;
import java.awt.Font;
import java.awt.event.HierarchyListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import jsyntaxpane.SyntaxDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implement an SQL editor using the JSyntaxPane package.  As the syntax kit
 * includes a definition of an undo manager, we must override the base undo/redo
 * methods to leverage what's provided in the SyntaxDocument.  it would be good
 * to be able to leverage this and still update the undoable and redoable properties
 * of the base class.
 * 
 * @author aar1069
 * @version $Id: SQLEditorJSyntaxPaneImpl.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log$
 *******************************************************************************/
@Component("sqlEditorJSyntaxPane")
@Scope("prototype")
public class SQLEditorJSyntaxPaneImpl extends SQLEditor implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SQLEditorJSyntaxPaneImpl.class);

    /** Creates new form BeanForm */
    public SQLEditorJSyntaxPaneImpl() {
        super();
        initComponents();
    }

    private void initComponents() {
        setFont(new Font("Courier New", 0, 12));
    }

    @Override
    public void undoChange() throws CannotUndoException {
        Document d = getDocument();
        if (d instanceof SyntaxDocument) {
            ((SyntaxDocument) d).doUndo();
        }
    }

    @Override
    public void redoChange() throws CannotRedoException {
        Document d = getDocument();
        if (d instanceof SyntaxDocument) {
            ((SyntaxDocument) d).doRedo();
        }
    }

    @Autowired
    private HierarchyListener editorHierarchyChangeListener;
    
    @Override
    public void afterPropertiesSet() throws Exception {        
        addHierarchyListener(editorHierarchyChangeListener);
    }
    
}
