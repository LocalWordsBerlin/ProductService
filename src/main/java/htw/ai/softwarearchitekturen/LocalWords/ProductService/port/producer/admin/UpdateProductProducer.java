package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto.ProductDTO;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.cart.AddToCartProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UpdateProductProducer implements IProductProducer {
    private final RabbitTemplate template;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.updateRoutingkey}")
    private String routingkey;

    @Autowired
    public UpdateProductProducer(RabbitTemplate template) {
        this.template = template;
    }
    @Override
    public void sendMessage(ProductDTO dto) {
        template.convertAndSend(exchange,routingkey, dto);
    }
}
