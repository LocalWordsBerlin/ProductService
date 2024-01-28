package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.controller;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.config.RabbitMQConfig;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.OutOfStockException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.ProductAlreadyExistsException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.ProductNotFoundException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin.IProductProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin.UpdateProductProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.cart.AddToCartProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IAuthorService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class ProductController {

    private final IProductService productService;
    private final IAuthorService authorService;
    private final UpdateProductProducer productProducer;

    @Autowired
    public ProductController(IProductService productService, IAuthorService authorService, UpdateProductProducer productProducer) {
        this.productService = productService;
        this.authorService = authorService;
        this.productProducer = productProducer;
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
        Product result = null;
        try {
            result = productService.createProduct(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Secured("permitAll")
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable UUID id) {
        return productService.getProduct(id);
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
        productService.updateProduct(product);
    }

    @RolesAllowed({"admin"})
    @DeleteMapping(path = "/product")
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

    @RolesAllowed({"admin"})
    @PostMapping("/stock/{id}/{quantity}")
    public void addStock(@PathVariable(name = "id") UUID id, @PathVariable(name = "quantity") int quantity) throws ProductNotFoundException {
        productService.addStock(id, quantity);
    }

    @Secured("permitAll")
    @GetMapping("/stock/{id}")
    public int getStock(@PathVariable(name = "id") UUID id) throws ProductNotFoundException {
        return productService.getStock(id);
    }
}
