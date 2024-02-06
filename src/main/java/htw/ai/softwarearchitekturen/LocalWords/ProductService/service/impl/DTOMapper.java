package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto.AddToCartDTO;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto.ProductDTO;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto.StockDTO;
import org.springframework.stereotype.Service;

@Service
public class DTOMapper {

    public DTOMapper() {
    }

    public ProductDTO mapToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        return dto;
    }

    public AddToCartDTO mapToCartDTO(Product product, int quantity) {
        AddToCartDTO dto = new AddToCartDTO();
        dto.setId(product.getId());
        dto.setStock(product.getStock());
        dto.setQuantity(quantity);
        return dto;
    }

    public StockDTO mapToStockDTO(Product product) {
        StockDTO dto = new StockDTO();
        dto.setId(product.getId());
        dto.setStock(product.getStock());
        return dto;
    }



}
