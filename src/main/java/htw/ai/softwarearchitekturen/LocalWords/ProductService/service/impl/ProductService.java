package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.ProductNotFoundException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin.IProductProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductRepository;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
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
        return savedProduct;
    }

    @Override
    public Product updateProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        productProducer.sendMessage(product.toString());
        return savedProduct;
    }

    @Override
    public void deleteProduct(UUID id) throws ProductNotFoundException{
        productRepository.deleteById(id);
    }

    @Override
    public Product getProduct(UUID id) throws ProductNotFoundException{
        Optional<Product> result = productRepository.findById(id);
        Product product = null;
        if(result.isPresent()){
            product = result.get();
            return product;
        }
        else {
            throw new ProductNotFoundException(id);
        }
    }

    @Override
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void addStock(UUID id, int quantity) throws ProductNotFoundException{
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
    public int getStock(UUID id) throws ProductNotFoundException{
        return getProduct(id).getStock();
    }

    @Override
    public void addAuthor(UUID id, Author author) throws ProductNotFoundException {
        Product product = getProduct(id);
        Set<Author> authors = product.getAuthors();
        authors.add(author);
        product.setAuthors(authors);
        updateProduct(product);
    }

    @Override
    public Set<Author> getAuthors(UUID id) {
        return getProduct(id).getAuthors();
    }

    @Override
    public Product getProductByIsbn(String isbn) throws ProductNotFoundException{
        return productRepository.findByIsbn(isbn);
    }

    @Override
    public Iterable<Product> getProductsByGenre(String genre) {
        return productRepository.findByGenre(genre);
    }

    @Override
    public Iterable<Product> getProductsByTitleOrAuthors(String search, String search2) {
        return productRepository.findByTitleOrAuthors(search, search2);
    }

    @Override
    public Iterable<Product> getProductsByTitle(String title) {
        return productRepository.findByTitle(title);
    }
}
