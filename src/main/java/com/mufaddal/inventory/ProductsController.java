package com.mufaddal.inventory;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;



@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductsRepository repository;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveData(@RequestBody Product product) {
        return repository.save(product);
    }

    @GetMapping("/all")
    public List<Product> getAllData() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Optional<Product> product = repository.findById(id);
        if(product.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(product.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Product());
        }
    }
    
}
