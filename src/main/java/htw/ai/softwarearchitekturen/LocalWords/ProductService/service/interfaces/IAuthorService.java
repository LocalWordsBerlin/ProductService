package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;

import java.util.UUID;

public interface IAuthorService {
    Author create(Author author);

    Author getAuthor(UUID id);

    Iterable<Author> getAllAuthors();

    Author update(Author author);

    void delete(UUID id);

    Iterable<Product> getProducts(UUID authorId);

    Author getAuthorByName(String name);



}
