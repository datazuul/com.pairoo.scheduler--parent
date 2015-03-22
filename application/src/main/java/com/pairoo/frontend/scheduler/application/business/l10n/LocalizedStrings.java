/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pairoo.frontend.scheduler.application.business.l10n;

import java.util.Locale;

/**
 *
 * @author klaus
 */
public class LocalizedStrings extends com.datazuul.framework.i18n.LocalizedStrings {

    private static final String BASENAME = LocalizedStrings.class.getName();
    public static final LocalizedStringIdentifier EMAIL_SUBJECT = new LocalizedStringIdentifier("email.subject");

    public static String get(final LocalizedStringIdentifier key, final Locale locale) {
        String result = LocalizedStrings.get(key.toString(), BASENAME, locale);

        return result;
    }

    public static <E extends Enum> String get(final E enumElement, final Locale locale) {
        if (enumElement == null) {
            return null;
        }
        return get(LocalizedStrings.getKey(enumElement), locale);
    }

    private static String simpleName(Class<?> c) {
        String simpleName = null;
        if (c != null) {
            while (c.isAnonymousClass()) {
                c = c.getSuperclass();
            }
            simpleName = c.getSimpleName();
        }

        return simpleName;
    }

    public static <E extends Enum> LocalizedStringIdentifier getKey(E enumElement) {
        String enumKey = simpleName(enumElement.getDeclaringClass()) + '.' + enumElement.name();
        return new LocalizedStringIdentifier(enumKey);
    }
}
