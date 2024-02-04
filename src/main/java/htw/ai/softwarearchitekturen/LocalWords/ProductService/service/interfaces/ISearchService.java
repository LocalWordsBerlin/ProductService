package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;

public interface ISearchService {

    Iterable<Product> getProductsByDistrict(String district);

    Iterable<Product> getProductsByPlz(String plz);



}
