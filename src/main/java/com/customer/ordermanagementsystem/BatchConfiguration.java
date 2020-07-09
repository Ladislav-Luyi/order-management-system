package com.customer.ordermanagementsystem;

import com.customer.ordermanagementsystem.orders.Item;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;



import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Item> reader() {
        return new FlatFileItemReaderBuilder<Item>()
                .name("itemItemReader")
                .resource(new ClassPathResource("source.csv"))
                .delimited()
                .names(new String[]{"id","name", "type", "price"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Item>() {{
                    setTargetType(Item.class);
                }})
                .build();
    }

    @Bean
    public ItemItemProcessor processor() {
        return new ItemItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Item> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Item>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO Items (id, name, type, price) VALUES (:id, :name, :type, :price)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
//                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Item> writer) {
        return stepBuilderFactory.get("step1")
                .<Item, Item> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}

