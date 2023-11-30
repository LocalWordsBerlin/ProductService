package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductRepository;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductService;

import java.util.Optional;

public class ProductService implements IProductService {
    private final IProductRepository productRepository;

    ProductService(IProductRepository productRepository){

        this.productRepository = productRepository;
    }
    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);

    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Product getProduct(int id) {
        Optional<Product> result = productRepository.findById(id);
        Product product = null;
        if(result.isPresent()){
            product = result.get();
        }
        else {
            throw new RuntimeException("Product was not found");
        }
        return product;
    }

    @Override
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
