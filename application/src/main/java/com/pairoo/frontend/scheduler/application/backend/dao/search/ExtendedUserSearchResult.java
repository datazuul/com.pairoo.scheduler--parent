/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pairoo.frontend.scheduler.application.backend.dao.search;

import com.pairoo.domain.search.UserSearchResult;
import org.springframework.core.style.ToStringCreator;

/**
 *
 * @author klaus
 */
public class ExtendedUserSearchResult extends UserSearchResult {

    @Override
    public String toString() {
        return new ToStringCreator(this).toString();
    }
}
