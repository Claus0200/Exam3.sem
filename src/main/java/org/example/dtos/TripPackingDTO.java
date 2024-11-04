package org.example.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class TripPackingDTO {

    private TripDTO trip;
    private List<PackagingDTO> packing;
}
