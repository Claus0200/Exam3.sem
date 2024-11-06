package org.example.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class GuidePriceDTO {

    private int guideId;
    private double price;
}
