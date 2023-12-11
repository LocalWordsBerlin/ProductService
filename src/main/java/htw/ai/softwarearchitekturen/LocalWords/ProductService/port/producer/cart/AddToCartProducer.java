package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.cart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
public class AddToCartProducer{
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.addToCartKey}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(AddToCartProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public AddToCartProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        LOGGER.info(String.format("Message sent -> %s", message));
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
