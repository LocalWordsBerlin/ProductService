package htw.ai.softwarearchitekturen.LocalWords.ProductService.infrastructure.controller;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.infrastructure.exception.ProductNotFoundException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ProductController {
        @Autowired
        private IProductService productService;

        @PostMapping(path = "/product")
        @ResponseStatus(HttpStatus.OK)
        public @ResponseBody void create(@RequestBody Product product) {
            productService.createProduct(product);
        }

        @GetMapping("/product/{id}")
        public Product getProduct(@PathVariable UUID id) {
            Product product = productService.getProduct(id);

            if (product == null) {
                throw new ProductNotFoundException(id);
            }

            return product;
        }

        @PutMapping(path = "/product")
        public @ResponseBody String update() {

            return null;
        }

        @DeleteMapping(path = "/product")
        public @ResponseBody String delete() {

            return null;
        }

        @GetMapping("/products")
        public @ResponseBody Iterable<Product> getProducts() {

            return productService.getAllProducts();
        }
        @PostMapping("stock/{id}/{quantity}")
        public void addStock(@PathVariable(name = "id") UUID id, @PathVariable(name = "quantity") int quantity) throws ProductNotFoundException {
            productService.addStock(id, quantity);
        }

        @GetMapping("stock/{id}")
        public int getStock(@PathVariable(name = "id") UUID id) throws ProductNotFoundException {
            return productService.getStock(id);
        }
}
