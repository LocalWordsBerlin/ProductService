package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.AuthorNotFoundException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IAuthorService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.ISearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService implements ISearchService {

    IAuthorService authorService;
    IProductService productService;

    public SearchService(IAuthorService authorService, IProductService productService) {
        this.authorService = authorService;
        this.productService = productService;
    }

    @Override
    public Iterable<Product> getProductsByDistrict(String district) {
        List<Product> productsByDistrict = new ArrayList<>();
        authorService.getAuthorsByDistrict(district).forEach(author ->
                productsByDistrict.addAll(author.getProducts())
        );
        return productsByDistrict;
    }

    @Override
    public Iterable<Product> getProductsByPlz(String plz) {
        List<Product> productsByPLZ = new ArrayList<>();
        authorService.getAuthorsByPlz(plz).forEach(author ->
                productsByPLZ.addAll(author.getProducts())
        );
        return productsByPLZ;
    }




}
