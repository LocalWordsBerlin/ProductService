package htw.ai.softwarearchitekturen.LocalWords.ProductService.service.impl;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.model.Product;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IProductService;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.service.interfaces.IStockService;
import org.springframework.stereotype.Service;

@Service
public class StockService implements IStockService {

    private final IProductService productService;

    public StockService(IProductService productService) {
        this.productService = productService;
    }
    @Override
    public void updateStock(int stock, String Isbn) {
        Product updateSockProduct = productService.getProductByIsbn(Isbn);
        updateSockProduct.setStock(stock);
        productService.updateProduct(updateSockProduct);
    }
}
