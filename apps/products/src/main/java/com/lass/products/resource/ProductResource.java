package com.lass.products.resource;

import com.lass.products.commun.to.ProductTO;
import com.lass.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductResource {

    @Autowired
    ProductService productService;

    @PostMapping("")
    public ProductTO create(@RequestBody ProductTO productTO){
        return productService.create(productTO);
    }

    @PutMapping("")
    public ProductTO update(@RequestBody ProductTO productTO){
        return productService.update(productTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        productService.remove(id);
    }

    @GetMapping("/{id}")
    public ProductTO getOne(@PathVariable Long id){
       return productService.getById(id);
    }

    @GetMapping("")
    public Page<ProductTO> getPage(Pageable pageable){
       return productService.getByPage(pageable);
    }


}
