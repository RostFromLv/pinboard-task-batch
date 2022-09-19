package com.example.taskservice.configs;

import com.example.taskservice.batch.BatchWriter;
import com.example.taskservice.batch.Processor;
import com.example.taskservice.domen.Task;
import java.util.Properties;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableBatchProcessing
public class Configs {

  @Value("${spring.mail.host}")
  private String host;
  @Value("${spring.mail.port}")
  private Integer port;
  @Value("${spring.mail.username}")
  private String username;
  @Value("${spring.mail.password}")
  private String password;
  @Value("${spring.mail.protocol}")
  private String protocol;
  @Value("${spring.mail.properties.mail.smtp.auth}")
  private String auth;
  @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
  private String starttls;
  @Value("${input}")
  private String fileInput;

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final BatchWriter batchWriter;
  private final Processor processor;

  @Autowired
  public Configs(
      JobBuilderFactory jobBuilderFactory,
      StepBuilderFactory stepBuilderFactory, BatchWriter batchWriter,
      Processor processor) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
    this.batchWriter = batchWriter;
    this.processor = processor;
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public JavaMailSender javaMailSender() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

    javaMailSender.setHost(host);
    javaMailSender.setPort(port);
    javaMailSender.setPassword(password);
    javaMailSender.setUsername(username);
    javaMailSender.setProtocol(protocol);

    Properties properties = javaMailSender.getJavaMailProperties();
    properties.setProperty("mail.transport.protocol", protocol);
    properties.put("mail.smtp.auth", auth);
    properties.put("mail.smtp.starttls.enable", starttls);
    properties.put("mail.debug", "true");

    javaMailSender.setJavaMailProperties(properties);

    return javaMailSender;
  }


  @Bean
  public FlatFileItemReader<Task> reader() {
    FlatFileItemReader flatFileItemReader = new FlatFileItemReaderBuilder<Task>()
        .name("taskItemReader")
        .resource(new ClassPathResource(fileInput))
        .delimited()
        .names("id", "title", "text", "status", "userId", "notificationText")
        .fieldSetMapper(new BeanWrapperFieldSetMapper() {{
          setTargetType(Task.class);
        }})
        .build();
    flatFileItemReader.setLinesToSkip(1);
    return flatFileItemReader;
  }

  @Bean
  public Job job(){
    return jobBuilderFactory.get("importTaskJob")
        .incrementer(new RunIdIncrementer())
        .start(step1())
        .build();
  }
  @Bean
  public Step step1(){
    return stepBuilderFactory.get("step1")
        .<Task,Task> chunk(10)
        .reader(reader())
        .processor(processor)
        .writer(batchWriter)
        .build();
  }


}
