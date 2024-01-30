package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.controller;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.config.RabbitMQConfig;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.OutOfStockException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.ProductAlreadyExistsException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.ProductCreationException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.ProductNotFoundException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin.IProductProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin.UpdateProductProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.cart.AddToCartProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IAuthorService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.ISearchService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.ScatteringByteChannel;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class ProductController {

    private final IProductService productService;
    private final IAuthorService authorService;

    private final ISearchService searchService;
    private final UpdateProductProducer productProducer;

    @Autowired
    public ProductController(IProductService productService, IAuthorService authorService, UpdateProductProducer productProducer, ISearchService searchService) {
        this.productService = productService;
        this.authorService = authorService;
        this.productProducer = productProducer;
        this.searchService = searchService;
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
            throw new ProductCreationException("Product could not be created");
        }
    }

    @Secured("permitAll")
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable UUID id) {
        return productService.getProduct(id);
    }

    @Secured("permitAll")
    @GetMapping("/productByIsbn/{isbn}")
    public Product getProductByIsbn(@PathVariable String isbn) {
        return productService.getProductByIsbn(isbn);
    }

    @Secured("permitAll")
    @GetMapping("/productByTitle/{title}")
    public Iterable<Product> getProductByTitle(@PathVariable String title) {
        return productService.getProductsByTitle(title);
    }

    @Secured("permitAll")
    @GetMapping("/productByAuthor/{authorId}")
    public Iterable<Product> getProductByAuthor(@PathVariable UUID authorId) {
        return authorService.getProducts(authorId);
    }

    @Secured("permitAll")
    @GetMapping("/productsByDistrict/{district}")
    public Iterable<Product> getProductByDistrict(@PathVariable String district) {
        return searchService.getProductsByDistrict(district);
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

    @RolesAllowed({"admin", "customer"})
    @PostMapping("/addToCart/{productId}")
    public void addToCart(@PathVariable UUID productId) {
        if (productService.getStock(productId) > 0) {
            //addToCartProducer.sendMessage("add to Cart" + productId);
        } else {
            throw new OutOfStockException(productId);
        }
    }

    //return updated Product @ResponseBody String instead of void
    @RolesAllowed({"admin"})
    @PutMapping(path = "/product")
    public void update(@RequestBody Product product) {
        try {
            productService.getProduct(product.getId());
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
    @GetMapping("/productsByTitleOrAuthors/{search}/{search2}")
    public @ResponseBody Iterable<Product> getProductsByTitleOrAuthors(@PathVariable String search, @PathVariable String search2) {
        try{
            return productService.getProductsByTitleOrAuthors(search, search2);
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
    @PostMapping("/stock/{id}/{quantity}")
    public void addStock(@PathVariable(name = "id") UUID id, @PathVariable(name = "quantity") int quantity) throws ProductNotFoundException {
        try{
            productService.addStock(id, quantity);
        } catch (Exception e) {
            throw new ProductNotFoundException(id);
        }
    }

    @Secured("permitAll")
    @GetMapping("/stock/{id}")
    public int getStock(@PathVariable(name = "id") UUID id) throws ProductNotFoundException {
        try{
            return productService.getStock(id);
        } catch (Exception e) {
            throw new ProductNotFoundException(id);
        }
    }
}
