package org.example.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.entities.Guide;
import org.example.entities.Trip;
import org.example.enums.Category;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TripDTO {
    private int id;
    private LocalDate starttime;
    private LocalDate endtime;
    private float longitude;
    private float latitude;
    private String name;
    private float price;
    private Category category;
    private Guide guide;

    public TripDTO(LocalDate starttime, LocalDate endtime, float longitude, float latitude, String name, float price, Category category) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.starttime = trip.getStarttime();
        this.endtime = trip.getEndtime();
        this.longitude = trip.getLongitude();
        this.latitude = trip.getLatitude();
        this.name = trip.getName();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
        this.guide = trip.getGuide() != null ? trip.getGuide() : null;
    }


    @Override
    public String toString() {
        return "TripDTO{" +
                "id=" + id +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
}
