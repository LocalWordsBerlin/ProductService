package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ProductStockConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductStockConsumer.class);

    @RabbitListener(queues = "${rabbitmq.stockQueue.name}")
    public void consume(String message) {
        LOGGER.info(String.format("Received message -> %s ", message));
        //add Logic to interpret message and add or remove STOCK of Product
    }
}
