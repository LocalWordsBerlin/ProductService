package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public interface IProductService {
    Product createProduct (Product product);

    Product updateProduct (Product product);

    void deleteProduct (UUID id);

    Product getProduct(UUID id);

    Iterable<Product> getAllProducts();

    void addStock(String isbn, int quantity);

    int getStock(UUID id);

    void addAuthor(UUID id, Author author);

    Set<Author> getAuthors(UUID id);

    Product getProductByIsbn(String isbn);

    Iterable<Product> getProductsByGenre(String genre);

    Iterable<Product> getProductsByTitle(String title);



}
