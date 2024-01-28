package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.AuthorNotFoundException;
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
            throw new AuthorNotFoundException(id);
        return author;
    }

    @Override
    public Iterable<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author update(Author author) throws AuthorNotFoundException {
        return authorRepository.save(author);
    }

    @Override
    public void delete(UUID id) throws AuthorNotFoundException{
        authorRepository.deleteById(id);
    }

    public Iterable<UUID> addProduct(UUID authorId, UUID productId){
        Author author = getAuthor(authorId);
        Set<UUID> products = author.getProducts();
        products.add(productId);
        author.setProducts(products);
        return update(author).getProducts();
    }
    @Override
    public Iterable<Product> getProducts(UUID authorId) throws AuthorNotFoundException{
        return getAuthor(authorId).getProducts();
    }

    @Override
    public Author getAuthorByName(String name) throws AuthorNotFoundException{
        Author author = authorRepository.findByFirstNameOrLastName(name, name);
        if(author == null){
            throw new AuthorNotFoundException(name);
        }
        return author;
    }

    @Override
    public Iterable<Author> getAuthorsByDistrict(String district) throws AuthorNotFoundException{
        return authorRepository.findByDistrict(district);
    }

    @Override
    public Iterable<Author> getAuthorsByPlz(String plz) throws AuthorNotFoundException{
        return authorRepository.findByPlz(plz);
    }


>>>>>>> Stashed changes
}
