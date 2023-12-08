package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.cart.AddToCartProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

//@PropertySource("src/main/resources/application.properties")
@Service
public class UpdateProductProductProducer implements IProductProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.updateKey}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(AddToCartProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public UpdateProductProductProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
