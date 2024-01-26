package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.controller;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl.AuthorService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1")
@EnableWebSecurity
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(@Autowired AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping("/author")
    @RolesAllowed({"admin"})
    public Author create(@RequestBody Author author){
        return authorService.create(author);
    }

    @GetMapping("/authors")
    public Iterable<Author> getAuthors(){
        return authorService.getAllAuthors();
    }

    @GetMapping("/author/{id}")
    public Author getAuthor(@PathVariable UUID id){
        return authorService.getAuthor(id);
    }

    @GetMapping("/author/{id}/products")
    public Iterable<UUID> getProducts(@PathVariable UUID id){
        return authorService.getAuthor(id).getProducts();
    }

    @PostMapping("/author/{id}/product")
    public Iterable<UUID> addProduct(@PathVariable UUID id, @RequestBody UUID productId){
        return authorService.addProduct(id, productId);
    }

    @DeleteMapping("/author/{id}")
    public void delete(@PathVariable UUID id){
        authorService.delete(id);
    }

    @PutMapping("/author")
    public Author update(@RequestBody Author author){
        return authorService.update(author);
    }




}

