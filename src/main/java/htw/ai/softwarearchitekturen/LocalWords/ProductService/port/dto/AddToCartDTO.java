package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto;

import java.util.UUID;

public class AddToCartDTO {
    private UUID id;
    private int stock;

    private int quantity;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public AddToCartDTO() {
            this.id = id;
            this.stock = stock;
            this.quantity = quantity;
        }
}
