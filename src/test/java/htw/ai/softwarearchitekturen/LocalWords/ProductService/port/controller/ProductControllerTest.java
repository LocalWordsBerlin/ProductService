package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
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

import static org.mockito.Mockito.*;
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

    private ObjectMapper objectMapper;

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

        mockMvc.perform(MockMvcRequestBuilders.get("api/v1/product/" + newProduct.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newProduct.getId().toString()))
                .andExpect(jsonPath("$.isbn").value(newProduct.getIsbn()))
                .andExpect(jsonPath("$.title").value(newProduct.getTitle()));

        verify(productService, times(1)).getProduct(any(UUID.class));
    }

   @Test
    public void testAddToCart() throws Exception {
        UUID productId = UUID.randomUUID();
        when(productService.getStock(productId)).thenReturn(1);

        mockMvc.perform(MockMvcRequestBuilders.post("api/v1/addToCart/" + productId)
                        .contentType("application/json"))
                .andExpect(status().isOk());

        verify(productService, times(1)).getStock(productId);
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Product newProduct = new Product();
        newProduct.setId(UUID.randomUUID());
        newProduct.setIsbn("123456789");
        newProduct.setTitle("Test Product");

        when(productService.getProduct(any(UUID.class))).thenReturn(newProduct);
        when(productService.updateProduct(any(Product.class))).thenReturn(newProduct);

        mockMvc.perform(put("api/v1/product")
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

        mockMvc.perform(MockMvcRequestBuilders.delete("api/v1/product/" + productId)
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

        mockMvc.perform(MockMvcRequestBuilders.get("api/v1/products")
                        .contentType("application/json"))
                .andExpect(status().isOk());

        verify(productService, times(1)).getAllProducts();
    }






}


