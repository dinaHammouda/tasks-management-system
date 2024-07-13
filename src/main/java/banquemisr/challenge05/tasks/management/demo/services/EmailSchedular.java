package banquemisr.challenge05.tasks.management.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailSchedular {
 @Autowired
    private TaskService taskService;
    //@Scheduled(cron = "0 * * * * *") // Run every day at midnight
    @Scheduled(fixedRate = 60000)  // for testing every min
    public void scheduleTask() {
        taskService.processTasksDueForTodayAndSendEmails();
    }
}
