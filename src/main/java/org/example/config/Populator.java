package org.example.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Guide;
import org.example.entities.Trip;
import org.example.enums.Category;

import java.time.LocalDate;

public class Populator {

    static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public static void main(String[] args) {

        Guide guide1 = new Guide("John", "Doe", "john.doe@example.com", "1234567890", 5);
        Guide guide2 = new Guide("Jane", "Smith", "jane.smith@example.com", "0987654321", 3);
        Guide guide3 = new Guide("Jack", "Brown", "jack.brown@example.com", "6789054321", 4);
        Trip trip1 = new Trip(LocalDate.of(2024, 1,12), LocalDate.of(2024, 1,19), 75.34f, 12.43f, "Mountain Adventure", 200.0f, Category.sea);
        Trip trip2 = new Trip(LocalDate.of(2024, 2, 5), LocalDate.of(2024, 2, 12), 80.0f, 15.0f, "City Tour", 100.0f, Category.city);
        Trip trip3 = new Trip(LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 17), 90.0f, 20.0f, "Desert Safari", 300.0f, Category.beach);
        Trip trip4 = new Trip(LocalDate.of(2024, 4, 15), LocalDate.of(2024, 4, 22), 85.0f, 18.0f, "Beach Relaxation", 150.0f, Category.beach);
        Trip trip5 = new Trip(LocalDate.of(2024, 5, 20), LocalDate.of(2024, 5, 27), 95.0f, 25.0f, "Forest Expedition", 250.0f, Category.forest);
        /*Trip trip6 = new Trip(LocalDate.of(2024, 6, 25), LocalDate.of(2024, 7, 2), 100.0f, 30.0f, "River Rafting", 350.0f, Category.lake);*/

        trip1.setGuide(guide1);
        trip2.setGuide(guide2);
        trip3.setGuide(guide3);
        trip4.setGuide(guide1);
        /*trip4.setGuide(guide1);
        trip5.setGuide(guide2);*/

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.persist(guide1);
            em.persist(guide2);
            em.persist(guide3);

            em.persist(trip1);
            em.persist(trip2);
            em.persist(trip3);
            em.persist(trip4);
            em.persist(trip5);
            /*em.persist(trip6);*/

            em.getTransaction().commit();
        }

    }
}
