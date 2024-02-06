package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.dto.AddToCartDTO;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.OutOfStockException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.cart.IAddToCartProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl.DTOMapper;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ComponentScan(basePackages = "htw.ai.softwarearchitekturen")
public class ProductControllerTest {


    @Mock
    private IProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private IAddToCartProducer addToCartProducer;

    @Mock
    private DTOMapper dtoMapper;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product newProduct = new Product();
        newProduct.setId(UUID.randomUUID());
        newProduct.setIsbn("123456789");
        newProduct.setTitle("Test Product");

        when(productService.getProductByIsbn(anyString())).thenReturn(null);
        when(productService.createProduct(any(Product.class))).thenReturn(newProduct);

        mockMvc.perform(post("/api/v1/product")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newProduct.getId().toString()))
                .andExpect(jsonPath("$.isbn").value(newProduct.getIsbn()))
                .andExpect(jsonPath("$.title").value(newProduct.getTitle()));

        verify(productService, times(1)).getProductByIsbn(anyString());
        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    public void testGetProduct() throws Exception {
        Product newProduct = new Product();
        newProduct.setId(UUID.randomUUID());
        newProduct.setIsbn("123456789");
        newProduct.setTitle("Test Product");

        when(productService.getProduct(any(UUID.class))).thenReturn(newProduct);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/" + newProduct.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newProduct.getId().toString()))
                .andExpect(jsonPath("$.isbn").value(newProduct.getIsbn()))
                .andExpect(jsonPath("$.title").value(newProduct.getTitle()));

        verify(productService, times(1)).getProduct(any(UUID.class));
    }

    @Test
    public void testAddToCart() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);
        product.setStock(10);
        when(productService.getProduct(productId)).thenReturn(product);
        when(dtoMapper.mapToCartDTO(product, 10)).thenReturn(new AddToCartDTO());
        // Act
        productController.addToCart(productId, 10);
        // Assert
        verify(addToCartProducer, times(1)).send(any(AddToCartDTO.class));
    }

    @Test
    public void testAddToCart_OutOfStockException() {
        // Arrange
        UUID productId = UUID.randomUUID();
        int quantity = 10;

        when(productService.getStock(productId)).thenReturn(quantity - 1);

        // Act & Assert
        OutOfStockException exception = assertThrows(OutOfStockException.class, () -> productController.addToCart(productId, quantity));
        verify(productService, times(1)).getStock(productId);
        verify(addToCartProducer, never()).send(any(AddToCartDTO.class));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Product newProduct = new Product();
        newProduct.setId(UUID.randomUUID());
        newProduct.setIsbn("123456789");
        newProduct.setTitle("Test Product");

        when(productService.getProduct(any(UUID.class))).thenReturn(newProduct);
        when(productService.updateProduct(any(Product.class))).thenReturn(newProduct);

        mockMvc.perform(put("/api/v1/product")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newProduct.getId().toString()))
                .andExpect(jsonPath("$.isbn").value(newProduct.getIsbn()))
                .andExpect(jsonPath("$.title").value(newProduct.getTitle()));

        verify(productService, times(1)).getProduct(any(UUID.class));
        verify(productService, times(1)).updateProduct(any(Product.class));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/product/" + productId)
                        .contentType("application/json"))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    public void testGetProducts() throws Exception {
        Product newProduct = new Product();
        newProduct.setId(UUID.randomUUID());
        newProduct.setIsbn("123456789");
        newProduct.setTitle("Test Product");

        List<Product> productList = Collections.singletonList(newProduct);

        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")
                        .contentType("application/json"))
                .andExpect(status().isOk());

        verify(productService, times(1)).getAllProducts();
    }






}


