package pl.product.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.product.product.exception.ProductNotFoundException;
import pl.product.product.model.Product;
import pl.product.product.repository.ProductRepository;

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

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product addNewProduct(Product product) {
        return productRepository.save(product);
    }

    public ResponseEntity<Product> findProductById(long id) {
//        Product product =  productRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Product with id = " + id + " not found"));
        Product product = findProduct(id);
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<Product> updateProductById(long id, Product productDetails) {
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Product with id = " + id + " not found"));
        Product product = findProduct(id, productDetails);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setNumber(productDetails.getNumber());
        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    public ResponseEntity<Map<String, Boolean>> deleteProduct(long id) {
//        Product product  = productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product with id = " + id + " not found"));
        Product product = findProduct(id);
        productRepository.delete(product);
        Map<String, Boolean> response =  new HashMap<>();
        response.put("Deleted product", Boolean.TRUE);
        return  ResponseEntity.ok(response);
    }

    private Product findProduct(long id, Product product) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id = " + id + " not found"));
    }

    private Product findProduct(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id = " + id + " not found"));
    }
}
