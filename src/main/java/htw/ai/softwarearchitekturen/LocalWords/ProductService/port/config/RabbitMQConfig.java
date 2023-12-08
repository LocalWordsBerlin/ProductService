package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.updateQueue.name}")
    private String updateQueue;

    @Value("${rabbitmq.addToCartQueue.name}")
    private String addToCartQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.updateKey}")
    private String updateRoutingKey;

    @Value("${rabbitmq.routing.addToCartKey}")
    private String addToCartRoutingKey;

    @Bean
    public Queue updateQueue(){
        return new Queue(updateQueue);
    }

    @Bean
    public Queue addToCartQueue(){
        return new Queue(addToCartQueue);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(updateQueue())
                .to(exchange())
                .with(updateRoutingKey);
    }

    @Bean
    public Binding itemBinding(){
        return BindingBuilder
                .bind(addToCartQueue())
                .to(exchange())
                .with(addToCartRoutingKey);
    }

}
