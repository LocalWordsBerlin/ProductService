package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ProductStockConsumer {
/**
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductStockConsumer.class);

    @RabbitListener(queues = "warehouseQueue")
    public void consume(WarehouseDTO dto) {
        LOGGER.info(String.format("Received message -> %s ", message));
        productService.addStock(dto.getIsbn(),dto.getQuantity());
    }
**/
}
