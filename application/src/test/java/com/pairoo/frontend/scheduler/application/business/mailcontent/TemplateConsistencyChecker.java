/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pairoo.frontend.scheduler.application.business.mailcontent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.HTMLDocumentBuilder;
import org.custommonkey.xmlunit.TolerantSaxDocumentBuilder;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author klaus
 */
/**
 *
 * @author klaus
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:com/pairoo/frontend/scheduler/application/springBeans.xml"
})
public class TemplateConsistencyChecker {

    @Autowired
    private FreemarkerMailCreator mailCreator;

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
    public void testHtmlTemplates() throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {

        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("firstname", "asdfghjklö");
        parameterMap.put("proposalName1", "6zhn");
        parameterMap.put("proposalProfileimage1", 123);
        parameterMap.put("proposalUrl1", 123);

        String resultMaster = mailCreator.getHtmlMail(parameterMap, Locale.ENGLISH);
        TolerantSaxDocumentBuilder tolerantSaxDocumentBuilder = new TolerantSaxDocumentBuilder(XMLUnit.newTestParser());
        HTMLDocumentBuilder htmlDocumentBuilder = new HTMLDocumentBuilder(tolerantSaxDocumentBuilder);
        Document resultMasterDocument = htmlDocumentBuilder.parse(resultMaster);

        final List<Locale> languagesToCheck = new ArrayList<Locale>();
        languagesToCheck.add(Locale.GERMAN);
        languagesToCheck.add(Locale.ITALIAN);

        final List<String> xPathForElementsToIgnore = new ArrayList<String>();
        xPathForElementsToIgnore.add("/html[1]/body[1]/div[1]/p[1]/text()[1]"); //Greeting text; 'Dear....'
        xPathForElementsToIgnore.add("/html[1]/body[1]/div[1]/p[2]/text()[1]"); //Common text; 'The properties of these..'
        xPathForElementsToIgnore.add("/html[1]/body[1]/div[1]/p[3]/text()[1]"); //Text: 'You didn't found you love yet'?
        xPathForElementsToIgnore.add("/html[1]/body[1]/div[1]/p[4]/text()[1]"); //Text 'Currently numberless singles are sign in to Pairoo.de every day. Check immediately for new profiles and find your love.'
        xPathForElementsToIgnore.add("/html[1]/body[1]/div[1]/p[5]/text()[1]"); //Text 'The Pairoo Team keep the fingers crossed and hope that you will find your love soon.'
        xPathForElementsToIgnore.add("/html[1]/body[1]/div[1]/p[6]/text()[1]"); //Text ' Cheers'
        xPathForElementsToIgnore.add("/html[1]/body[1]/div[1]/p[7]/text()[1]"); //Text 'Your PAIROO Support Team'
        xPathForElementsToIgnore.add("/html[1]/body[1]/div[1]/p[8]/text()[1]"); //Text 'Contact us vial email to support@pairoo.de.'

        for (Locale l : languagesToCheck) {

            String resultToCompare = mailCreator.getHtmlMail(parameterMap, l);
            Document resultDeDocument = htmlDocumentBuilder.parse(resultToCompare);

            Diff diff = new Diff(resultMasterDocument, resultDeDocument);
            diff.overrideDifferenceListener(new SuperDuperDifferenceListener(xPathForElementsToIgnore));

//            XMLUnit.setIgnoreComments(true);
//            XMLUnit.setIgnoreWhitespace(true);

            Assert.assertTrue(diff.similar());
        }


    }

    private class SuperDuperDifferenceListener implements DifferenceListener {

        private final List<String> xPathForElementsToIgnore;

        public SuperDuperDifferenceListener(final List<String> xPathForElementsToIgnore) {
            this.xPathForElementsToIgnore = xPathForElementsToIgnore;
        }

        public int differenceFound(Difference difference) {
            if (xPathForElementsToIgnore.contains(difference.getControlNodeDetail().getXpathLocation())) {
                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
            }
            return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
        }

        public void skippedComparison(Node control, Node test) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
