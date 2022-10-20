package com.projeto.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
//@EnableAsync
//@ConditionalOnProperty(name="schedule.enable",matchIfMissing = true)
public class ScheduleConfig {
	
//	@Scheduled(fixedDelay = 5000) //inverval in miliseconds
	@Scheduled(cron = "0 53 11 * * *") //(second, minute, hour, day of the month, month, day of the week)
//	@Async 
	public void test() {
		System.out.println("O_O");
	}

}
