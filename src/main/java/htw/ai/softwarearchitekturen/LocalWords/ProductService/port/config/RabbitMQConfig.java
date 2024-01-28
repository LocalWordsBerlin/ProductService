package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RabbitMQConfig {
    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange("exchange");
    }

    @Bean
    public Queue updateQueue() {
        return new Queue("updateQueue");
    }

    @Bean
    public Queue userQueue() {
        return new Queue("userQueue");
    }

    @Bean
    public Queue addToCartQueue() {
        return new Queue("addToCartQueue");
    }

    @Bean
    public Binding updateBinding(FanoutExchange exchange, Queue updateQueue) {
        return BindingBuilder
                .bind(updateQueue)
                .to(exchange);
    }

    @Bean
    public Binding cartBinding(FanoutExchange exchange, Queue addToCartQueue) {
        return BindingBuilder
                .bind(addToCartQueue)
                .to(exchange);
    }

    @Bean
    CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("rabbitmq-container", 5672);
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }


}

