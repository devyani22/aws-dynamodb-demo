package com.example.awsdemo.repository;

import com.example.awsdemo.model.RateInfo;
import java.util.Optional;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface RateInfoRepository extends CrudRepository<RateInfo, String> {

  Optional<RateInfo> findById(String id);

}
