package pl.product.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.product.product.model.Product;
import pl.product.product.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ProductController {


    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //    get all products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProduct();
    }

    //    create new products
    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        return productService.addNewProduct(product);
    }

    //    get product by id
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        return productService.findProductById(id);
    }

    //    update product by id
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product productDetails) {
        return productService.updateProductById(id, productDetails);
    }

    //    delete product by id
    @DeleteMapping("products/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }
}
