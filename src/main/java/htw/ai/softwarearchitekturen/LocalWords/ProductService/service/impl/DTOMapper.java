package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto.AddToCartDTO;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto.ProductDTO;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto.StockDTO;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IAuthorService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DTOMapper {
    private final IProductService productService;

    public DTOMapper(IProductService productService, IAuthorService authorService) {
        this.productService = productService;
    }

    public ProductDTO mapToDTO(String isbn) {
        Product product = productService.getProductByIsbn(isbn);
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        return dto;
    }

    public AddToCartDTO mapToCartDTO(UUID id, int quantity) {
        Product product = productService.getProduct(id);
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
