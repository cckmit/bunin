package my.bunin.partition;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PartitionJobTest {

    @Resource
    private JobLauncher jobLauncher;

    @Resource
    @Qualifier("partitionJob")
    private Job partitionJob;

    @Test
    public void partitionJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        JobExecution execution = jobLauncher.run(partitionJob,
                new JobParametersBuilder()
                        .addString("input.resources", "file:///d:/tmp/spark/list1.txt")
                        .toJobParameters());

        Assert.assertEquals(execution.getExitStatus().getExitCode(), ExitStatus.COMPLETED.getExitCode());
    }

}
