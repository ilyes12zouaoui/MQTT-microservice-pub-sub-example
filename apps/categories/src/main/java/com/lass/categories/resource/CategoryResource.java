package com.lass.categories.resource;

import com.lass.categories.commun.to.CategoryTO;
import com.lass.categories.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {

    @Autowired
    CategoryService categoryService;

    @PostMapping("")
    public CategoryTO create(@RequestBody CategoryTO categoryTO){
        return categoryService.create(categoryTO);
    }

    @PutMapping("")
    public CategoryTO update(@RequestBody CategoryTO categoryTO){
        return categoryService.update(categoryTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        categoryService.remove(id);
    }

    @GetMapping("/{id}")
    public CategoryTO getOne(@PathVariable Long id){
       return categoryService.getById(id);
    }

    @GetMapping("")
    public Page<CategoryTO> getPage(Pageable pageable){
       return categoryService.getByPage(pageable);
    }


}
