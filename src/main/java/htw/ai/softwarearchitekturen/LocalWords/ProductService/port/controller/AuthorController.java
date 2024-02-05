package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.controller;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.AuthorNotFoundException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.CreationException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl.AuthorService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(@Autowired AuthorService authorService){
            this.authorService = authorService;
    }

    @RolesAllowed({"admin"})
    @PostMapping("/author")
    public Author create(@RequestBody Author author){
        if (authorService.getAuthorByFirstNameAndLastName(author.getFirstName(), author.getLastName()) !=null)
            throw new CreationException("Author already exists");
        try {
            return authorService.create(author);
        } catch (Exception e) {
            throw new CreationException("AuthorService could not be created");
        }
    }

    @Secured("permitAll")
    @GetMapping("/authors")
    public Iterable<Author> getAuthors(){
        return authorService.getAllAuthors();
    }

    @Secured("permitAll")
    @GetMapping("/author/{id}")
    public Author getAuthor(@PathVariable UUID id){
        try {
            Author author = authorService.getAuthor(id);
            if (author == null) throw new AuthorNotFoundException(id);
            return author;
        } catch (Exception e) {
            throw new AuthorNotFoundException(id);
        }
    }

    @Secured("permitAll")
    @GetMapping("/author/name/{name}")
    public Author getAuthorByName(@PathVariable String name){
        Author author = authorService.getAuthorByName(name);
        if (author == null) throw new AuthorNotFoundException(name);
        return author;
    }
    @Secured("permitAll")
    @GetMapping("/author/{id}/products")
    public Iterable<Product> getProducts(@PathVariable UUID id){
        return authorService.getProducts(id);
    }

    @RolesAllowed({"admin"})
    @DeleteMapping("/author/{id}")
    public void delete(@PathVariable UUID id){
        if (!authorService.delete(id)) throw new AuthorNotFoundException(id);
    }

    @RolesAllowed({"admin"})
    @PutMapping("/author")
    public Author update(@RequestBody Author author){
        Author updatedAuthor = authorService.update(author);
        if (updatedAuthor == null) throw new AuthorNotFoundException(author.getId());
        return authorService.update(author);
    }




}

