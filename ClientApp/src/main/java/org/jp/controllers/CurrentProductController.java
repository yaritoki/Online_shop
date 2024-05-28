package org.jp.controllers;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.jp.client.BadRequestException;
import org.jp.client.RestClientProduct;
import org.jp.controllers.payload.UpdateProductPayload;
import org.jp.entity.Product;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products/{productId:\\d+}")
public class CurrentProductController {
    private final RestClientProduct restClientProduct;
    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") int productId){
        return this.restClientProduct.findById(productId)
                .orElseThrow(()->new NoSuchElementException("catalogue.errors.product.not_found"));
    }
    @GetMapping
    public String getProduct(){
            return "catalogue/products/product";
    }

    @GetMapping("edit")
    public String getProductEditPage(){
        return "catalogue/products/edit";
    }
    @PostMapping("edit")
    public String editProduct(@ModelAttribute(value = "product", binding = false) Product product,
                              Model model, BindingResult bindingResult,
                              UpdateProductPayload payload){
        if(bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage).toList());

            return "catalogue/products/edit";
        }else{
            this.restClientProduct.updateProduct(product.getId(),
                    payload.getTitle(),payload.getDetails(),payload.getImg(), payload.getPrice());
            return "redirect:/catalogue/products/%d".formatted(product.getId());
        }
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product")Product product){
        this.restClientProduct.deleteProduct(product.getId());
        return "redirect:/catalogue/products/list";
    }
}
