package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.AuthorNotFoundException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IAuthorRepository;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IAuthorService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthorService implements IAuthorService {

    IAuthorRepository authorRepository;

    public AuthorService(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author create(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author getAuthor(UUID id) {
        Optional<Author> result = authorRepository.findById(id);
        Author author = null;
        if(result.isPresent()){
            author = result.get();
        }
        else {
            throw new AuthorNotFoundException(id);
        }
        return author;
    }

    @Override
    public Iterable<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author update(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void delete(UUID id) {
        authorRepository.deleteById(id);
    }

    public Iterable<UUID> getProducts(UUID authorId){
        return getAuthor(authorId).getProducts();
    }

    public Iterable<UUID> addProduct(UUID authorId, UUID productId){
        Author author = getAuthor(authorId);
        Set<UUID> products = author.getProducts();
        products.add(productId);
        author.setProducts(products);
        return update(author).getProducts();
    }
}
