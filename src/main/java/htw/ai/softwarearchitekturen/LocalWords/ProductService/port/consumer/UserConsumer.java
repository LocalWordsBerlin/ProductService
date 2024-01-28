package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductStockConsumer.class);

    @RabbitListener(queues = "updateQueue")
    public void consume(String message) throws InterruptedException{
        LOGGER.info(String.format("Received message -> %s ", message));
        System.out.println("UserConsumer: " + message);
    }
}
