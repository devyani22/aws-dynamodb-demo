package com.example.awsdemo.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName = "Rate")
public class RateInfo {

  private String itemId;

  private String itemName;

  private String cost;

  @DynamoDBHashKey
  @DynamoDBAutoGeneratedKey
  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  @DynamoDBAttribute
  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  @DynamoDBAttribute
  public String getCost() {
    return cost;
  }

  public void setCost(String cost) {
    this.cost = cost;
  }
}
