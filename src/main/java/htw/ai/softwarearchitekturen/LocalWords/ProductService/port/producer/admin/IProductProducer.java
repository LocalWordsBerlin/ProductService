package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto.ProductDTO;

public interface IProductProducer {
    void sendMessage(ProductDTO dto);
}
