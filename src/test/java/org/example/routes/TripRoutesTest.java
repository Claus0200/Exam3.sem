package org.example.routes;

import io.javalin.Javalin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.config.ApplicationConfig;
import org.example.config.HibernateConfig;
import org.example.config.Populator;
import org.example.dtos.TripDTO;
import org.example.enums.Category;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class TripRoutesTest {
    static final String BASE_URL = "http://localhost:7070/api/trips";
    static Javalin app;
    static EntityManagerFactory emf;

    @BeforeAll
    static void setUpAll() {
        app = ApplicationConfig.startServer(7070);
        emf = HibernateConfig.getEntityManagerFactory();
    }

    @BeforeEach
    void setUp() {
        Populator.main(null);
    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM guide_trip").executeUpdate();
            em.createQuery("DELETE FROM Trip ").executeUpdate();
            em.createQuery("DELETE FROM Guide ").executeUpdate();

            em.createNativeQuery("ALTER SEQUENCE guide_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE trip_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDownAll() {
        ApplicationConfig.stopServer(app);
    }

    @Test
    void testGetAllTrips() {
        given()
                .when()
                .get(BASE_URL + "/")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    void testGetTripById() {
        given()
                .when()
                .get(BASE_URL+ "/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", notNullValue());
    }

    @Test
    void testCreateTrip() {
        TripDTO newTrip = new TripDTO(LocalDate.of(2024, 6, 25), LocalDate.of(2024, 7, 2), 100.0f, 30.0f, "River Rafting", 350.0f, Category.lake);

        given()
                .contentType("application/json")
                .body(newTrip)
                .when()
                .post(BASE_URL + "/")
                .then()
                .statusCode(201)
                .body("name", equalTo("River Rafting"));
    }

    @Test
    void testUpdateTrip() {
        TripDTO updatedTrip = new TripDTO(LocalDate.of(2024, 6, 25), LocalDate.of(2024, 7, 2), 100.0f, 30.0f, "Updated Trip", 350.0f, Category.lake);

        given()
                .contentType("application/json")
                .body(updatedTrip)
                .when()
                .put(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Trip"));
    }

    @Test
    void testDeleteTrip() {
        given()
                .when()
                .delete(BASE_URL + "/1")
                .then()
                .statusCode(204);
    }

}