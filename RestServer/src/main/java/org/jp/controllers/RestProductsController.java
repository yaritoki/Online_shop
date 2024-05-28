package org.jp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jp.controllers.payload.NewProductPayload;
import org.jp.entity.Product;
import org.jp.service.ProductService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalogue-api/products")
public class RestProductsController {
private final ProductService productService;
private final MessageSource messageSource;

@GetMapping
    public Iterable<Product> findProduct(@RequestParam(name="filter", required = false) String filter){
    return this.productService.findAllProducts(filter);
}

@PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody NewProductPayload payload,
                                           BindingResult bindingResult,
                                           UriComponentsBuilder uriComponentsBuilder,
                                           Locale locale) throws BindException {
    if(bindingResult.hasErrors()){
        ProblemDetail problemDetail =ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,this.messageSource.
                getMessage("error.title.400",new Object[0], locale));
        problemDetail.setProperty("errors",
                bindingResult.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage).toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }else{
        Product product=this.productService.createProduct(payload.getTitle(),payload.getDetails(),payload.getImg(),payload.getPrice());
        return ResponseEntity.created(
        uriComponentsBuilder.replacePath("/catalogue-api/products/{productId}")
                .build("productId",product.getId()))
                .body(product);
        }
    }
}
