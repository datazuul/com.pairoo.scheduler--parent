/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pairoo.frontend.scheduler.application.util;

import com.datazuul.framework.domain.geo.GeoLocation;
import com.pairoo.backend.dao.GeoLocationDao;
import com.pairoo.domain.Address;
import com.pairoo.domain.Appearance;
import com.pairoo.domain.ImageEntry;
import com.pairoo.domain.LifeStyle;
import com.pairoo.domain.NotificationSettings;
import com.pairoo.domain.SearchProfile;
import com.pairoo.domain.User;
import com.pairoo.domain.UserAccount;
import com.pairoo.domain.UserProfile;
import com.pairoo.domain.enums.Ethnicity;
import com.pairoo.domain.geo.GeoArea;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author klaus
 */
public class DomainObjectGenerator {

    private final GeoLocationDao geoLocationDao;

    public DomainObjectGenerator(GeoLocationDao geoLocationDao) {
        this.geoLocationDao = geoLocationDao;
    }

    public User getUser() {
        HashMap<String, Object> propertiesWithSpecials = new HashMap<String, Object>();

        NotificationSettings notificationSettings = getInitializesObject(new NotificationSettings(), null);

        UserAccount account = getInitializesObject(new UserAccount(), propertiesWithSpecials);
        account.setNotificationSettings(notificationSettings);

        Address address = getInitializesObject(new Address(), null);

        SearchProfile searchProfile = getInitializesObject(new SearchProfile(), null);
        searchProfile.setMaxAge(88);
        searchProfile.setMinAge(18);
        searchProfile.setGeoArea(new GeoArea());
        searchProfile.setEthnicities(new ArrayList<Ethnicity>());
        searchProfile.getEthnicities().add(Ethnicity.ANY);

        Appearance appearance = getInitializesObject(new Appearance(), null);

        LifeStyle lifestyle = getInitializesObject(new LifeStyle(), null);

        GeoLocation geolocation = null;
        if (geoLocationDao != null) {
            geolocation = geoLocationDao.getReference(1l);
        }

        ImageEntry imageEntry = getInitializesObject(new ImageEntry(), null);

        UserProfile userProfile = getInitializesObject(new UserProfile(), null);
        userProfile.setAppearance(appearance);
        userProfile.setLifeStyle(lifestyle);
        userProfile.setGeoLocation(geolocation);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -35);
        userProfile.setBirthdate(c.getTime());

        List<ImageEntry> imageEntries = new ArrayList<ImageEntry>();
        imageEntries.add(imageEntry);
        userProfile.setImageEntries(imageEntries);

        propertiesWithSpecials.put("email".toLowerCase(), "email@earth.at");
        User user = getInitializesObject(new User(), propertiesWithSpecials);
        user.setSearchProfile(searchProfile);
        user.setUserAccount(account);
        user.setAddress(address);
        user.setUserProfile(userProfile);
        return user;
    }

    private <T> T getInitializesObject(T object, HashMap<String, Object> propertiesWithSpecials) {

        List<String> propertiesToIgnore = new ArrayList<String>();
        propertiesToIgnore.add("id".toLowerCase());
        propertiesToIgnore.add("serialVersionUID".toLowerCase());
        propertiesToIgnore.add("uuid".toLowerCase());
        propertiesToIgnore.add("version".toLowerCase());

        List<Field> fieldsOfObject = getAllFields(new ArrayList<Field>(), object.getClass());

        for (Field field : fieldsOfObject) {
            if (propertiesToIgnore.contains(field.getName().toLowerCase())) {
                continue;
            }
            try {
                field.setAccessible(true);
                // special init
                if (propertiesWithSpecials != null && propertiesWithSpecials.containsKey(field.getName().toLowerCase())) {
                    field.set(object, propertiesWithSpecials.get(field.getName().toLowerCase()));
                    continue;
                }
                if (field.getType().equals(String.class)) {
                    field.set(object, field.getName());
                    continue;
                } else if (field.getType().isPrimitive()) {
                    //leave as default
                    continue;
                } else {
                    field.set(object, null);
                    continue;
                }

            } catch (IllegalArgumentException ex) {
                Logger.getLogger(DomainObjectGenerator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(DomainObjectGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return object;
    }

    private List<Field> getAllFields(List<Field> fields, Class type) {
        for (Field field : type.getDeclaredFields()) {
            fields.add(field);
        }

        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }
}
