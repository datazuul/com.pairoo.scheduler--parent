package com.pairoo.frontend.scheduler.application.business.mailcontent;

import java.util.Locale;
import java.util.Map;

/**
 *
 * @author klaus
 */
public interface MailcontentCreator {

    public String getTextMail(Map<String, Object> parameterMap, Locale locale);

    public String getHtmlMail(Map<String, Object> parameterMap, Locale locale);
}
