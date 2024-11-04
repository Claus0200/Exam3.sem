package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.dtos.TripDTO;
import org.example.enums.Category;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate starttime;
    private LocalDate endtime;
    private float longitude;
    private float latitude;
    private String name;
    private float price;
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    private Guide guide;

    public void setGuide(Guide guide) {
        this.guide = guide;
        guide.getTrips().add(this);
    }

    public void removeGuide() {
        if (guide != null) {
            guide.getTrips().remove(this);
            guide = null;
        }
    }

    public Trip(LocalDate starttime, LocalDate endtime, float longitude, float latitude, String name, float price, Category category) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Trip(TripDTO tripDTO) {
        this.id = tripDTO.getId();
        this.starttime = tripDTO.getStarttime();
        this.endtime = tripDTO.getEndtime();
        this.longitude = tripDTO.getLongitude();
        this.latitude = tripDTO.getLatitude();
        this.name = tripDTO.getName();
        this.price = tripDTO.getPrice();
        this.category = tripDTO.getCategory();
        this.guide = tripDTO.getGuide();
    }

}
