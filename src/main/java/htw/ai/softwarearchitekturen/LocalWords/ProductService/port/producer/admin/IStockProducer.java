package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto.StockDTO;

public interface IStockProducer {
    void send(StockDTO dto);
}
