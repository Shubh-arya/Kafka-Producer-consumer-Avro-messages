package com.sarya.kafka.sample.service.consumer;

import com.sarya.graphql.service.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductConsumer {

  @KafkaListener(topics = {"create-product"}, groupId = "sample-service-group")
  public void consumeProducts(ProductCreatedEvent event) {
    log.info("message: {}", event);
  }
}
