package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto.ProductDTO;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.ProductNotFoundException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin.IProductProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin.IStockProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductRepository;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final IStockProducer stockProducer;

    private final DTOMapper dtoMapper;

    ProductService(IProductRepository productRepository, IStockProducer stockProducer, DTOMapper dtoMapper){
        this.productRepository = productRepository;
        this.stockProducer = stockProducer;
        this.dtoMapper = dtoMapper;
    }
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
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
        Product product;
        if(result.isPresent()){
            product = result.get();
            return product;
        }
        else {
            return null;
        }
    }

    @Override
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void addStock(String isbn, int quantity) throws ProductNotFoundException{
        Product product = productRepository.findByIsbn(isbn);
        if(product != null){
            int newStock = product.getStock() + quantity;
            product.setStock(newStock);
            stockProducer.send(dtoMapper.mapToStockDTO(product));
        }
        else {
            throw new ProductNotFoundException();
        }
    }

    @Override
    public int getStock(UUID id){
        return getProduct(id).getStock();
    }

    @Override
    public void addAuthor(UUID productId, Author author) {
        Product product = getProduct(productId);
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
    public Product getProductByIsbn(String isbn) {
        return productRepository.findByIsbn(isbn);
    }

    @Override
    public Iterable<Product> getProductsByGenre(String genre) {
        return productRepository.findByGenre(genre);
    }

    @Override
    public Iterable<Product> getProductsByTitle(String title) {
        return productRepository.findByTitle(title);
    }


}
