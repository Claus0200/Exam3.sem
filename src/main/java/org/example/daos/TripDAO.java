package org.example.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.example.dtos.TripDTO;
import org.example.entities.Guide;
import org.example.entities.Trip;
import org.example.enums.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TripDAO implements iDAO<TripDTO, Integer>, ITripGuideDAO {
    private static TripDAO instance;
    private static EntityManagerFactory emf;

    public static TripDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TripDAO();
        }
        return instance;
    }


    @Override
    public List<TripDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Trip> query = em.createQuery("SELECT d FROM Trip d", Trip.class);
            List<Trip> trips = query.getResultList();
            if (trips.isEmpty()) {
                return new ArrayList<>();
            }
            else {
                return trips.stream()
                        .map(TripDTO::new)
                        .toList();
            }
        }
    }

    @Override
    public TripDTO getById(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, integer);
            if (trip == null) {
                return null;
            }
            else {
                return new TripDTO(trip);
            }
        }
    }

    @Override
    public TripDTO create(TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = new Trip(tripDTO);
            em.persist(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO update(Integer integer, TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, integer);
            if (trip == null) {
                return null;
            }
            trip.setStarttime(tripDTO.getStarttime());
            trip.setEndtime(tripDTO.getEndtime());
            trip.setName(tripDTO.getName());
            trip.setCategory(tripDTO.getCategory());
            trip.setPrice(tripDTO.getPrice());
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, integer);
            if (trip == null) {
                return null;
            }
            trip.removeGuide();
            em.remove(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public void addGuideToTrip(int tripId, int guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripId);
            Guide guide = em.find(Guide.class, guideId);
            TypedQuery<Trip> query = em.createQuery("SELECT d FROM Trip d WHERE d.guide.id = :guideId AND d.id = :tripId", Trip.class);
            query.setParameter("guideId", guideId);
            query.setParameter("tripId", tripId);
            if (query.getResultList().isEmpty()) {
                trip.setGuide(guide);
                em.merge(trip);
            }
            em.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<TripDTO> getTripsByGuide(int guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Trip> query = em.createQuery("SELECT d FROM Trip d WHERE d.guide.id = :guideId", Trip.class);
            query.setParameter("guideId", guideId);
            List<Trip> trips = query.getResultList();
            if (trips.isEmpty()) {
                return null;
            }
            else {
                return trips.stream()
                        .map(TripDTO::new)
                        .collect(Collectors.toSet());
            }
        }
    }

    @Override
    public Set<TripDTO> getTripsByCategory(Category category) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Trip> query = em.createQuery("SELECT d FROM Trip d WHERE d.category = :category", Trip.class);
            query.setParameter("category", category);
            List<Trip> trips = query.getResultList();
            if (trips.isEmpty()) {
                return null;
            }
            else {
                return trips.stream()
                        .map(TripDTO::new)
                        .collect(Collectors.toSet());
            }
        }
    }

    @Override
    public Map<Integer, Float> getPriceByGuide() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Trip> query = em.createQuery("SELECT d FROM Trip d", Trip.class);
            List<Trip> trips = query.getResultList();
            if (trips.isEmpty()) {
                return null;
            }
            else {
                return trips.stream()
                        .collect(Collectors.toMap(Trip::getId, Trip::getPrice));
            }
        }
    }
}
