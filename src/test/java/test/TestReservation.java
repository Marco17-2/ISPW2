package test;

import org.example.project3.dao.*;

import org.example.project3.exceptions.LoginAndRegistrationException;
import org.example.project3.exceptions.MailAlreadyExistsException;
import org.example.project3.utilities.enums.Role;

import org.example.project3.model.Customer;
import org.example.project3.model.Reservation;
import org.example.project3.model.Trainer;
import org.example.project3.model.Course;

import org.example.project3.model.Credentials;

import org.example.project3.patterns.factory.FactoryDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


class TestReservation {

    private String passwordT;
    private String passwordC;
    private String testTrainerEmail;
    private String testCustomerEmail;
    private String uniqueDay;
    private String uniqueHour;
    private List<Reservation> testReservations;

    //COURSE
    private static final String TEST_NAME = "test name";
    private static final String TEST_DURATION = "test duration";
    private static final int TEST_REMAINING = 1;
    private static final String TEST_LEVEL = "test level";

    //TRAINER
    private static final String TEST_TNAME = "Test Name";
    private static final String TEST_SURNAME = "Test Surname";
    private static final String TEST_GENDER = "Gender";
    private static final boolean TEST_ONLINE = false;
    private static final LocalDate TBDAY  = LocalDate.now();

    //DAO
    private ReservationDAO reservationDAO;
    private CustomerDAO customerDAO;
    private RequestDAO requestDAO;
    private CourseDAO courseDAO;
    private TrainerDAO trainerDAO;

    private Course testCourse;
    private Course testtCourse;
    private Trainer testTrainer;
    private Customer testCustomer;
    private Reservation testReservationReq;


    @BeforeEach
    void setUp() throws LoginAndRegistrationException {

        reservationDAO = FactoryDAO.getReservationDAO();
        requestDAO = FactoryDAO.getRequestDAO();
        customerDAO = FactoryDAO.getCustomerDAO();
        courseDAO = FactoryDAO.getCourseDAO();
        trainerDAO = FactoryDAO.getTrainerDAO();

        testTrainerEmail = generateTestEmail();
        passwordT = generatePassword();
        testCustomerEmail = generateTestEmail();
        passwordC = generatePassword();
        uniqueDay = createDay();
        uniqueHour = createHour();

        testCourse = createCourse();
        testTrainer = createTrainer();
        testCustomer = createCustomer();

        registerTrainer();
        registerCustomer();
        registerCourse();
        loadCourse();

        testReservationReq = createReservation();
        registerReservationReq();
    }

    @AfterEach
    void tearDown(){
        try {
            // Rimuove la reservation
            if (testCustomer != null && testCourse != null) {
                requestDAO.removeCourseRequest(testReservationReq);
            }

            if (testReservations != null) {
                for (Reservation res : testReservations) {
                        reservationDAO.removeReservation(res);
                }
            }

            // Rimuove il corso
            if (testtCourse != null) {
                courseDAO.removeCourse(testtCourse);
            }

            // Rimuove il cliente
            if (testCustomer != null) {
                customerDAO.removeCustomer(testCustomer);
                trainerDAO.removeTrainer(testTrainer);
            }



        } catch (Exception e) {
            throw new RuntimeException("Errore durante la pulizia dei dati di test", e);
        }
    }

    //test per recupero Corsi
    @Test
    void testRetrieveCourse(){

        List<Course> course = new ArrayList<>();
        courseDAO.searchCourses(course);
        boolean found = course.stream().anyMatch(c ->
                c.getCourseName().equals(TEST_NAME) &&
                        c.getDuration().equals(TEST_DURATION) &&
                        c.getLevel().equals(TEST_LEVEL) &&
                        c.getDay().equals(uniqueDay) &&
                        c.getHour().equals(uniqueHour) &&
                        c.getRemainingSlots() == TEST_REMAINING
        );

        Assertions.assertTrue(found, "Il corso atteso non è stato trovato nella lista.");

    }

    //Test sulla corretta registrazione della richiesta
    @Test
    void testCourseReq(){
        Assertions.assertTrue(requestDAO.alreadyHasReservation(testReservationReq),
                "La richiesta non è stata salvata");
    }

    //Test per il corretto recupero delle prenotazioni
    @Test
    void testRetrieveReservations() throws LoginAndRegistrationException {
        loadRegisterReservations();
        List<Reservation> reservations = new ArrayList<>();
        reservationDAO.retrieveReservation(testCustomer, reservations);

        for (int i = 0; i < reservations.size(); i++) {
            Reservation actual = reservations.get(i);
            Reservation expected = testReservations.get(i);

            Assertions.assertEquals(expected.getCourse().getCourseID(), actual.getCourse().getCourseID(), "Course ID diverso alla posizione " + i);
            Assertions.assertEquals(expected.getHour(), actual.getHour(), "Course Hour diverso alla posizione " + i);
            Assertions.assertEquals(expected.getDay(), actual.getDay(), "Course Day diverso alla posizione " + i);
            Assertions.assertEquals(expected.getCustomer().getCredentials().getMail(), actual.getCustomer().getCredentials().getMail(), "Customer mail diversa alla posizione " + i);
        }
    }



    private void registerTrainer() throws LoginAndRegistrationException{
        try {
            trainerDAO.registerTrainer(testTrainer);
        } catch (MailAlreadyExistsException e) {
            throw new LoginAndRegistrationException("Errore nella registrazione del trainer"+e.getMessage(), e);
        }
    }

    private void registerCustomer() throws LoginAndRegistrationException{
        try {
            customerDAO.registerCustomer(testCustomer);
        } catch (MailAlreadyExistsException e) {
            throw new LoginAndRegistrationException("Errore nella registrazione del cliente", e);
        }
    }

    private Course createCourse(){
        testCourse = new Course(TEST_NAME, TEST_REMAINING, TEST_DURATION, TEST_LEVEL, uniqueDay, uniqueHour);
        return testCourse;
    }

    private Trainer createTrainer(){
        return new Trainer(
                new Credentials(testTrainerEmail, passwordT, Role.TRAINER),
                TEST_TNAME,
                TEST_SURNAME,
                TEST_GENDER,
                TEST_ONLINE,
                TBDAY
        );
    }

    private Customer createCustomer(){
        return new Customer(
                new Credentials(testCustomerEmail, passwordC, Role.CLIENT),
                TEST_TNAME,
                TEST_SURNAME,
                TEST_GENDER,
                TEST_ONLINE,
                TBDAY
        );
    }

    private void registerCourse(){
        try{
            courseDAO.addCourse(testCourse, testTrainerEmail);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private Reservation createReservation(){

        return new Reservation(testCustomer, testtCourse, uniqueDay, uniqueHour);
    }

    private void registerReservationReq(){
        try{
            requestDAO.addCourseRequest(testReservationReq);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private void loadCourse(){

        List<Course> courses = new ArrayList<>();
        courseDAO.searchCourses(courses);

        testtCourse = (Course) courses.stream()
                .filter(c -> c.getCourseName().equals(TEST_NAME) &&
                        c.getDuration().equals(TEST_DURATION) &&
                        c.getLevel().equals(TEST_LEVEL) &&
                        c.getDay().equals(uniqueDay) &&
                        c.getHour().equals(uniqueHour) &&
                        c.getRemainingSlots() == TEST_REMAINING)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Course non trovato"));

    }

    private void loadRegisterReservations() throws LoginAndRegistrationException {

        try{

            testReservations = new ArrayList<>();

            reservationDAO.addReservation(testReservationReq);
            testReservations.add(testReservationReq);

            testTrainerEmail = generateTestEmail();
            passwordT = generatePassword();

            uniqueDay = createDay();
            uniqueHour = createHour();

            testCourse = createCourse();
            testTrainer = createTrainer();

            registerTrainer();
            registerCourse();
            loadCourse();

            Reservation reservationR = new Reservation(testCustomer, testtCourse, uniqueDay, uniqueHour);

            reservationDAO.addReservation(reservationR);
            testReservations.add(reservationR);

        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generatePassword() {
        return "test" + System.currentTimeMillis();
    }
    //genero un email di test
    private String generateTestEmail() {
        return  "t_" + UUID.randomUUID().toString() + "@ex.com";
    }

    private String createDay(){
        return "td_" + System.currentTimeMillis();
    }

    private String createHour(){
        return "th_" + System.currentTimeMillis();
    }
}
