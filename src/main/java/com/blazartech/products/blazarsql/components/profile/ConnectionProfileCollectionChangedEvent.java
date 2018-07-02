/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.profile;

import java.util.EventObject;

/**
 *
 * @author AAR1069
 */
public class ConnectionProfileCollectionChangedEvent extends EventObject {
    
    public ConnectionProfileCollectionChangedEvent(ConnectionProfileManager source) {
        super(source);
    }
    
    public ConnectionProfileManager getProfileManager() {
        return (ConnectionProfileManager) this.source;
    }

    
}
