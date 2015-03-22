/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pairoo.frontend.scheduler.application.backend.db.hibernate;

import com.pairoo.domain.User;
import com.pairoo.frontend.scheduler.application.backend.dao.UserDao;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

/**
 *
 * @author klaus
 */
public class UserDaoImpl extends com.pairoo.backend.db.hibernate.UserDaoImpl implements UserDao {

    public Iterator<User> searchUsersForWeekendSuggestions() {
        return new UserSearchresultIterator(this);
    }

    public List<User> searchUsersForWeekendSuggestions(Integer firstResult, Integer maxResults) {
        Criteria rootCriteria = getSession().createCriteria(User.class);
        rootCriteria.createAlias("userAccount", "ua");
        rootCriteria.createAlias("ua.notificationSettings", "ns");
        rootCriteria.createAlias("userProfile", "up");
        rootCriteria.createAlias("up.lifeStyle", "ls");

        rootCriteria.add(Restrictions.eq("ns.weekendSuggestions", true));

        rootCriteria.addOrder(Order.asc("id"));

        rootCriteria.setFirstResult(firstResult);
        rootCriteria.setMaxResults(maxResults);
        rootCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return rootCriteria.list();
    }
}
