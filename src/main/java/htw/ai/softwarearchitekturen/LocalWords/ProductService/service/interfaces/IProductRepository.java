package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IProductRepository extends CrudRepository<Product, UUID> {
    Product findByIsbn(String isbn);
}
