/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.products.blazarsql.components.gui.editor;

import com.blazartech.products.blazarsql.components.gui.editor.jsyntax.SQLEditorJSyntaxPaneImpl;
import com.blazartech.products.blazarsql.components.gui.editor.plain.SQLEditorPlainImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author scott
 */
@Configuration
public class SQLEditorConfiguration {
    
    @Value("${blazarSQL.editor.implementationType}")
    private String implementationType;
    
    @Bean
    @Scope("prototype")
    public SQLEditor sqlEditor() {
        switch (implementationType) {
            case "plain":
                return new SQLEditorPlainImpl();
            case "syntaxpane":
                return new SQLEditorJSyntaxPaneImpl();
            default:
                throw new IllegalArgumentException("editor type can only be plain or syntaxpane, not " + implementationType);
        }
    }
}
