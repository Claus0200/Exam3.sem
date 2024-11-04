package org.example.daos;

import org.example.dtos.TripDTO;
import org.example.entities.Trip;
import org.example.enums.Category;

import java.util.Map;
import java.util.Set;

public interface ITripGuideDAO {

    void addGuideToTrip(int tripId, int guideId);
    Set<TripDTO> getTripsByGuide(int guideId);
    Set<TripDTO> getTripsByCategory(Category category);
    Map<Integer, Float> getPriceByGuide();
}
