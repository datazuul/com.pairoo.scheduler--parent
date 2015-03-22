/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pairoo.frontend.scheduler.application.domain.util;

import com.pairoo.backend.dao.search.DaoUserSearchParams;
import com.pairoo.domain.SearchProfile;
import com.pairoo.domain.User;
import com.pairoo.domain.enums.SmokeType;
import java.util.Calendar;

/**
 *
 * @author klaus
 */
@Deprecated
public class DomTranslator {

    public DaoUserSearchParams translate(final DaoUserSearchParams searchParams, final User user) {

        if (user == null || user.getSearchProfile() == null) {
            return searchParams;
        }

        SearchProfile searchProfile = user.getSearchProfile();

        if (searchProfile.getMinAge() != null) {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.YEAR, searchProfile.getMinAge());
            searchParams.setBirthdateOldest(now.getTime());
        }
        if (searchProfile.getMaxAge() != null) {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.YEAR, searchProfile.getMaxAge());
            searchParams.setBirthdateYoungest(now.getTime());
        }
//        searchParams.setCountry(Country.ANY);
        if (searchProfile.getEthnicities() != null && !searchProfile.getEthnicities().isEmpty()) {
            searchParams.setEthnicities(searchProfile.getEthnicities());
        }
        searchParams.setIdOfSearchingUser(user.getId());
//        searchParams.setMaxLatitude(Double.NaN);
//        searchParams.setMaxLongitude(Double.NaN);
//        searchParams.setMinLatitude(Double.NaN);
//        searchParams.setMinLongitude(Double.NaN);
        searchParams.setPartnerType(searchProfile.getPartnerType());
        searchParams.setProfilePictureType(searchProfile.getProfilePictureType());
        if (searchProfile.getSmokeType() != null && !searchProfile.getSmokeType().equals(SmokeType.ANY)) {
            searchParams.setSmokeType(searchProfile.getSmokeType());
        }
//        searchParams.setZipcode(searchProfile.ge);
//        searchParams.setZipcodeStartsWith(rootUrl);
        return searchParams;
    }
}
