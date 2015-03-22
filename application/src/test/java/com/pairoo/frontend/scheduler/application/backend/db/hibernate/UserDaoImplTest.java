/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pairoo.frontend.scheduler.application.backend.db.hibernate;

import com.pairoo.backend.dao.GeoLocationDao;
import com.pairoo.backend.dao.search.DaoUserSearchParams;
import com.pairoo.domain.User;
import com.pairoo.domain.enums.SmokeType;
import com.pairoo.domain.search.UserSearchResult;
import com.pairoo.frontend.scheduler.application.backend.dao.UserDao;
import com.pairoo.frontend.scheduler.application.util.DomainObjectGenerator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author klaus
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:com/pairoo/frontend/scheduler/application/springBeans.xml"
})
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private GeoLocationDao geoLocationDao;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCRUD() {
        int amountOfUser = userDao.findAll().size();

        User user01 = new DomainObjectGenerator(geoLocationDao).getUser();
        userDao.save(user01);
        Assert.assertEquals(amountOfUser + 1, userDao.findAll().size());

        User userLoaded = userDao.find(user01.getId());
        Assert.assertNotNull(userLoaded);
        Assert.assertNotNull(userLoaded.getAddress());

        User userToDelete = new User();
        userToDelete.setId(user01.getId());
        userDao.remove(userToDelete);
        Assert.assertEquals(amountOfUser, userDao.findAll().size());
    }

    @Test
    public void testSearch() {
        int amountOfUsers = userDao.findAll().size();

        DomainObjectGenerator objectGenerator = new DomainObjectGenerator(geoLocationDao);
        User user01 = objectGenerator.getUser();
        user01.getUserProfile().getLifeStyle().setSmokeType(SmokeType.SMOKE.DONT_SMOKE);
        user01.getUserProfile().getImageEntries().get(0).setProfileImage(true);
        user01.getUserProfile().getImageEntries().get(0).setVisible(true);
        User user02 = objectGenerator.getUser();
        user02.getUserProfile().getLifeStyle().setSmokeType(SmokeType.SMOKE.DONT_SMOKE);
        user02.getUserProfile().getImageEntries().get(0).setProfileImage(true);
        user02.getUserProfile().getImageEntries().get(0).setVisible(true);
        User user03 = objectGenerator.getUser();
        user03.getUserProfile().getLifeStyle().setSmokeType(SmokeType.SMOKE.DONT_SMOKE);
        user03.getUserProfile().setImageEntries(null);
        User user04 = objectGenerator.getUser();
        user04.getUserProfile().getLifeStyle().setSmokeType(SmokeType.SMOKE.OCCASIONALLY);
        user04.getUserProfile().getImageEntries().get(0).setProfileImage(true);
        user04.getUserProfile().getImageEntries().get(0).setVisible(false);
        User user05 = objectGenerator.getUser();
        user05.getUserProfile().getLifeStyle().setSmokeType(SmokeType.SMOKE.OCCASIONALLY);
        user05.getUserProfile().getImageEntries().get(0).setProfileImage(false);
        user05.getUserProfile().getImageEntries().get(0).setVisible(true);
        User user06 = objectGenerator.getUser();
        user06.getUserProfile().getLifeStyle().setSmokeType(SmokeType.SMOKE.SMOKE);
        userDao.save(user01, user02, user03, user04, user05, user06);
        Assert.assertEquals(amountOfUsers + 6, userDao.findAll().size());

        DaoUserSearchParams searchParams = new DaoUserSearchParams();
        searchParams.setSmokeType(SmokeType.DONT_SMOKE);
        searchParams.setIdOfSearchingUser(Long.MIN_VALUE);
        List<UserSearchResult> result = userDao.search(searchParams, 0, 100);
        Assert.assertEquals(3, result.size());
        Assert.assertNotNull(result.get(0).getProfileImageEntry());
        Assert.assertNotNull(result.get(1).getProfileImageEntry());
        Assert.assertNull(result.get(2).getProfileImageEntry());

        searchParams.setSmokeType(SmokeType.SMOKE.OCCASIONALLY);
        result = userDao.search(searchParams, 0, 100);
        Assert.assertEquals(2, result.size());
        Assert.assertNull(result.get(0).getProfileImageEntry());
        Assert.assertNull(result.get(1).getProfileImageEntry());

        userDao.remove(user01, user02, user03, user04, user05, user06);
    }

    //@Test
    public void testPerformanceOfLoadingUsers() {
        DomainObjectGenerator objectGenerator = new DomainObjectGenerator(geoLocationDao);
        List<Long> idsOfCreatedUsers = new ArrayList<Long>();
        Random zipcodeRandomizer = new Random();
        for (int i = 0; i < 1000; ++i) {
            User user = objectGenerator.getUser();
            user.getAddress().setZipcode(String.valueOf(zipcodeRandomizer.nextInt(5)));
            idsOfCreatedUsers.add(user.getId());
            userDao.save(user);
        }

        //init hibernate
        userDao.findAll();

        long defaultStartTime = System.nanoTime();
        List<User> foundUsersDefault = userDao.findAll();
        long defaultStopTime = System.nanoTime();
        long durationDefaultLoad = defaultStopTime - defaultStartTime;
        long quickStartTime = System.nanoTime();
        List<UserSearchResult> foundUserQuick = userDao.search(null, 0, Long.MAX_VALUE);
        long quickStopTime = System.nanoTime();
        long durationQuickLoad = quickStopTime - quickStartTime;
        Assert.assertEquals(foundUsersDefault.size(), foundUserQuick.size());
        Assert.assertTrue(durationQuickLoad < durationDefaultLoad);
        System.out.println("Duration hibernate default load-all:  " + durationDefaultLoad);
        System.out.println("Duration hibernate criteria load-all: " + durationQuickLoad);

        userDao.removeByIds(idsOfCreatedUsers.toArray(new Long[idsOfCreatedUsers.size()]));
    }

    @Test
    public void testsearchUsersForWeekendSuggestions() {
        DomainObjectGenerator objectGenerator = new DomainObjectGenerator(geoLocationDao);
        List<Long> idsOfCreatedUsers = new ArrayList<Long>();
        int amountOfUsers = 10;
        for (int i = 0; i < amountOfUsers; ++i) {
            User user = objectGenerator.getUser();
            user.getUserAccount().getNotificationSettings().setWeekendSuggestions(true);
            userDao.save(user);
            idsOfCreatedUsers.add(user.getId());
        }
        List<User> result = userDao.searchUsersForWeekendSuggestions(0, 100);
        Assert.assertEquals(10, result.size());

        result = userDao.searchUsersForWeekendSuggestions(6, 10);
        Assert.assertEquals(4, result.size());
        result = userDao.searchUsersForWeekendSuggestions(0, 5);
        Assert.assertEquals(5, result.size());

        userDao.removeByIds(idsOfCreatedUsers.toArray(new Long[idsOfCreatedUsers.size()]));
    }

    //@Test
    /*
     * With the help of this test, out of memory could be tested when loading users for weekend suggestions mails.
     * Just set the variable loadUserBatches to 1 an a OoMemory Exception should be thrown.
     */
    public void testsearchUsersForWeekendSuggestionsForOutOfMemory() {
        DomainObjectGenerator objectGenerator = new DomainObjectGenerator(geoLocationDao);
        List<Long> idsOfCreatedUsers = new ArrayList<Long>();
        int amountOfUsers = 50 * 1000;
        for (int i = 0; i < amountOfUsers + 1; ++i) {
            if (i % 1000 == 0) {
                System.out.println("Stored " + i + " users.");
            }
            User user = objectGenerator.getUser();
            user.getUserAccount().getNotificationSettings().setWeekendSuggestions(true);
            idsOfCreatedUsers.add(user.getId());
            userDao.save(user);
        }

        int loadUserBatches = 5;
        List<User> result = userDao.searchUsersForWeekendSuggestions(0, amountOfUsers / loadUserBatches);
        System.out.println("Found " + result.size() + " users for weekend suggestions. Id of first is " + result.get(0).getId());

        userDao.removeByIds(idsOfCreatedUsers.toArray(new Long[idsOfCreatedUsers.size()]));
    }

    //@Test
    public void testsearchUsersForWeekendSuggestionsWithIterator() {

        List<User> resultAsList = userDao.searchUsersForWeekendSuggestions(0, 10);
        Assert.assertEquals(0, resultAsList.size());

        Iterator<User> result = userDao.searchUsersForWeekendSuggestions();
        Assert.assertFalse(result.hasNext());

        DomainObjectGenerator objectGenerator = new DomainObjectGenerator(geoLocationDao);
        List<Long> idsOfCreatedUsers = new ArrayList<Long>();
        int amountOfUsers = 50 * 1000;
        for (int i = 0; i < amountOfUsers + 1; ++i) {
            if (i % 1000 == 0) {
                System.out.println("Stored " + i + " users.");
            }
            User user = objectGenerator.getUser();
            user.getUserAccount().getNotificationSettings().setWeekendSuggestions(true);
            idsOfCreatedUsers.add(user.getId());
            userDao.save(user);
        }

        result = userDao.searchUsersForWeekendSuggestions();
        int i = 1;
        while (result.hasNext()) {
            if (i % 1000 == 0) {
                System.out.println("Found " + i + " users for weekendsuggestions.");
            }
            User u = result.next();
            System.out.println(u.getId() + " ");
        }

        userDao.removeByIds(idsOfCreatedUsers.toArray(new Long[idsOfCreatedUsers.size()]));

    }
}
