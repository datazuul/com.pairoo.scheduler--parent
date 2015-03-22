/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pairoo.frontend.scheduler.application.business;

import java.util.Date;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author klaus
 */
public class EchoJob implements Job {

    private final Logger logger = LoggerFactory.getLogger(EchoJob.class);
    private String kmsg = "Inject did not work! (it should overwrite this message) ";

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        logger.info(kmsg);
    }

    public void setKmsg(String kmsg) {
        this.kmsg = kmsg;
    }
}
