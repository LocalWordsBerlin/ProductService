package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.cart;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto.AddToCartDTO;

public interface IAddToCartProducer {
    void send(AddToCartDTO dto);
}
