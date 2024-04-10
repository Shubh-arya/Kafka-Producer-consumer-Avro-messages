package com.sarya.kafka.sample.service.config;

import com.sarya.graphql.service.ProductCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
@EnableKafka
@AllArgsConstructor
@Slf4j
public class KafkaConsumerConfig {

  @Bean
  @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
  ConcurrentKafkaListenerContainerFactory<String, ProductCreatedEvent> kafkaListenerContainerFactory(
      KafkaProperties kafkaProperties
  ) {
    var consumerFactory = new DefaultKafkaConsumerFactory<String, ProductCreatedEvent>(
        kafkaProperties.buildConsumerProperties(new DefaultSslBundleRegistry())
    );
    ConcurrentKafkaListenerContainerFactory<String, ProductCreatedEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    factory.setConcurrency(3);
    // factory.setCommonErrorHandler(errorHandler());
    return factory;
  }

  // @Bean
  // @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
  // ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
  // ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
  // ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory
  // ) {
  // ConcurrentKafkaListenerContainerFactory<Object, Object> factory =
  // new ConcurrentKafkaListenerContainerFactory<>();
  // configurer.configure(
  // factory,
  // kafkaConsumerFactory.getIfAvailable(
  // () -> new DefaultKafkaConsumerFactory<>(this.kafkaProperties.buildConsumerProperties())
  // )
  // );
  // factory.setConcurrency(3);
  // // factory.setCommonErrorHandler(errorHandler());
  // return factory;
  // }
}
