package com.pairoo.frontend.scheduler.application;

import org.quartz.impl.StdScheduler;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        String environment = System.getProperty("env");
        if ("PROD".equals(environment)) {
            environment = "PROD";

            String cronExpression = System.getProperty("cronExpression");
            if (cronExpression == null) {
            // every 5 minutes (starting at full hour (0))
                // cronExpression = "0 0/5 * * * *";

                // every Friday at 13 o'clock
                cronExpression = "0 0 13 ? * FRI";
                System.setProperty("cronExpression", cronExpression);
            }
            LOGGER.info("setting cron expression: '" + cronExpression + "'");
        }
        LOGGER.info("setting environment: '" + environment + "'");

        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/com/pairoo/frontend/scheduler/application/springBeans.xml");
//        if (args != null && args.length > 0 && args[0] != null) {
//            CronTriggerImpl trigger = (CronTriggerImpl) appContext.getBean("partnerSuggestionTrigger");
//            StdScheduler scheduler = (StdScheduler) appContext.getBean("schedulerFactoryBean");
//            if (trigger != null && scheduler != null) {
//                trigger.setCronExpression(args[0]);
//                scheduler.rescheduleJob(trigger.getKey(), trigger);
//            }
//        }
        CronTriggerImpl trigger = (CronTriggerImpl) appContext.getBean("partnerSuggestionTrigger");
        String cronExpression = trigger.getCronExpression();
        if (cronExpression != null) {
            LOGGER.info("trigger using cron expression: '" + cronExpression + "'");
        }
    }
}
