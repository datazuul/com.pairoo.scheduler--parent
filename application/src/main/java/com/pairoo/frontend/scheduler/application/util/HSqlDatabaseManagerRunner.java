/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pairoo.frontend.scheduler.application.util;

/**
 *
 * @author klaus
 */
public class HSqlDatabaseManagerRunner {

    private String connectionString;

    public void start() {
        org.hsqldb.util.DatabaseManagerSwing.main(new String[]{"--url", connectionString, "--noexit"});
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
}
