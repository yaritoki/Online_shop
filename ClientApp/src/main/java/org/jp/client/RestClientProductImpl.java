package org.jp.client;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jp.controllers.payload.NewProductPayload;
import org.jp.controllers.payload.UpdateProductPayload;
import org.jp.entity.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class RestClientProductImpl implements RestClientProduct{
    private final RestClient restClient;
    private static final ParameterizedTypeReference<List<Product>> PRODUCTS_TYPE_REFERENCE=
            new ParameterizedTypeReference<>() {
            };
    @Override
    public List<Product> findAllProducts(String filter) {
        return this.restClient.get()
                .uri("/catalogue-api/products?filter={filter}",filter)
                //.header("Authorization","Bearer "+token)
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public Product createProduct(String title, String details, String img,float price) {
        try{
        return this.restClient.post()
                .uri("/catalogue-api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new NewProductPayload(title,details,img,price))
                .header("authority","admin")
                .retrieve()
                .body(Product.class);
    }catch (HttpClientErrorException.BadRequest exception){
            ProblemDetail problemDetail=exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>)problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Product> findById(int productId) {
        try {
            return Optional.ofNullable(
                    this.restClient.get()
                    .uri("/catalogue-api/products/{productId}", productId)
                           // .header("Authorization","Bearer "+token)
                    .retrieve()
                    .body(Product.class));
        }catch(HttpClientErrorException.NotFound exception){
          return Optional.empty();
        }
    }

    @Override
    public void updateProduct(Integer productId, String title, String details, String img,float price) {
        try{
            this.restClient
                    .patch()
                    .uri("/catalogue-api/products/{productId}",productId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateProductPayload(title,details,img,price ))
                    .retrieve()
                    .toBodilessEntity();
        }catch (HttpClientErrorException exception){
            ProblemDetail problemDetail=exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteProduct(Integer productId) {
        try {
            this.restClient
                    .delete()
                    .uri("/catalogue-api/products/{productId}",productId)
                    .retrieve()
                    .toBodilessEntity();
        }catch (HttpClientErrorException exception){
            throw new NoSuchElementException(exception);
        }
    }

}
