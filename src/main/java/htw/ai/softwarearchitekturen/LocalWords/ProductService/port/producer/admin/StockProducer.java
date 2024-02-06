package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto.StockDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StockProducer implements IStockProducer{
    private final RabbitTemplate template;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.stockRoutingkey}")
    private String routingkey;

public StockProducer(RabbitTemplate template) {
        this.template = template;
    }
    @Override
    public void send(StockDTO dto) {
        template.convertAndSend(exchange,routingkey, dto);
    }
}
