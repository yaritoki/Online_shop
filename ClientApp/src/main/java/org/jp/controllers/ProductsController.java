package org.jp.controllers;

import lombok.RequiredArgsConstructor;
import org.jp.client.BadRequestException;
import org.jp.client.RestClientProduct;
import org.jp.controllers.payload.NewProductPayload;
import org.jp.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products")
public class ProductsController {
    private final RestClientProduct restClientProduct;
    @GetMapping("list")
    public String getProductList(Model model, @RequestParam(name="filter", required = false) String filter){
        model.addAttribute("products",this.restClientProduct.findAllProducts(filter));
        model.addAttribute("filter",filter);
        return "catalogue/products/list";
    }
    @GetMapping("create")
    public String getNewProduct(){
        return "catalogue/products/new_product";
    }
    @PostMapping("create")
    public String createProduct(Model model,NewProductPayload payload){
        try{
            Product product=this.restClientProduct
                    .createProduct(payload.getTitle(),payload.getDetails(),payload.getImg(), payload.getPrice());
           return "redirect:/catalogue/products/%d".formatted(product.getId());

        }catch (BadRequestException exception){
            model.addAttribute("payload",payload);
            model.addAttribute("errors",exception.getErrors());
            return "catalogue/products/new_product";
        }
    }

}
