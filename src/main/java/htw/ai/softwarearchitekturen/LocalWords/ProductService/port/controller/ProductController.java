package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.controller;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.OutOfStockException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.ProductAlreadyExistsException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.ProductNotFoundException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.cart.AddToCartProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IAuthorService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
public class ProductController {
        @Autowired
        private IProductService productService;

        @Autowired
        private IAuthorService authorService;

        @Autowired
        private AddToCartProducer addToCartProducer;

        /*
        - checks if product with given isbn already exists
        - if not -> Repository creates product and returns it
        - adds product id to all author instances listed in the author set
         */
        @PostMapping(path = "/product")
        @ResponseStatus(HttpStatus.OK)
        public @ResponseBody Product create(@RequestBody Product product) {
            Product alreadyExists = productService.getProductByIsbn(product.getIsbn());
            if(alreadyExists!=null)
                throw new ProductAlreadyExistsException(product.getIsbn());
            Product result = null;
            try{
                result = productService.createProduct(product);
                for(Author author : product.getAuthors())
                    authorService.addProduct(author.getId(), result.getId());
            }catch(Exception e){
                throw new RuntimeException("Something weird happened");
            }
            return result;
        }

        @GetMapping("/product/{id}")
        public Product getProduct(@PathVariable UUID id) {
             return productService.getProduct(id);
        }

        @PostMapping("/addToCart/{productId}")
        public void addToCart(@PathVariable UUID productId){
            if(productService.getStock(productId)>0) {
                addToCartProducer.sendMessage("add to Cart" + productId);
            }else{
                throw new OutOfStockException(productId);
            }
        }
        //return updated Product @ResponseBody String instead of void
        @PutMapping(path = "/product")
        public void update(@RequestBody Product product) {
            productService.updateProduct(product);
        }

        @DeleteMapping(path = "/product")
        public @ResponseBody String delete(@PathVariable UUID id) {
            try{
                productService.deleteProduct(id);
            }catch(Exception e){
                throw new ProductNotFoundException(id);
            }
            return "Successfully deleted Product "+id;
        }

        @GetMapping("/products")
        public @ResponseBody Iterable<Product> getProducts() {
            return productService.getAllProducts();
        }
        @PostMapping("/stock/{id}/{quantity}")
        public void addStock(@PathVariable(name = "id") UUID id, @PathVariable(name = "quantity") int quantity) throws ProductNotFoundException {
            productService.addStock(id, quantity);
        }

        @GetMapping("/stock/{id}")
        public int getStock(@PathVariable(name = "id") UUID id) throws ProductNotFoundException {
            return productService.getStock(id);
        }
}
