package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.controller;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl.AuthorService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @RolesAllowed({"admin"})
    @PostMapping("/author")
    public Author create(@RequestBody Author author){
        return authorService.create(author);
    }

    @PermitAll
    @GetMapping("/authors")
    public Iterable<Author> getAuthors(){
        return authorService.getAllAuthors();
    }

    @PermitAll
    @GetMapping("/author/{id}")
    public Author getAuthor(@PathVariable UUID id){
        return authorService.getAuthor(id);
    }

    @PermitAll
    @GetMapping("/author/name/{name}")
    public Author getAuthorByName(@PathVariable String name){
        return authorService.getAuthorByName(name);
    }
    @PermitAll
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

