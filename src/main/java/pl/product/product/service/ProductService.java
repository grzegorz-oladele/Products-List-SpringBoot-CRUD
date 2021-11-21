package pl.product.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.product.product.exception.ProductNotFoundException;
import pl.product.product.model.Product;
import pl.product.product.repository.ProductRepository;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //    get all products
    public ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    //    add new products
    public ResponseEntity<Product> addNewProduct(Product product) {
        productRepository.save(product);
        return ResponseEntity.created(URI.create("/" + product.getId())).body(product);
    }

    //    find product by id
    public ResponseEntity<Product> findProductById(long id) {
        return productRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


//    update product by id
    public ResponseEntity<?> updateProductById(long id, Product productDetails) {
        if(!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productDetails.setId(id);
        productRepository.save(productDetails);
        return ResponseEntity.noContent().build();
    }

//    delete product by id
    public ResponseEntity<Map<String, Boolean>> deleteProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product with id = " + id + " not found"));
        productRepository.delete(product);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted product", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
