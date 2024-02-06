package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.controller;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.*;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.cart.IAddToCartProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl.DTOMapper;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IAuthorService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.ISearchService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class ProductController {

    private final IProductService productService;
    private final IAuthorService authorService;

    private final ISearchService searchService;
    private final IAddToCartProducer addToCartProducer;

    private final DTOMapper dtoMapper;

    @Autowired
    public ProductController(IProductService productService, IAuthorService authorService, IAddToCartProducer addToCartProducer, ISearchService searchService, DTOMapper dtoMapper) {
        this.productService = productService;
        this.authorService = authorService;
        this.searchService = searchService;
        this.addToCartProducer = addToCartProducer;
        this.dtoMapper = dtoMapper;
    }

    /*
    - checks if product with given isbn already exists
    - if not -> Repository creates product and returns it
     */
    @PostMapping(path = "/product")
    @RolesAllowed({"admin"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Product create(@RequestBody Product product) {
        Product alreadyExists = productService.getProductByIsbn(product.getIsbn());
        if (alreadyExists != null)
            throw new ProductAlreadyExistsException(product.getIsbn());
        try {
            return productService.createProduct(product);
        } catch (Exception e) {
            throw new CreationException("Product could not be created");
        }
    }

    @Secured("permitAll")
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable UUID id) {
        try {
            return productService.getProduct(id);
        } catch (Exception e) {
            throw new ProductNotFoundException(id);
        }
    }

    @RolesAllowed({"admin", "customer"})
    @PostMapping("/addToCart/{productId}")
    public void addToCart(@PathVariable UUID productId, @RequestParam int quantity) {
        if(productService.getStock(productId) >= quantity) {
            addToCartProducer.send(dtoMapper.mapToCartDTO(productId, quantity));
        } else {
            throw new OutOfStockException(quantity);
        }
    }

    @RolesAllowed({"admin"})
    @PutMapping(path = "/product")
    public @ResponseBody Product update(@RequestBody Product product) {
        try {
            productService.getProduct(product.getId());
            return productService.updateProduct(product);
        } catch (Exception e) {
            throw new ProductNotFoundException(product.getId());
        }
    }

    @RolesAllowed({"admin"})
    @DeleteMapping(path = "/product/{id}")
    public @ResponseBody String delete(@PathVariable UUID id) {
        try {
            productService.deleteProduct(id);
        } catch (Exception e) {
            throw new ProductNotFoundException(id);
        }
        return "Successfully deleted Product " + id;
    }

    @Secured("permitAll")
    @GetMapping("/products")
    public @ResponseBody Iterable<Product> getProducts() {
        return productService.getAllProducts();
    }

    @Secured("permitAll")
    @GetMapping("/productsByGenre/{genre}")
    public @ResponseBody Iterable<Product> getProductsByGenre(@PathVariable String genre) {
        try{
            return productService.getProductsByGenre(genre);
        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }


    @Secured("permitAll")
    @GetMapping("/productsByTitle/{title}")
    public @ResponseBody Iterable<Product> getProductsByTitle(@PathVariable String title) {
        try{
            return productService.getProductsByTitle(title);
        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }

    @RolesAllowed({"admin"})
    @PostMapping("/stock/{isbn}/{quantity}")
    public void addStock(@PathVariable(name = "isbn") String isbn, @PathVariable(name = "quantity") int quantity) {
        try{
            productService.addStock(isbn, quantity);
        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }

    @Secured("permitAll")
    @GetMapping("/stock/{id}")
    public int getStock(@PathVariable(name = "id") UUID id)  {
        try{
            return productService.getStock(id);
        } catch (Exception e) {
            throw new ProductNotFoundException(id);
        }
    }

    @Secured("permitAll")
    @GetMapping("/productByIsbn/{isbn}")
    public Product getProductByIsbn(@PathVariable String isbn) {
        try {
            return productService.getProductByIsbn(isbn);
        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }

    @Secured("permitAll")
    @GetMapping("/productByTitle/{title}")
    public Iterable<Product> getProductByTitle(@PathVariable String title) {
        try {
            return productService.getProductsByTitle(title);
        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }

    @Secured("permitAll")
    @GetMapping("/productByAuthor/{authorId}")
    public Iterable<Product> getProductByAuthor(@PathVariable UUID authorId) {
        try {
            return authorService.getProducts(authorId);
        } catch (Exception e) {
            throw new AuthorNotFoundException(authorId);
        }
    }

    @Secured("permitAll")
    @GetMapping("/productsByDistrict/{district}")
    public Iterable<Product> getProductByDistrict(@PathVariable String district) {
        try {
            return searchService.getProductsByDistrict(district);
        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }

    @Secured("permitAll")
    @GetMapping("/productsByPlz/{plz}")
    public Iterable<Product> getProductByPlz(@PathVariable String plz) {
        try{
            return searchService.getProductsByPlz(plz);
        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }
}
