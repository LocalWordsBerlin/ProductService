package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.AuthorNotFoundException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.ProductAlreadyExistsException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IAuthorRepository;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IAuthorService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthorService implements IAuthorService {

    private final IAuthorRepository authorRepository;
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
        Author author;
        if(result.isPresent())
            author = result.get();
        else
            return null;
        return author;
    }

    @Override
    public Iterable<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author update(Author author) {
        if (!authorRepository.existsById(author.getId())) return null;
        return authorRepository.save(author);
    }

    @Override
    public Boolean delete(UUID id) {
        if (!authorRepository.existsById(id)) return false;
        authorRepository.deleteById(id);
        return true;
    }

    @Override
    public Boolean addProduct(UUID authorId, Product product){
        Author author = getAuthor(authorId);
        Set<Product> products = author.getProducts();
        if (products.contains(product)) return false;
        products.add(product);
        author.setProducts(products);
        update(author);
        return true;
    }
    @Override
    public Set<Product> getProducts(UUID authorId) {
        if (!authorRepository.existsById(authorId)) throw new AuthorNotFoundException(authorId);
        return getAuthor(authorId).getProducts();
    }

    @Override
    public Author getAuthorByName(String name) {
        return authorRepository.findByFirstNameOrLastName(name, name);
    }

    @Override
    public Author getAuthorByFirstNameAndLastName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Iterable<Author> getAuthorsByDistrict(String district) {
        return authorRepository.findByDistrict(district);
    }

    @Override
    public Iterable<Author> getAuthorsByPlz(String plz) {
        return authorRepository.findByPlz(plz);
    }




}
