package org.jp.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jp.controllers.payload.UpdateProductPayload;
import org.jp.entity.Product;
import org.jp.service.ProductService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalogue-api/products/{productId}")
public class CurentProductController {

    private final ProductService productService;
    private final MessageSource messageSource;
    @ModelAttribute
    private Product getProductById(@PathVariable("productId" )int productId){
        return this.productService.findProduct(productId).orElseThrow(()->new NoSuchElementException());

    }

    @GetMapping
    @PreAuthorize("PermitAll()")
    public ResponseEntity<?> findProduct(@ModelAttribute("product") Product product){
    return ResponseEntity.ok(product);
    }

    @PatchMapping

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editProduct(@PathVariable("productId") int productId,
                                         @Valid @RequestBody UpdateProductPayload payload,
                                         BindingResult bindingResult, Locale locale){
        if(bindingResult.hasErrors()){
            ProblemDetail problemDetail=ProblemDetail.forStatusAndDetail(
                    HttpStatus.BAD_REQUEST,this.messageSource.getMessage(
                            "error.400.title",new Object[0],"error.400.title",locale
                    )
            );
            problemDetail.setProperty("errors",
                    bindingResult.getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage).toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);



        }else{
            this.productService.updateProduct(productId,payload.getTitle(),payload.getDetails(),payload.getImg(),payload.getPrice());
            return ResponseEntity.noContent().build();
        }

    }


    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") int productId){
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();

    }

}
