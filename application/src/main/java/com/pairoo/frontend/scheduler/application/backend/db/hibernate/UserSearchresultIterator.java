/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pairoo.frontend.scheduler.application.backend.db.hibernate;

import com.pairoo.domain.User;
import com.pairoo.frontend.scheduler.application.backend.dao.UserDao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author klaus
 */
public class UserSearchresultIterator implements Iterator<User> {

    private final int cacheSize = 1000000;
    private final UserDao userDao;
    private int counter = 0;
    private int currentUserIndex = 0;
    private List<User> currentLoadedUsers = new ArrayList<User>();

    public UserSearchresultIterator(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean hasNext() {
        if (currentUserIndex >= currentLoadedUsers.size()) {
            currentLoadedUsers = userDao.searchUsersForWeekendSuggestions(counter * cacheSize, cacheSize);
            counter++;
        }
        return currentLoadedUsers.size() > 0;
    }

    public User next() {
        remove();
        currentUserIndex++;
        if (currentUserIndex >= currentLoadedUsers.size()) {
            return null;
        }
        return currentLoadedUsers.get(currentUserIndex);
    }

    public void remove() {
        //any logic necessary?
    }
}
