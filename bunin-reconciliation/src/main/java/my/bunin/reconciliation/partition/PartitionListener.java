package my.bunin.reconciliation.partition;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class PartitionListener implements JobExecutionListener {


    @Override
    public void beforeJob(JobExecution jobExecution) {
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        log.info("job {} completed with: {}",
                String.format("%s:%s", jobExecution.getJobInstance().getJobName(), jobExecution.getJobId()),
                DurationFormatUtils.formatPeriod(
                        jobExecution.getStartTime().getTime(),
                        jobExecution.getEndTime().getTime(),
                        "y 'years' M 'months' d 'days' H 'hours' m 'minutes' s 'seconds' S 'milliseconds'"));
    }
}
