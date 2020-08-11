package com.example.awsdemo.integ;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.example.awsdemo.model.RateInfo;
import com.example.awsdemo.repository.RateInfoRepository;
import com.example.awsdemo.rule.LocalDbCreationRule;
import java.util.List;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("local")
@TestPropertySource(locations = "/application.properties")
public class RateInfoIntegrationTest {

  @ClassRule
  public static LocalDbCreationRule dynamoDB = new LocalDbCreationRule();

  private DynamoDBMapper dynamoDBMapper;

  @Autowired
  private AmazonDynamoDB amazonDynamoDB;

  @Autowired
  RateInfoRepository repository;

  private static final String EXPECTED_COST = "20";
  private static final String EXPECTED_PRICE = "50";

  @Before
  public void setup() throws Exception {

    try {
      dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

      CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(RateInfo.class);

      tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

      amazonDynamoDB.createTable(tableRequest);
    } catch (ResourceInUseException e) {
      // Do nothing, table already created
    }

    // TODO How to handle different environments. i.e. AVOID deleting all entries in RateInfo on table
    dynamoDBMapper.batchDelete((List<RateInfo>) repository.findAll());
  }

  @Test
  public void givenItemWithExpectedCost_whenRunFindAll_thenItemIsFound() {

    RateInfo RateInfo = new RateInfo();
    repository.save(RateInfo);

    List<RateInfo> result = (List<RateInfo>) repository.findAll();
    assertThat(result.size(), is(greaterThan(0)));
    assertThat(result.get(0).getCost(), is(equalTo(EXPECTED_COST)));
  }
}