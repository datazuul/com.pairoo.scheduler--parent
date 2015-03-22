package com.pairoo.frontend.scheduler.application.business;

import com.datazuul.framework.domain.geo.Country;
import com.datazuul.framework.domain.geo.GeoLocation;
import com.pairoo.backend.dao.search.DaoUserSearchParams;
import com.pairoo.business.api.GeoLocationService;
import com.pairoo.domain.ImageEntry;
import com.pairoo.domain.PersonProfile;
import com.pairoo.domain.User;
import com.pairoo.domain.enums.FamilyStatusType;
import com.pairoo.domain.enums.PartnerType;
import com.pairoo.domain.search.UserSearchResult;
import com.pairoo.frontend.scheduler.application.backend.dao.UserDao;
import com.pairoo.frontend.scheduler.application.business.l10n.LocalizedStrings;
import com.pairoo.frontend.scheduler.application.business.mailcontent.MailcontentCreator;
import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.measure.quantity.Length;
import javax.measure.unit.Unit;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Assert;

/**
 *
 * @author klaus
 */
public class PartnerSuggestionsJob implements Job {

    private final Logger logger = LoggerFactory.getLogger(PartnerSuggestionsJob.class);
    private final int blockSizeOfUserLoading = 100; //means, 100 users for PartnerSuggestions will be loaded in one block; other with the next block..
    private final int amountOfSuggestions = 3; // (up to) three suggestions are made to each user; templates are configured accordingly
    private final Map<PartnerType, String> genderImages;
    private GeoLocationService geoLocationService;
    private boolean isInstanceInitialized = false;
    private MailcontentCreator mailcontentCreator;
    private JavaMailSender mailServer;
    private String mailSenderAddress;
    private String rootUrl;
    private UserDao userDao;

    public PartnerSuggestionsJob() {
        this.genderImages = new EnumMap<>(PartnerType.class);
        genderImages.put(PartnerType.FEMALE, "female-gender-sign.jpg");
        genderImages.put(PartnerType.MALE, "male-gender-sign.jpg");
        genderImages.put(PartnerType.ANY, "unknown-gender-sign.jpg");
        Locale.setDefault(Locale.ENGLISH);
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Job execution started..");

        if (!isInstanceInitialized) {
            initializeInstanceVariables(context);
        }

        int initialStartIndex = 0;
        int count = 0;
        while (true) { // loop is ended with break
            int startIndex = (count * blockSizeOfUserLoading) + initialStartIndex;

            List<User> usersThatWantToReceivePartnerSuggestions = userDao.searchUsersForWeekendSuggestions(startIndex, blockSizeOfUserLoading);
            logger.info("Loading user that want to receive weekend suggestions. Page " + (count + 1)
                    + " (Start at " + startIndex + ", try to load " + blockSizeOfUserLoading + " entries. Actual loaded: "
                    + usersThatWantToReceivePartnerSuggestions.size() + ")");
            String idsOfLoadedUser = "";
            for (User u : usersThatWantToReceivePartnerSuggestions) {
                if (idsOfLoadedUser.length() > 0) {
                    idsOfLoadedUser += ", ";
                }
                idsOfLoadedUser += u.getId();
            }
            logger.info("Loaded User identifiers: " + idsOfLoadedUser);
            count++;

            if (usersThatWantToReceivePartnerSuggestions.isEmpty()) {
                break; // ends while loop
            }

            for (User user : usersThatWantToReceivePartnerSuggestions) {
                Map<String, Object> parameterMapForReplacements = new HashMap<>();
                parameterMapForReplacements.put("firstname", user.getUserAccount().getUsername());

                DaoUserSearchParams searchParams = new DaoUserSearchParams(user, user.getSearchProfile());

                final List<UserSearchResult> matchingUsers = userDao.search(searchParams, 0, amountOfSuggestions);
                logger.info("For user " + user.getId() + ": Found " + matchingUsers.size() + " matching users for suggestions.");

                if (matchingUsers.isEmpty()) {
                    continue; // no matching users found; no weekend suggestions mail
                }

                Assert.notNull(user.getUserProfile()); // every user should have a userProfile
                Locale locale = Locale.ENGLISH; //default
                if (user.getUserAccount().getPreferredLanguage() != null) {
                    switch (user.getUserAccount().getPreferredLanguage()) {
                        case ANY: // should not happen as user can not select any as preferred language, but if..
                            break; // leave default;
                        default:
                            locale = user.getUserAccount().getPreferredLanguage().getLocale();
                    }
                }

                int proposalCount = 1;
                for (UserSearchResult matchingUser : matchingUsers) {
                    String proposalUrl = createProfileDetailsUrl(user, matchingUser, locale.getLanguage());
                    parameterMapForReplacements.put("proposalUrl" + proposalCount, proposalUrl);
                    parameterMapForReplacements.put("proposalName" + proposalCount, matchingUser.getUsername());
                    int age = PersonProfile.getAge(matchingUser.getBirthdate());
                    if (age > 0) {
                        parameterMapForReplacements.put("proposalAge" + proposalCount, String.valueOf(age));
                    }

                    FamilyStatusType familyStatusTypOfMatchingUser = matchingUser.getFamilyStatusType();
                    if (familyStatusTypOfMatchingUser != null) {
                        switch (familyStatusTypOfMatchingUser) {
                            case ANY: //should not happen
                            case DONT_SAY: //does not make sense to show it
                                break;  // .. so leave empty
                            default:
                                parameterMapForReplacements.put("proposalFamilyState" + proposalCount, LocalizedStrings.get(familyStatusTypOfMatchingUser, locale));
                        }
                    }

                    //disabled as we (Ralf, Klaus) were not sure if it makes really sense to include distance information in proposal mail (on 2013-15-08)
                    //parameterMapForReplacements.putAll(calulateGeolocationProperties(user, matchingUser, proposalCount));
                    final ImageEntry profileImageEntry = matchingUser.getProfileImageEntry();
                    parameterMapForReplacements.put("proposalProfileimage" + proposalCount, createImageUrl(profileImageEntry, matchingUser));
                    proposalCount += 1;
                }
                String htmlContent = mailcontentCreator.getHtmlMail(parameterMapForReplacements, locale);
                String textContent = mailcontentCreator.getTextMail(parameterMapForReplacements, locale);

                try {
                    MimeMessage mail = createMail(user, textContent, htmlContent, locale);
                    mailServer.send(mail);
                    logger.info("Successfully sent mail to " + user.getEmail());
                } catch (MessagingException | MailException ex) {
                    logger.info("Could not send mail to " + user.getEmail(), ex);
                }

            }
        }
        logger.info("Job execution finished!");
    }

    private synchronized MimeMessage createMail(User receipient, String textContent, String htmlContent, Locale locale) throws MessagingException {
        MimeMessage mail = mailServer.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true);
        mailHelper.setText(textContent, htmlContent);
        String subject = MessageFormat.format(LocalizedStrings.get(LocalizedStrings.EMAIL_SUBJECT, locale), receipient.getFirstname());
        mailHelper.setSubject(subject);
        mailHelper.setTo(receipient.getEmail());
        mailHelper.setFrom(mailSenderAddress);
        return mail;
    }

    /**
     * *
     * This method is only required as i did not get dependency injection
     * working for Quartz Jobs. (even if i generate the public setters..)
     *
     * @param context
     */
    private void initializeInstanceVariables(JobExecutionContext context) {
        if (userDao == null) {
            userDao = (UserDao) context.getJobDetail().getJobDataMap().get("userDao");
        }
        if (mailcontentCreator == null) {
            mailcontentCreator = (MailcontentCreator) context.getJobDetail().getJobDataMap().get("mailcontentCreator");
        }
        if (mailServer == null) {
            mailServer = (JavaMailSender) context.getJobDetail().getJobDataMap().get("mailServer");
        }
        if (geoLocationService == null) {
            geoLocationService = (GeoLocationService) context.getJobDetail().getJobDataMap().get("geoLocationService");
        }
        if (mailSenderAddress == null) {
            String key = "mailSenderAddress";
            mailSenderAddress = (String) context.getJobDetail().getJobDataMap().get(key);
            if (mailSenderAddress == null || mailSenderAddress.isEmpty()) {
                String msg = "Configuration for key " + key + " has to be set and is not allowed to be empty. Please configure in spring config.";
                logger.error(msg);
                throw new RuntimeException(msg);
            }
        }

        if (rootUrl == null) {
            rootUrl = (String) context.getJobDetail().getJobDataMap().get("rootUrl");
        }
        isInstanceInitialized = true;
    }

    /**
     * As we (ralf & klaus) were not sure if geo information should be included
     * into suggestions, i moved the logic to this method.
     *
     * @param userThatWantTheSuggestions
     * @param userThatWillBeSuggested
     * @return
     */
    private Map<String, String> calulateGeolocationProperties(User userThatWantTheSuggestions, UserSearchResult userThatWillBeSuggested, int proposalCount) {
        Map<String, String> result = new HashMap<>();

        if (userThatWantTheSuggestions == null || userThatWillBeSuggested == null) {
            return result;
        }

        final GeoLocation geoLocationOfUser = userThatWantTheSuggestions.getUserProfile().getGeoLocation();
        Country usersCountry = null; // Country is needed, to derive the default distance unit of this specific country from
        if (geoLocationOfUser == null || geoLocationOfUser.getLatitude() == null || geoLocationOfUser.getLongitude() == null) {
            return result;
        }
        try {  // why try-catch here.. do not know. Ralf maybe knows more
            usersCountry = geoLocationOfUser.getCountry();
        } catch (Exception e) {
            usersCountry = Country.GERMANY;
        } finally {
            if (usersCountry == null) {
                usersCountry = Country.GERMANY;
            }
        }
        Unit<Length> preferredUnit = usersCountry.getUnitDistance();
        GeoLocation geoLocationOfMatchingUser = userThatWillBeSuggested.getGeoLocation();
        if (geoLocationOfMatchingUser != null && geoLocationOfMatchingUser.getLatitude() != null && geoLocationOfMatchingUser.getLongitude() != null) {
            String distanceInPreferredUnit = geoLocationService.distanceInPreferredUnit(geoLocationOfUser, geoLocationOfMatchingUser, preferredUnit);
            result.put("proposalDistance" + proposalCount, distanceInPreferredUnit);
        }

        return result;
    }

    /**
     * create url to profiledetails with preferred language param
     * @param user visitor
     * @param matchingUser profile's user
     * @param language preferred language
     * @return URL to profile details
     */
    private String createProfileDetailsUrl(final User user, final UserSearchResult matchingUser, final String language) {
        // see WicketWebApplication mounts
        return rootUrl + "p/" + user.getId() + "/" + matchingUser.getId() + "?pl=" + language;
    }

    private String createImageUrl(final ImageEntry profileImageEntry, final UserSearchResult matchingUser) {
        if (profileImageEntry == null) {
            return rootUrl + "images/" + genderImages.get(matchingUser.getPartnerType()); //url to profile image
        } else {
            return rootUrl + "img/" + profileImageEntry.getRepositoryId() + "/MIDDLE"; //url to default gender image
        }
    }
}
