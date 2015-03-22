/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pairoo.frontend.scheduler.application.business.mailcontent;

import com.pairoo.frontend.scheduler.application.business.mailcontent.FreemarkerMailCreator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class FreemarkerMailCreatorTest {

    @Autowired
    private FreemarkerMailCreator mailCreator;
    private final Logger logger = LoggerFactory.getLogger(FreemarkerMailCreatorTest.class);

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
    public void testMailCreation() {

        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("firstname", "asdfghjklö");
        parameterMap.put("proposalName1", "6zhn");
        parameterMap.put("proposalProfileimage1", 123);
        parameterMap.put("proposalUrl1", 123);

        String result = mailCreator.getHtmlMail(parameterMap, Locale.GERMAN);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains(parameterMap.get("firstname").toString()));
        logger.info(result);
    }
}
