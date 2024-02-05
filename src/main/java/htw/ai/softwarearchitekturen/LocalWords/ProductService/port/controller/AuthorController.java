package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.controller;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.CreationException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl.AuthorService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(@Autowired AuthorService authorService){
            this.authorService = authorService;
    }

    @RolesAllowed({"admin"})
    @PostMapping("/author")
    public Author create(@RequestBody Author author){
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

        return authorService.getAuthor(id);
    }

    @Secured("permitAll")
    @GetMapping("/author/name/{name}")
    public Author getAuthorByName(@PathVariable String name){
        return authorService.getAuthorByName(name);
    }
    @Secured("permitAll")
    @GetMapping("/author/{id}/products")
    public Iterable<Product> getProducts(@PathVariable UUID id){
        return authorService.getAuthor(id).getProducts();
    }

    @RolesAllowed({"admin"})
    @DeleteMapping("/author/{id}")
    public void delete(@PathVariable UUID id){
        authorService.delete(id);
    }

    @RolesAllowed({"admin"})
    @PutMapping("/author")
    public Author update(@RequestBody Author author){
        return authorService.update(author);
    }




}

