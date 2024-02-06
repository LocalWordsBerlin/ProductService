package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.cart;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto.AddToCartDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AddToCartProducer implements IAddToCartProducer{
    private final RabbitTemplate template;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.addToCartRoutingkey}")
    private String routingkey;

    @Autowired
    public AddToCartProducer(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public void send(AddToCartDTO dto) {
        template.convertAndSend(exchange,routingkey, dto);
    }
}
