package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.ProductNotFoundException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin.IProductProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductRepository;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final IProductProducer productProducer;

    ProductService(IProductRepository productRepository, IProductProducer productProducer){
        this.productRepository = productRepository;
        this.productProducer = productProducer;
    }
    @Override
    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        productProducer.sendMessage(product.toString());
        //return ProductAlreadyExistsException
        return savedProduct;
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);

    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProduct(UUID id) {
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

    @Override
    public void addStock(UUID id, int quantity) {
        Optional<Product> result = productRepository.findById(id);
        Product product = null;
        if(result.isPresent()){
            product = result.get();
            product.setStock(product.getStock()+quantity);
        }
        else {
            throw new ProductNotFoundException(id);
        }
    }

    @Override
    public int getStock(UUID id) {
        Optional<Product> result= productRepository.findById(id);
        Product product = null;
        if(result.isPresent()){
            product = result.get();
        }
        return product.getStock();
    }
}
