package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;

public interface IProductService {
    void createProduct (Product product);

    void updateProduct (Product product);

    void deleteProduct (Product product);

    Product getProduct(int id);

    Iterable<Product> getAllProducts();
}
