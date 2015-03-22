/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pairoo.frontend.scheduler.application.business.mailcontent;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

/**
 *
 * @author klaus
 */
public class FreemarkerMailCreator implements MailcontentCreator {

    private final Logger logger = LoggerFactory.getLogger(FreemarkerMailCreator.class);
    private Configuration freemarkerconfiguation;
    private String nameOfTextTemplate;
    private String nameOfHtmlTemplate;

    public String getTextMail(Map<String, Object> parameterMap, Locale locale) {
        return getMail(nameOfTextTemplate, parameterMap, locale);
    }

    public String getHtmlMail(Map<String, Object> parameterMap, Locale locale) {
        return getMail(nameOfHtmlTemplate, parameterMap, locale);
    }

    private String getMail(String nameOfTemplate, Map<String, Object> parameterMap, Locale locale) {
        String result = null;
        try {
            Template template;
            if (locale == null) {
                template = freemarkerconfiguation.getTemplate(nameOfTemplate);
            } else {
                template = freemarkerconfiguation.getTemplate(nameOfTemplate, locale);
            }
            result = FreeMarkerTemplateUtils.processTemplateIntoString(template, parameterMap);
        } catch (IOException ex) {
            logger.error("Could not process template.", ex);
        } catch (TemplateException ex) {
            logger.error("Could not process template.", ex);
        }
        return result;
    }

    public void setFreemarkerconfiguation(Configuration freemarkerconfiguation) {
        this.freemarkerconfiguation = freemarkerconfiguation;
    }

    public void setNameOfTextTemplate(String nameOfTextTemplate) {
        this.nameOfTextTemplate = nameOfTextTemplate;
    }

    public void setNameOfHtmlTemplate(String nameOfHtmlTemplate) {
        this.nameOfHtmlTemplate = nameOfHtmlTemplate;
    }
}
