package test;

import org.example.project3.dao.*;
import org.example.project3.exceptions.LoginAndRegistrationException;
import org.example.project3.exceptions.MailAlreadyExistsException;
import org.example.project3.exceptions.WrongEmailOrPasswordException;
import org.example.project3.model.*;
import org.example.project3.patterns.factory.FactoryDAO;
import org.example.project3.utilities.enums.RestTime;
import org.example.project3.utilities.enums.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

class TestRequest {
    //Adamo Luca,matricola 0307348



    //Parametri il test di alcuni metodi
    private String passwordCustomer;
    private String passwordTrainer;
    private String testEmailCustomer;
    private String testEmailTrainer;
    private final LocalDate testBirthday = LocalDate.now();

    private static final String TEST_NAME = "TestName";
    private static final String TEST_SURNAME = "TestSurname";
    private static final String TEST_GENDER = "Gender";
    private static final boolean TEST_ONLINE = true;

    //DAO
    private CustomerDAO customerDAO;
    private CredentialsDAO credentialsDAO;
    private ExerciseDAO exerciseDAO;
    private ScheduleDAO scheduleDAO;
    private TrainerDAO trainerDAO;
    private RequestDAO requestDAO;

    //Cliente di test
    private Customer testCustomer;
    private Trainer testTrainer;
    private List<Exercise> testExercises;
    private List<Schedule> testSchedules;
    private Request testRequest;

    //Azione che viene svolta prima di ogni test
    @BeforeEach
    void setUp() throws  LoginAndRegistrationException {
        // genero una password
        passwordCustomer = generatePassword();
        passwordTrainer = generatePassword();
        //test email
        testEmailCustomer=generateTestEmail();
        testEmailTrainer=generateTestEmail();

        // Inizializziamo i DAO una sola volta per evitare chiamate ripetute alla Factory
        customerDAO = FactoryDAO.getCustomerDAO();
        credentialsDAO = FactoryDAO.getDAO();
        exerciseDAO = FactoryDAO.getExerciseDAO();
        scheduleDAO = FactoryDAO.getScheduleDAO();
        trainerDAO = FactoryDAO.getTrainerDAO();
        requestDAO = FactoryDAO.getRequestDAO();
        // Creo un paziente di test
        testCustomer = createCustomer();
        testTrainer = createTrainer();
        testExercises = createExercises();
        testSchedules = createSchedules();
        testRequest= createRequest();
        // Registriamo il paziente
        registerCustomer();
        registerTrainer();
    }

        // Azione che viene svolta dopo ogni test
        @AfterEach
        void tearDown() {
            if(testCustomer!=null){
                requestDAO.deleteRequest(testRequest);
                for(Exercise exercise:testExercises){
                    exerciseDAO.deleteExercise(exercise);
                }
                for(Schedule s: testSchedules){
                    scheduleDAO.deleteSchedule(s);
                }
                customerDAO.removeCustomer(testCustomer);
                trainerDAO.removeTrainer(testTrainer);
            }

        }

    // test per assicurarmi che un paziente dopo essersi registrato può svolgere il login
    @Test
    void testCredential() {
        try{
            Credentials credentials = new Credentials(testEmailCustomer, passwordCustomer, Role.CLIENT);
            // Effettuiamo il login
            credentialsDAO.login(credentials);
        } catch (WrongEmailOrPasswordException | LoginAndRegistrationException e) {
            // Se il login fallisce, il test fallisce
            Assertions.fail("Errore: " + e.getMessage());
        }

    }

    //Test per il corretto recupero delle schede
    @Test
    void testSchedules(){

        registerSchedules();
        registerExercises();
        List<Schedule> schedules = new ArrayList<>();
        scheduleDAO.retrieveSchedule(testCustomer,schedules);

        Assertions.assertEquals(schedules, testSchedules,
                "Il contenuto delle schede recuperate non corrisponde a quello previsto.");
    }

    //Test per la corretta registrazione della richiesta
    @Test
    void testRequest(){

        registerSchedules();
        registerExercises();
            requestDAO.sendRequest(testRequest);

            Assertions.assertTrue(requestDAO.hasAlreadySentRequest(testRequest),
                    "La richiesta non è stata registrata correttamente.");
    }

    private Request createRequest() {
        return new Request(10, testSchedules.get(0),testExercises.get(0),"i don't like", LocalDateTime.now());

    }

    private List<Schedule> createSchedules(){
        List<Exercise> exercisesForScheduleA = new ArrayList<>();
        exercisesForScheduleA.add(testExercises.get(0));
        exercisesForScheduleA.add(testExercises.get(1));
        Schedule scheduleA = new Schedule(70,"Beginner Full Body",testCustomer,testTrainer,exercisesForScheduleA);
        List<Exercise> exercisesForScheduleB = new ArrayList<>();
        exercisesForScheduleB.add(testExercises.get(2));
        Schedule scheduleB = new Schedule(80,"Cardio Focus",testCustomer,testTrainer,exercisesForScheduleB);
        List<Exercise> exercisesForScheduleC = new ArrayList<>();
        exercisesForScheduleC.add(testExercises.get(0));
        exercisesForScheduleC.add(testExercises.get(1));
        exercisesForScheduleC.add(testExercises.get(2));
        Schedule scheduleC = new Schedule(90,"Advanced Strength",testCustomer,testTrainer,exercisesForScheduleC);
        return Arrays.asList(scheduleA,scheduleB,scheduleC);

    }

    private List<Exercise> createExercises(){
        Exercise exercise1 = new Exercise(60,"Push-ups","Standard push-up exercise.",3,10, RestTime.SECONDS60);
        Exercise exercise2 = new Exercise(61,"Squats","Bodyweight squats.",3,10, RestTime.SECONDS60);
        Exercise exercise3 = new Exercise(62,"Plank","Core plank hold.",3,10, RestTime.SECONDS60);
        return Arrays.asList(exercise1,exercise2,exercise3);
    }

    //creo un cliente
    private Customer createCustomer() {
        return new Customer(
                new Credentials(testEmailCustomer, passwordCustomer, Role.CLIENT),
                TEST_NAME,
                TEST_SURNAME,
                TEST_GENDER,
                TEST_ONLINE,
                testBirthday
        );
    }

    private Trainer createTrainer() {
        return new Trainer(
                new Credentials(testEmailTrainer, passwordTrainer, Role.TRAINER),
                TEST_NAME,
                TEST_SURNAME,
                TEST_GENDER,
                TEST_ONLINE,
                testBirthday
        );
    }
    //genero una password
    private String generatePassword() {
        return "test" + System.currentTimeMillis();
    }
    //genero un email di test
    private String generateTestEmail() {
        return "t_" + UUID.randomUUID().toString() + "@ex.com";
    }
    //registro il paziente
    private void registerCustomer() throws LoginAndRegistrationException {
        try {
            customerDAO.registerCustomer(testCustomer);
        } catch (MailAlreadyExistsException e) {
            throw new LoginAndRegistrationException("Errore nella registrazione del cliente", e);
        }
    }

    private void registerTrainer() throws LoginAndRegistrationException {
        try {
            trainerDAO.registerTrainer(testTrainer);
        } catch (MailAlreadyExistsException e) {
            throw new LoginAndRegistrationException("Errore nella registrazione del trainer"+e.getMessage(), e);
        }
    }

    private void registerExercises(){
        try {
            exerciseDAO.addExercise(testExercises.get(0));
            exerciseDAO.addExercise(testExercises.get(1));
            exerciseDAO.addExercise(testExercises.get(2));
            exerciseDAO.addExerciseSchedule(testSchedules.get(0),testExercises.get(0));
            exerciseDAO.addExerciseSchedule(testSchedules.get(0),testExercises.get(1));
            exerciseDAO.addExerciseSchedule(testSchedules.get(1),testExercises.get(2));
            exerciseDAO.addExerciseSchedule(testSchedules.get(2),testExercises.get(0));
            exerciseDAO.addExerciseSchedule(testSchedules.get(2),testExercises.get(1));
            exerciseDAO.addExerciseSchedule(testSchedules.get(2),testExercises.get(2));
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void registerSchedules() {
        try {
            scheduleDAO.addSchedule(testSchedules.get(0));
            scheduleDAO.addSchedule(testSchedules.get(1));
            scheduleDAO.addSchedule(testSchedules.get(2));
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
