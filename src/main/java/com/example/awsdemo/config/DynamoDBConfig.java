package com.example.awsdemo.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * Configuration for DynamoDB
 * @author devyanisharma
 */
@Configuration
@EnableDynamoDBRepositories(basePackages = "com.example.awsdemo.repository")
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    @Value("${amazon.aws.region}")
    private String amazonSigninRegion;

    @Autowired
    private ApplicationContext context;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
      if (StringUtils.isEmpty(amazonDynamoDBEndpoint)){
        return null;
      }
      return AmazonDynamoDBClientBuilder.standard()
          .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint,
              amazonSigninRegion))
          .build();
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
      return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }
}
