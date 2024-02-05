package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Author;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.producer.admin.UpdateProductProducer;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl.AuthorService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IAuthorService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.ISearchService;
import org.junit.jupiter.api.BeforeAll;
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
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.empty;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateAuthor() throws Exception {
        Author newAuthor = new Author();
        newAuthor.setId(UUID.randomUUID());
        newAuthor.setFirstName("Max");
        newAuthor.setLastName("Mustermann");
        newAuthor.setDistrict("Köpenick");
        newAuthor.setPlz("12345");
        newAuthor.setProducts(Collections.emptySet());

        when(authorService.create(any(Author.class))).thenReturn(newAuthor);
        when(authorService.getAuthorByFirstNameAndLastName(newAuthor.getFirstName(), newAuthor.getLastName())).thenReturn(null);

        mockMvc.perform(post("/api/v1/author")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newAuthor)))
                .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(newAuthor.getId().toString()))
                    .andExpect(jsonPath("$.firstName").value(newAuthor.getFirstName()))
                    .andExpect(jsonPath("$.lastName").value(newAuthor.getLastName()))
                    .andExpect(jsonPath("$.district").value(newAuthor.getDistrict()))
                    .andExpect(jsonPath("$.plz").value(newAuthor.getPlz()));

        verify(authorService, times(1)).create(any(Author.class));
        verify(authorService, times(1)).getAuthorByFirstNameAndLastName(newAuthor.getFirstName(), newAuthor.getLastName());
    }

    @Test
    public void testGetAuthors() throws Exception {
        Author newAuthor = new Author();
        newAuthor.setId(UUID.randomUUID());
        newAuthor.setFirstName("Max");
        newAuthor.setLastName("Mustermann");
        newAuthor.setDistrict("Köpenick");
        newAuthor.setPlz("12345");
        newAuthor.setProducts(Collections.emptySet());

        when(authorService.getAllAuthors()).thenReturn(List.of(newAuthor));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(newAuthor.getId().toString()))
                .andExpect(jsonPath("$[0].firstName").value(newAuthor.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(newAuthor.getLastName()))
                .andExpect(jsonPath("$[0].district").value(newAuthor.getDistrict()))
                .andExpect(jsonPath("$[0].plz").value(newAuthor.getPlz()));

        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    public void testGetAuthor() throws Exception {
        Author newAuthor = new Author();
        newAuthor.setId(UUID.randomUUID());
        newAuthor.setFirstName("Max");
        newAuthor.setLastName("Mustermann");
        newAuthor.setDistrict("Köpenick");
        newAuthor.setPlz("12345");
        newAuthor.setProducts(Collections.emptySet());

        Optional<Author> author = Optional.empty();

        when(authorService.getAuthor(newAuthor.getId())).thenReturn(newAuthor);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/author/" + newAuthor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newAuthor.getId().toString()))
                .andExpect(jsonPath("$.firstName").value(newAuthor.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(newAuthor.getLastName()))
                .andExpect(jsonPath("$.district").value(newAuthor.getDistrict()))
                .andExpect(jsonPath("$.plz").value(newAuthor.getPlz()));

        verify(authorService, times(1)).getAuthor(newAuthor.getId());
    }



}
