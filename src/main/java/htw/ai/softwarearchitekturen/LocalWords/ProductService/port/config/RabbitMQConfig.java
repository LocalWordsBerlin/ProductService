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
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.updateRoutingkey}")
    private String updateRoutingkey;

    @Value("${spring.rabbitmq.addToCartRoutingkey}")
    private String addTocartRoutingkey;

    @Value("${spring.rabbitmq.stockRoutingkey}")
    private String stockRoutingkey;

    @Value("${spring.rabbitmq.updateQueue}")
    private String updateQueue;

    @Value("${spring.rabbitmq.addToCartQueue}")
    private String addToCartQueue;

    @Value("${spring.rabbitmq.stockQueue}")
    private String stockQueue;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue stockQueue() {
        return new Queue(stockQueue);
    }

    @Bean
    public Queue updateQueue() {
        return new Queue(updateQueue);
    }

    @Bean
    public Queue addToCartQueue() {
        return new Queue(addToCartQueue);
    }

    @Bean
    public Binding updateBinding(TopicExchange exchange, Queue updateQueue) {
        return BindingBuilder
                .bind(updateQueue)
                .to(exchange)
                .with(updateRoutingkey);
    }

    @Bean
    public Binding cartBinding(TopicExchange exchange, Queue addToCartQueue) {
        return BindingBuilder
                .bind(addToCartQueue)
                .to(exchange)
                .with(addTocartRoutingkey);
    }

    @Bean
    public Binding stockBinding(TopicExchange exchange, Queue stockQueue) {
        return BindingBuilder
                .bind(stockQueue)
                .to(exchange)
                .with(stockRoutingkey);
    }

    @Bean
    CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host, 5672);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
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

