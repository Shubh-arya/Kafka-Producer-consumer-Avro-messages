package com.sarya.kafka.sample.service.producer;

import com.sarya.graphql.service.ProductCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class CreateProductProducer {

  private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

  public boolean sendProductCreatedMessage(ProductCreatedEvent event) {
    var result = kafkaTemplate.send((new GenericMessage<>(event)));
    return result.isDone();
  }
}
