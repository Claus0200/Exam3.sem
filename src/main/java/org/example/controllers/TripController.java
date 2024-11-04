package org.example.controllers;

import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.example.config.HibernateConfig;
import org.example.config.Populator;
import org.example.daos.TripDAO;
import org.example.dtos.GuidePriceDTO;
import org.example.dtos.PackagingDTO;
import org.example.dtos.TripDTO;
import org.example.dtos.TripPackingDTO;
import org.example.enums.Category;
import org.example.exceptions.ApiException;
import org.example.services.PackagingService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TripController implements IController, ITripController {

    private TripDAO dao;

    public TripController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = TripDAO.getInstance(emf);
    }


    @Override
    public void create(Context ctx) {
        TripDTO tripDTO;
        try {
            tripDTO = ctx.bodyAsClass(TripDTO.class);
        } catch (Exception e) {
            throw new ApiException(400, "Invalid trip data or format");
        }
        TripDTO createdTrip = dao.create(tripDTO);
        if (createdTrip == null) {
            throw new ApiException(400, "Trip already exists");
        }
        ctx.json(createdTrip).status(201);
    }

    @Override
    public void read(Context ctx) {
        int id;
        try {
            id = Integer.parseInt(ctx.pathParam("id"));
        } catch (NumberFormatException e) {
            throw new ApiException(500, "Invalid id");
        }
        TripDTO trip = dao.getById(id);
        if (trip == null) {
            throw new ApiException(404, "Trip not found");
        }
        try {
            List<PackagingDTO> packagingList = getPackaging(trip.getCategory());
            TripPackingDTO tripPacking = new TripPackingDTO(trip, packagingList);
            ctx.json(tripPacking).status(200);
        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }
    }

    @Override
    public void readAll(Context ctx) {
        List<TripDTO> trips = dao.getAll();
        if (trips.isEmpty()) {
            throw new ApiException(500, "No trips found");
        } else {
            ctx.json(trips).status(200);
        }
    }

    @Override
    public void update(Context ctx) {
        int id;
        try {
            id = Integer.parseInt(ctx.pathParam("id"));
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid id");
        }

        TripDTO tripDTO;
        try {
            tripDTO = ctx.bodyAsClass(TripDTO.class);
        } catch (Exception e) {
            throw new ApiException(400, "Invalid trip data or format");
        }

        try {
            TripDTO updatedTrip = dao.update(id, tripDTO);
            if (updatedTrip == null) {
                throw new ApiException(404, "Trip not found");
            }
            ctx.json(updatedTrip).status(200);
        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }
    }

    @Override
    public void delete(Context ctx) {
        int id;
        try {
            id = Integer.parseInt(ctx.pathParam("id"));
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid id");
        }
        TripDTO trip = dao.delete(id);
        if (trip == null) {
            throw new ApiException(404, "Trip not found");
        }
        ctx.json(trip).status(204);
    }

    @Override
    public void addGuideToTrip(Context ctx) {
        int tripId;
        int guideId;
        try {
            tripId = Integer.parseInt(ctx.pathParam("tripId"));
            guideId = Integer.parseInt(ctx.pathParam("guideId"));
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid id");
        }
        try {
            dao.addGuideToTrip(tripId, guideId);
        } catch (Exception e) {
            throw new ApiException(500, "Error adding guide to trip");
        }
        ctx.status(204);
    }

    @Override
    public void populate(Context ctx) {
        Populator.main(null);
        ctx.status(204);
    }

    @Override
    public void sortByCategory(Context ctx) {
        String cat = ctx.pathParam("category");
        Category category;
        try {
            category = Category.valueOf(cat.toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new ApiException(400, "Invalid category");
        }
        Set<TripDTO> trips = dao.getTripsByCategory(category);
        if (trips.isEmpty()) {
            throw new ApiException(404, "No trips found");
        } else {
            ctx.json(trips).status(200);
        }
    }

    @Override
    public void getTotalPriceOfGuides(Context ctx) {
        Map<Integer, Float> totalPriceOfGuides = dao.getPriceByGuide();
        if (totalPriceOfGuides == null) {
            throw new ApiException(404, "No trips found");
        }
        Set<GuidePriceDTO> guidePriceDTOS = new HashSet<>();
        totalPriceOfGuides.forEach((g,p) -> guidePriceDTOS.add(new GuidePriceDTO(g,p)));

        ctx.json(guidePriceDTOS).status(200);
    }

    @Override
    public List<PackagingDTO> getPackaging(Category category) {
        try {
            return PackagingService.getPackaging(category);
        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }
    }

    @Override
    public void getTotalPriceOfPacking(Context ctx) {
        int tripId;
        try {
            tripId = Integer.parseInt(ctx.pathParam("tripId"));
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid id");
        }
        TripDTO trip = dao.getById(tripId);
        if (trip == null) {
            throw new ApiException(404, "Trip not found");
        }

        List<PackagingDTO> packagingList = getPackaging(trip.getCategory());
        float weight = 0;
        for (PackagingDTO p : packagingList) {
            weight += p.getWeightInGrams();
        }
        ctx.json(weight).status(200);
    }


}
