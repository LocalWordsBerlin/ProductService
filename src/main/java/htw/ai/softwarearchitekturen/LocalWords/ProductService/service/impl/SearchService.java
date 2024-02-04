package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.AuthorNotFoundException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IAuthorService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.ISearchService;
import org.springframework.stereotype.Service;

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
        return authorService.getAuthorsByDistrict(district).iterator().next().getProducts();
    }

    @Override
    public Iterable<Product> getProductsByPlz(String plz) {
        return authorService.getAuthorsByPlz(plz).iterator().next().getProducts();
    }




}
