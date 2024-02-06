package htw.ai.softwarearchitekturen.LocalWords.ProductService.service;

import groovy.util.logging.Slf4j;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin.IStockProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl.DTOMapper;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl.ProductService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.shaded.com.trilead.ssh2.Connection;

import java.sql.DriverManager;
import java.time.Duration;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private DTOMapper dtoMapper;

    @Autowired
    private IStockProducer stockProducer;

    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0");

    static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.10.6-management-alpine").withStartupTimeout(Duration.of(100, SECONDS));
    @BeforeAll
    static void beforeAll() {
        mysqlContainer.start();
        rabbitMQContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mysqlContainer.stop();
        rabbitMQContainer.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        System.out.println("rabbit url: "+ rabbitMQContainer.getAmqpUrl());
        System.out.println(rabbitMQContainer.getHttpPort() + " " + rabbitMQContainer.getHost());
        registry.add("spring.rabbitmq.host",() -> rabbitMQContainer.getHost());
        registry.add("spring.rabbitmq.port",() -> rabbitMQContainer.getAmqpPort());

    }

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    public void testCreateProduct() {
        //Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setIsbn("123456789");
        product.setDescription("Test Description");
        product.setPrice(10.0);
        product.setLanguage("English");
        product.setStock(10);
        product.setDiscount(0);
        product.setPrice(10.0);
        //Act
        Product createdProduct = productService.createProduct(product);
        //Assert
        assertNotNull(createdProduct.getId());
        assertEquals(product.getTitle(), createdProduct.getTitle());
    }

    @Test
    public void testCreateProductAlreadyExists() {
        //Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setIsbn("123456789");
        product.setDescription("Test Description");
        product.setPrice(10.0);
        product.setLanguage("English");
        product.setStock(10);
        product.setDiscount(0);
        product.setPrice(10.0);
        Product createdProduct = productService.createProduct(product);
        //Act
        Product createdProduct2 = productService.createProduct(product);
        //Assert
        assertEquals(createdProduct.getId(), createdProduct2.getId());
    }

    @Test
    public void testUpdateProduct() {
        //Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setIsbn("123456789");
        product.setDescription("Test Description");
        product.setPrice(10.0);
        product.setLanguage("English");
        product.setStock(10);
        product.setDiscount(0);
        product.setPrice(10.0);
        Product createdProduct = productService.createProduct(product);
        createdProduct.setTitle("Updated Test Product");
        //Act
        Product updatedProduct = productService.updateProduct(createdProduct);
        //Assert
        assertEquals(createdProduct.getId(), updatedProduct.getId());
        assertEquals("Updated Test Product", updatedProduct.getTitle());
    }

    @Test
    public void testGetProduct() {
        //Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setIsbn("123456789");
        product.setDescription("Test Description");
        product.setPrice(10.0);
        product.setLanguage("English");
        product.setStock(10);
        product.setDiscount(0);
        product.setPrice(10.0);
        Product createdProduct = productService.createProduct(product);
        //Act
        Product foundProduct = productService.getProduct(createdProduct.getId());
        //Assert
        assertNotNull(foundProduct);
        assertEquals(createdProduct.getId(), foundProduct.getId());
    }

    @Test
    public void testGetAllProducts() {
        //Arrange
        Product product1 = new Product();
        product1.setTitle("Test Product 1");
        product1.setIsbn("123456789");
        product1.setDescription("Test Description");
        product1.setPrice(10.0);
        product1.setLanguage("English");
        product1.setStock(10);
        product1.setDiscount(0);
        product1.setPrice(10.0);
        Product createdProduct1 = productService.createProduct(product1);
        Product product2 = new Product();
        product2.setTitle("Test Product 2");
        product2.setIsbn("123456780");
        product2.setDescription("Test Description");
        product2.setPrice(10.0);
        product2.setLanguage("English");
        product2.setStock(10);
        product2.setDiscount(0);
        product2.setPrice(10.0);
        Product createdProduct2 = productService.createProduct(product2);
        //Act
        Iterable<Product> products = productService.getAllProducts();
        //Assert
        assertNotNull(products);
        assertEquals(2, products.spliterator().getExactSizeIfKnown());
    }

    @Test
public void testDeleteProduct() {
        //Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setIsbn("123456789");
        product.setDescription("Test Description");
        product.setPrice(10.0);
        product.setLanguage("English");
        product.setStock(10);
        product.setDiscount(0);
        product.setPrice(10.0);
        Product createdProduct = productService.createProduct(product);
        //Act
        productService.deleteProduct(createdProduct.getId());
        //Assert
        assertEquals(0, productRepository.count());
    }

    @Test
    public void testAddStock() {
        //Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setIsbn("123456789");
        product.setDescription("Test Description");
        product.setPrice(10.0);
        product.setLanguage("English");
        product.setStock(10);
        product.setDiscount(0);
        product.setPrice(10.0);
        Product createdProduct = productService.createProduct(product);
        //Act
        productService.addStock(createdProduct.getIsbn(), 10);
        //Assert
        assertEquals(20, productService.getStock(createdProduct.getId()));
        verify(stockProducer, times(1)).send(any(StockDTO.class));
    }
    @Test
    public void testGetStock() {
        //Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setIsbn("123456789");
        product.setDescription("Test Description");
        product.setPrice(10.0);
        product.setLanguage("English");
        product.setStock(10);
        product.setDiscount(0);
        product.setPrice(10.0);
        Product createdProduct = productService.createProduct(product);
        //Act
        int stock = productService.getStock(createdProduct.getId());
        //Assert
        assertEquals(10, stock);
    }



}





