package com.example.jpa.web.quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.example.jpa.web.quartz.jobs.ScheduleJob;


public class TestQuartz extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER;


	static {

		LOGGER = Logger.getLogger(TestQuartz.class);
	}

	public TestQuartz() {
		super();
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		LOGGER.info("Inside init - Initializing quartz scheduler ..");

		try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();

			// Trigger the job to run on the next round minute
			Trigger trigger = newTrigger()
					.withIdentity("trigger1", "group1")
					.startAt(futureDate(2, IntervalUnit.MINUTE))
					.build();

			// Define job instance
			JobDetail job1 = newJob(ScheduleJob.class)
					.withIdentity("job1", "group1")
					.build();

			// Schedule the job with the trigger 
			scheduler.scheduleJob(job1, trigger); 

			
			//Job to send Careplan Survey Form Reminders
			String cronExpressionForSurveyFormRminder = "0/30 * * * * ?";
			LOGGER.info("cronExpressionForSurveyFormRminder  " + cronExpressionForSurveyFormRminder);
			
			if(!cronExpressionForSurveyFormRminder.isEmpty()) {
				
				LOGGER.info("cronExpressionForSurveyFormRminder is not empty ");
				JobDetail jobDetail = newJob(ScheduleJob.class)
						.withIdentity("CPN_SURVEY_FORM_REMINDER", StdScheduler.DEFAULT_GROUP)
						.storeDurably()
						.requestRecovery()
						.build();

				if(!scheduler.checkExists(jobDetail.getKey())) {

					LOGGER.info(jobDetail.getKey() + " not exists");
					Trigger ct = newTrigger()
							.withIdentity("CPN_SURVEY_FORM_REMINDER")
							.startAt(futureDate(30, IntervalUnit.SECOND))
							.forJob(jobDetail)
							.withSchedule(cronSchedule(cronExpressionForSurveyFormRminder))
							.build();
					
					LOGGER.info("DATE : " + scheduler.scheduleJob(jobDetail, ct));
				}
			}
			
			//Start scheduler
			scheduler.start();

		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}


}
