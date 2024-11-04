package org.example.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BuyingOptionDTO {
    private String name;
    private float price;
    private int stock;
}
