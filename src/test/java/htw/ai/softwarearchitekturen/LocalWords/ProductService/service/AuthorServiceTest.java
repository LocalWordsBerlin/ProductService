package htw.ai.softwarearchitekturen.LocalWords.ProductService.service;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl.AuthorService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IAuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    private IAuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void testCreateAuthor() {
        Author author = new Author();
        when(authorRepository.save(author)).thenReturn(author);

        Author result = authorService.create(author);

        assertEquals(author, result);
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testGetAuthorById() {
        UUID authorId = UUID.randomUUID();
        Author author = new Author();
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        Author result = authorService.getAuthor(authorId);

        assertEquals(author, result);
    }

    @Test
    void testGetAuthorByIdNotFound() {
        UUID authorId = UUID.randomUUID();
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());
        assertNull(authorService.getAuthor(authorId));
    }

    @Test
    void testGetAllAuthors() {
        authorService.getAllAuthors();
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testUpdateAuthorExists() {
        Author author = new Author();
        author.setId(UUID.randomUUID());

        when(authorRepository.existsById(author.getId())).thenReturn(true);

        Author result = authorService.update(author);

        assertNull(result);
    }

    @Test
    void testUpdateAuthorNotExists() {
        Author author = new Author();
        author.setId(UUID.randomUUID());

        when(authorRepository.existsById(author.getId())).thenReturn(false);

        Author result = authorService.update(author);

        assertNull(result);
        verify(authorRepository, never()).save(author);
    }

    @Test
    void testDeleteAuthorExists() {
        UUID authorId = UUID.randomUUID();

        when(authorRepository.existsById(authorId)).thenReturn(true);

        boolean result = authorService.delete(authorId);

        assertTrue(result);

        verify(authorRepository, times(1)).deleteById(authorId);
    }

    @Test
    void testDeleteAuthorNotExists() {
        UUID authorId = UUID.randomUUID();
        when(authorRepository.existsById(authorId)).thenReturn(false);

        boolean result = authorService.delete(authorId);

        assertFalse(result);

        verify(authorRepository, never()).deleteById(authorId);
    }

    @Test
    void testAddProductProductAlreadyExists() {
        UUID authorId = UUID.randomUUID();
        Product product = new Product();
        Author author = new Author();
        author.setId(authorId);
        author.setProducts(new HashSet<>(Set.of(product)));

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        assertFalse(authorService.addProduct(authorId, product));

        verify(authorRepository, never()).save(author);
    }

    @Test
    void testAddProductProductDoesNotExist() {
        UUID authorId = UUID.randomUUID();
        Product product = new Product();
        Author author = new Author();
        author.setId(authorId);
        author.setProducts(new HashSet<>());

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(authorRepository.save(author)).thenReturn(author);

        assertTrue(authorService.addProduct(authorId, product));

        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testGetProducts() {
        UUID authorId = UUID.randomUUID();
        Author author = new Author();
        Set<Product> products = new HashSet<>(Set.of(new Product()));
        author.setProducts(products);

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        assertEquals(products, authorService.getProducts(authorId));
    }

    @Test
    void testGetAuthorByNameAuthorExists() {
        String name = "John Doe";
        Author author = new Author();

        when(authorRepository.findByFirstNameOrLastName(name, name)).thenReturn(author);

        assertEquals(author, authorService.getAuthorByName(name));
    }

    @Test
    void testGetAuthorByNameAuthorNotFound() {
        String name = "Nonexistent Author";

        when(authorRepository.findByFirstNameOrLastName(name, name)).thenReturn(null);

        assertNull(authorService.getAuthorByName(name));
    }

    @Test
    void testGetAuthorsByDistrict() {
        String district = "Sample District";

        when(authorRepository.findByDistrict(district)).thenReturn(new HashSet<>());

        assertNotNull(authorService.getAuthorsByDistrict(district));
    }

    @Test
    void testGetAuthorsByPlz() {
        String plz = "12345";

        when(authorRepository.findByPlz(plz)).thenReturn(new HashSet<>());

        assertNotNull(authorService.getAuthorsByPlz(plz));
    }







}
