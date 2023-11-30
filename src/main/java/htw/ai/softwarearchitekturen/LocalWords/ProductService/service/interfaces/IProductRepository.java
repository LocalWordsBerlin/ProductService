package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface IProductRepository extends CrudRepository<Product, Integer> {
}
