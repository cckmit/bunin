package my.bunin.reconciliation;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Configuration
@ImportResource("classpath:batch.xml")
public class BatchConfig {

//    @Resource
//    private DataSource dataSource;
//
//    @Bean
//    public JobRepository jobRepository(PlatformTransactionManager transactionManager) throws Exception {
//        return new JobRepositoryFactoryBean() {{
//            setDataSource(dataSource);
//            setTransactionManager(transactionManager);
//            setDatabaseType("mysql");
//        }}.getObject();
//    }
//
//    @Bean
//    public JobLauncher jobLauncher(JobRepository jobRepository){
//        return new SimpleJobLauncher() {{
//            setJobRepository(jobRepository);
//        }};
//    }
//
//
//    @Bean
//    public Step step1(StepBuilderFactory factory,
//                      ItemReader<ListResult> reader,
//                      ItemProcessor<ListResult, ListResult> processor,
//                      ItemWriter<ListResult> writer) {
//        return factory
//                .get("step1")
//                .<ListResult, ListResult>chunk(100000)
//                .reader(reader)
//                .processor(processor)
//                .writer(writer)
//                .faultTolerant()
//                .taskExecutor(new SimpleAsyncTaskExecutor())
//                .throttleLimit(20)
//                .build();
//    }
//
//    @Bean
//    public ItemReader<ListResult> reader() {
//        // FlatFileItemReader is not thread-safe
//        FlatFileItemReader<ListResult> reader = new FlatFileItemReader<ListResult>() {
////            private final Logger log = LoggerFactory.getLogger(FlatFileItemReader.class);
//            @Override
//            public ListResult read() throws Exception {
////                log.info("reading");
//                return super.read();
//            }
//        };
//        reader.setResource(new FileSystemResource("D:\\tmp\\spark\\result20180411202630528\\part-00000"));
//        reader.setLineMapper(new DefaultLineMapper<ListResult>() {{
//            setLineTokenizer(new DelimitedLineTokenizer() {{
//                setNames(new String[] {"result", "orderNo","originStatus","targetStatus"});
//            }});
//            setFieldSetMapper(new BeanWrapperFieldSetMapper<ListResult>() {{
//                setTargetType(ListResult.class);
//            }});
//        }});
//
//        return reader;
//    }
//
//    @Bean
//    public ItemProcessor<ListResult, ListResult> processor() {
//        ValidatingItemProcessor<ListResult> processor = new ValidatingItemProcessor<ListResult>() {
////            private final Logger log = LoggerFactory.getLogger(ValidatingItemProcessor.class);
//            @Override
//            public ListResult process(ListResult item) throws ValidationException {
////                log.info("process item: {}", item);
//                return super.process(item);
//            }
//        };
//
//        processor.setValidator(value -> {
//            // empty
//        });
//
//        return processor;
//    }
//
//    @Bean
//    public ItemWriter<ListResult> writer() {
//        JdbcBatchItemWriter<ListResult> writer = new JdbcBatchItemWriter<ListResult>() {
//            private final Logger log = LoggerFactory.getLogger(JdbcBatchItemWriter.class);
//            @Override
//            public void write(List<? extends ListResult> items) throws Exception {
//                log.info("writing {}", items.size());
//                super.write(items);
//            }
//        };
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
//        writer.setDataSource(dataSource);
//        writer.setSql("insert into list_result (order_no, origin_status, target_status, result)"
//                + "values( :orderNo, :originStatus, :targetStatus, :result)" );
//
//        return writer;
//    }
//
//    @Bean
//    public Job job(JobBuilderFactory factory, @Qualifier("step1") Step step, JobExecutionListener listener) {
//        return factory
//                .get("listResultJob")
//                .incrementer(new RunIdIncrementer())
//                .flow(step)
//                .end()
//                .listener(listener)
//                .build();
//    }
//
//    @Bean
//    public JobExecutionListener listener() {
//        return new JobExecutionListenerSupport() {
//            private final Logger log = LoggerFactory.getLogger(JobExecutionListenerSupport.class);
//
//            private StopWatch watch = new StopWatch();
//
//            @Override
//            public void afterJob(JobExecution jobExecution) {
//                watch.stop();
//                log.info(watch.prettyPrint());
//            }
//
//            @Override
//            public void beforeJob(JobExecution jobExecution) {
//
//                watch.start(jobExecution.getJobInstance().getJobName() + ":" + jobExecution.getJobId());
//            }
//        };
//    }
//
//
//    //
//    @Bean
//    public MultiResourcePartitioner partitioner() throws IOException {
//        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
//        partitioner.setResources(new PathMatchingResourcePatternResolver()
//                .getResources("file:d:/tmp/bigspark/bf-*"));
//
//        return partitioner;
//    }
//
//    @Bean
//    public ThreadPoolTaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(8);
//        taskExecutor.setMaxPoolSize(20);
//        return taskExecutor;
//    }
//
//
//    @Bean
//    public Step multiStep(StepBuilderFactory factory,
//                          Partitioner partitioner,
//                          TaskExecutor taskExecutor) {
//        return factory
//                .get("step2")
//                .partitioner("singleStep", partitioner)
//                .gridSize(8)
//                .taskExecutor(taskExecutor)
//                .build();
//    }
//
//    @Bean
//    public ItemReader<ListResult> multiReaderParent() {
//
//        FlatFileItemReader<ListResult> reader = new FlatFileItemReader<>();
//        reader.setResource(new FileSystemResource("#{stepExecutionContext[fileName]}"));
//
//
//        // FlatFileItemReader is not thread-safe
//        FlatFileItemReader<ListResult> reader = new FlatFileItemReader<ListResult>() {
//            //            private final Logger log = LoggerFactory.getLogger(FlatFileItemReader.class);
//            @Override
//            public ListResult read() throws Exception {
////                log.info("reading");
//                return super.read();
//            }
//        };
//        reader.setResource(new FileSystemResource("D:\\tmp\\spark\\result20180411202630528\\part-00000"));
//        reader.setLineMapper(new DefaultLineMapper<ListResult>() {{
//            setLineTokenizer(new DelimitedLineTokenizer() {{
//                setNames(new String[] {"result", "orderNo","originStatus","targetStatus"});
//            }});
//            setFieldSetMapper(new BeanWrapperFieldSetMapper<ListResult>() {{
//                setTargetType(ListResult.class);
//            }});
//        }});
//
//        return reader;
//    }



}
