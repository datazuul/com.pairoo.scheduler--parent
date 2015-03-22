/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pairoo.frontend.scheduler.application.business.l10n;

/**
 *
 * @author klaus
 */
public class LocalizedStringIdentifier {

    private String id;

    LocalizedStringIdentifier(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
