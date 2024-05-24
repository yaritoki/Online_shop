package org.jp.controllers.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewProductPayload {
    private String title;
    private String details;
    private String img="";
    private float price;
}
