package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto;

import java.util.UUID;

public class StockDTO {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    private int stock;

    public StockDTO(UUID id, int stock, double price) {
        this.id = id;
        this.stock = stock;
    }

    public StockDTO() {
    }
}
