/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pairoo.frontend.scheduler.application.business;

import com.datazuul.framework.domain.Language;
import com.pairoo.backend.dao.GeoLocationDao;
import com.pairoo.backend.dao.UserDao;
import com.pairoo.domain.User;
import com.pairoo.domain.enums.FamilyStatusType;
import com.pairoo.domain.enums.SmokeType;
import com.pairoo.frontend.scheduler.application.business.l10n.LocalizedStrings;
import com.pairoo.frontend.scheduler.application.util.DomainObjectGenerator;
import com.pairoo.frontend.scheduler.application.util.SmtpServerRunner;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.subethamail.wiser.WiserMessage;

/**
 * Some guys would call this test already a integration test, but leave it as
 * unit test.
 *
 * @author klaus
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:com/pairoo/frontend/scheduler/application/springBeans.xml"
})
public class PartnerSuggestionsJobTest {

    @Autowired
    private JobDetail partnerSuggestionJob;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GeoLocationDao geoLocationDao;
    @Autowired
    private SmtpServerRunner smtpSrvRunner;

    public PartnerSuggestionsJobTest() {
    }

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
    public void testInject() {
        Assert.assertNotNull(partnerSuggestionJob);

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
        user02.getSearchProfile().setSmokeType(SmokeType.DONT_SMOKE);
        user02.getUserAccount().getNotificationSettings().setWeekendSuggestions(true);
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
        user05.getUserAccount().getNotificationSettings().setWeekendSuggestions(true);
        user05.getUserAccount().setPreferredLanguage(Language.GERMAN);
        user05.getSearchProfile().setSmokeType(SmokeType.OCCASIONALLY);
        User user06 = objectGenerator.getUser();
        user06.getUserProfile().getLifeStyle().setSmokeType(SmokeType.SMOKE.SMOKE);
        userDao.save(user01, user02, user03, user04, user05, user06);
        Assert.assertEquals(amountOfUsers + 6, userDao.findAll().size());

        smtpSrvRunner.start();

        SchedulerFactory sf = new StdSchedulerFactory();
        try {
            Scheduler s = sf.getScheduler();

            Trigger t = TriggerBuilder.newTrigger().withIdentity("kName", "kGroup").forJob(partnerSuggestionJob).startNow().build();

            s.deleteJob(partnerSuggestionJob.getKey());
            s.scheduleJob(partnerSuggestionJob, t);
            s.start();
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PartnerSuggestionsJobTest.class.getName()).log(Level.SEVERE, null, ex);
            }

            s.shutdown(true);

        } catch (SchedulerException ex) {
            Logger.getLogger(PartnerSuggestionsJobTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        smtpSrvRunner.stop();

        List<WiserMessage> receivedMails = smtpSrvRunner.getMessages();

        Assert.assertEquals(2, receivedMails.size());
        for (WiserMessage mail : receivedMails) {
            Assert.assertFalse(mail.toString().isEmpty());
        }

        userDao.remove(user01, user02, user03, user04, user05, user06);
    }

    @Test
    public void testSubjectL10n() {
        Locale.setDefault(Locale.ENGLISH);
        String subjectEN = LocalizedStrings.get(LocalizedStrings.EMAIL_SUBJECT, Locale.ENGLISH);
        String subjectDE = LocalizedStrings.get(LocalizedStrings.EMAIL_SUBJECT, Locale.GERMAN);

        Assert.assertTrue(subjectEN != null && !subjectEN.isEmpty());
        Assert.assertTrue(subjectDE != null && !subjectDE.isEmpty());
        Assert.assertFalse(subjectDE.equals(subjectEN));

        String enumTranslation = LocalizedStrings.get(FamilyStatusType.DONT_SAY, Locale.GERMAN);
        Assert.assertNotNull(enumTranslation);
        Assert.assertFalse(enumTranslation.isEmpty());
    }
}
