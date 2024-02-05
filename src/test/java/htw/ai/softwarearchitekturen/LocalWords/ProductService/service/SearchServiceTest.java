package htw.ai.softwarearchitekturen.LocalWords.ProductService.service;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl.SearchService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IAuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private IAuthorService authorService;

    @InjectMocks
    private SearchService searchService;

    @Test
    public void testGetProductsByDistrict() {
        //SetUp
        String district = "Köpenick";
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();

        Author author1 = new Author();
        author1.setProducts(new HashSet<>(Arrays.asList(product1, product2)));

        Author author2 = new Author();
        author2.setProducts(new HashSet<>(Collections.singletonList(product3)));

        when(authorService.getAuthorsByDistrict(anyString())).thenReturn(Arrays.asList(author1, author2));

        //Act
        Iterable<Product> result = searchService.getProductsByDistrict(district);

        //Assert
        Iterable<Product> expectedProducts = Arrays.asList(product1, product2, product3);
        assertEquals(expectedProducts, result);
    }

    @Test
    public void testGetProductsByPlz() {
        //SetUp
        String plz = "12345";
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();

        Author author1 = new Author();
        author1.setProducts(new HashSet<>(Arrays.asList(product1, product2)));

        Author author2 = new Author();
        author2.setProducts(new HashSet<>(Collections.singletonList(product3)));

        when(authorService.getAuthorsByPlz(anyString())).thenReturn(Arrays.asList(author1, author2));

        //Act
        Iterable<Product> result = searchService.getProductsByPlz(plz);

        //Assert
        Iterable<Product> expectedProducts = Arrays.asList(product1, product2, product3);
        assertEquals(expectedProducts, result);
    }


}
