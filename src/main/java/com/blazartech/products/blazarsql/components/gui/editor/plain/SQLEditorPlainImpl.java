/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.gui.editor.plain;

import com.blazartech.products.blazarsql.components.gui.editor.SQLEditor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * a dumb implementation of the SQL editor, one that just uses the default behavior
 * and thus plain JEditorPane functionality.  
 * 
 * @author aar1069
 * @version $Id: SQLEditorPlainImpl.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log$
 *******************************************************************************/
@Component
@Scope("prototype")
@Profile("!syntaxpane")
public class SQLEditorPlainImpl extends SQLEditor {

    public SQLEditorPlainImpl() {
    }
    
    
}
