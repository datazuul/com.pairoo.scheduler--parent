/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pairoo.frontend.scheduler.application.backend.dao;

import com.pairoo.domain.User;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author klaus
 */
public interface UserDao extends com.pairoo.backend.dao.UserDao {

    /*-
     * NOT YET WORKING
     * Should someday replace the other search method and hide
     * the need for paging within the implementation of the iterator.
     * btw: Will be changed to javadoc later; but so it is not reformated.
     *
     * @return
     */
    public Iterator<User> searchUsersForWeekendSuggestions();

    public List<User> searchUsersForWeekendSuggestions(Integer firstResult, Integer maxResults);
}
