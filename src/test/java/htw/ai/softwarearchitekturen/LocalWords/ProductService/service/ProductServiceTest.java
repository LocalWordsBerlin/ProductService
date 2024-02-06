package htw.ai.softwarearchitekturen.LocalWords.ProductService.service;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin.IStockProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl.ProductService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private IProductRepository productRepository;

    @Mock
    private IStockProducer stockProducer;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testCreateProduct() {
        // Arrange
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setTitle("Test Product");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product createdProduct = productService.createProduct(product);

        // Assert
        assertEquals(product.getId(), createdProduct.getId());
        assertEquals(product.getTitle(), createdProduct.getTitle());

        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testUpdateProduct() {
        // Arrange
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setTitle("Test Product");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product updatedProduct = productService.updateProduct(product);

        // Assert
        assertEquals(product.getId(), updatedProduct.getId());
        assertEquals(product.getTitle(), updatedProduct.getTitle());

        verify(productRepository, times(1)).save(product);
        //verify(productProducer, times(1)).sendMessage(product.toString());

    }

    @Test
    public void testDeleteProduct() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        productService.deleteProduct(id);

        // Assert
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetProduct() {
        // Arrange
        UUID id = UUID.randomUUID();
        Product product = new Product();
        product.setId(id);
        product.setTitle("Test Product");

        when(productRepository.findById(id)).thenReturn(java.util.Optional.of(product));

        // Act
        Product foundProduct = productService.getProduct(id);

        // Assert
        assertEquals(product.getId(), foundProduct.getId());
        assertEquals(product.getTitle(), foundProduct.getTitle());

        verify(productRepository, times(1)).findById(id);
    }

    @Test
    public void testGetAllProducts() {
        // Arrange
        Product product1 = new Product();
        product1.setId(UUID.randomUUID());
        product1.setTitle("Test Product 1");
        Product product2 = new Product();
        product2.setId(UUID.randomUUID());
        product2.setTitle("Test Product 2");

        when(productRepository.findAll()).thenReturn(java.util.List.of(product1, product2));

        // Act
        Iterable<Product> products = productService.getAllProducts();

        // Assert
        assertEquals(2, ((java.util.List<Product>) products).size());
        assertEquals(product1.getId(), ((java.util.List<Product>) products).get(0).getId());
        assertEquals(product1.getTitle(), ((java.util.List<Product>) products).get(0).getTitle());
        assertEquals(product2.getId(), ((java.util.List<Product>) products).get(1).getId());
        assertEquals(product2.getTitle(), ((java.util.List<Product>) products).get(1).getTitle());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testAddStock() {
        // Arrange
        UUID id = UUID.randomUUID();
        Product product = new Product();
        product.setId(id);
        product.setIsbn("Test ISBN");
        product.setTitle("Test Product");
        product.setStock(10);

        when(productRepository.findByIsbn(anyString())).thenReturn(product);

        // Act
        productService.addStock(product.getIsbn(), 5);

        // Assert
        assertEquals(15, product.getStock());

        verify(productRepository, times(1)).findById(id);
    }

    @Test
    public void testGetStock() {
        // Arrange
        UUID id = UUID.randomUUID();
        Product product = new Product();
        product.setId(id);
        product.setTitle("Test Product");
        product.setStock(10);

        when(productRepository.findById(id)).thenReturn(java.util.Optional.of(product));

        // Act
        int stock = productService.getStock(id);

        // Assert
        assertEquals(10, stock);

        verify(productRepository, times(1)).findById(id);
    }


    @Test
    public void testAddAuthor(){
        // Arrange
        UUID id = UUID.randomUUID();
        Product product = new Product();
        product.setId(id);
        product.setTitle("Test Product");
        product.setStock(10);

        when(productRepository.findById(id)).thenReturn(java.util.Optional.of(product));

        // Act

        // Assert
        assertEquals(15, product.getStock());

        verify(productRepository, times(1)).findById(id);
    }


    @Test
    public void testGetAuthors(){
        // Arrange
        UUID id = UUID.randomUUID();
        Product product = new Product();
        product.setId(id);
        product.setTitle("Test Product");
        product.setStock(10);

        when(productRepository.findById(id)).thenReturn(java.util.Optional.of(product));

        // Act
        int stock = productService.getStock(id);

        // Assert
        assertEquals(10, stock);

        verify(productRepository, times(1)).findById(id);
    }

    @Test
    public void testGetProductByIsbn(){
        // Arrange
        Product product = new Product();
        product.setIsbn("Test ISBN");

        when(productRepository.findByIsbn("Test ISBN")).thenReturn(product);

        // Act
        Product result = productService.getProductByIsbn("Test ISBN");

        // Assert
        assertEquals(product, result);

        verify(productRepository, times(1)).findByIsbn("Test ISBN");
    }

    @Test
    public void testGetProductsByGenre(){
        // Arrange
        Product product1 = new Product();
        product1.setGenre("Test Genre");

        Product product2 = new Product();
        product2.setGenre("Test Genre");

        Iterable<Product> products = java.util.List.of(product1, product2);

        when(productRepository.findByGenre("Test Genre")).thenReturn(products);

        // Act
         Iterable<Product> productsByGenre= productService.getProductsByGenre("Test Genre");

        // Assert
        assertEquals(products, productsByGenre);

        verify(productRepository, times(1)).findByGenre("Test Genre");
    }

    @Test
    public void testGetProductsByTitle(){
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");

        Iterable<Product> products = java.util.List.of(product);

        when(productRepository.findByTitle("Test Product")).thenReturn(products);

        // Act
        Iterable<Product> productsByTitle = productService.getProductsByTitle("Test Product");

        // Assert
        assertEquals(products, productsByTitle);

        verify(productRepository, times(1)).findByTitle("Test Product");
    }


    public void tearDown() {
        reset(productRepository);
        reset(stockProducer);
    }





}

