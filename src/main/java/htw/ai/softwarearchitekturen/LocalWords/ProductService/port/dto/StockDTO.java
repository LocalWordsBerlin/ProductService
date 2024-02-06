package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto;

import java.util.UUID;

public class StockDTO {
    private UUID id;
    private int stock;

    public StockDTO(UUID id, int stock, double price) {
        this.id = id;
        this.stock = stock;
    }
}
