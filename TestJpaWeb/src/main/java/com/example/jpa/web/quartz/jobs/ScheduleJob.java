package com.example.jpa.web.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ScheduleJob implements Job {

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		
		
		JobDetail detail  = jobExecutionContext.getJobDetail();
		System.out.println(detail.getKey().getName() + " triggered successfully ...");

	}

}
