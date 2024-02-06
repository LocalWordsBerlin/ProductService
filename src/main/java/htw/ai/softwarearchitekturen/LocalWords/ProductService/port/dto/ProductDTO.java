package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto;

import java.util.UUID;

public class ProductDTO {
    private UUID id;
    private String title;
    private double price;
    private int stock;

    public ProductDTO() {
    }

    public ProductDTO(UUID id, String name, double price, int stock) {
        this.id = id;
        this.title = name;
        this.price = price;
        this.stock = stock;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
