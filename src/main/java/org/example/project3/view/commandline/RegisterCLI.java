package org.example.project3.view.commandline;

import javafx.scene.control.Label;
import org.example.project3.beans.CredentialsBean;
import org.example.project3.beans.CustomerBean;
import org.example.project3.beans.TrainerBean;
import org.example.project3.controller.LoginController;
import org.example.project3.controller.UserRegistrationController;
import org.example.project3.exceptions.MailAlreadyExistsException;
import org.example.project3.patterns.state.AbstractState;
import org.example.project3.patterns.state.StateMachineConcrete;
import org.example.project3.utilities.enums.Role;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RegisterCLI extends AbstractState {



    private final Scanner scanner = new Scanner(System.in);
    private final LoginController login = new LoginController();
    private final UserRegistrationController controller = new UserRegistrationController();

    @Override
    public void action(StateMachineConcrete context) {
        //metodo effettivo per la registrazione
        try {
            //Chiedo informazioni
            String nome=prompt("Nome: ");
            String surname=prompt("Cognome: ");
            String gender=prompt("Genere: ");
            String email=prompt("Email: ");
            if (isValidMail(email, null)) {
                System.out.println("Email non valida");
                return;
            }
            String password=prompt("Password: ");
            LocalDate selectedDate = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");

            while (selectedDate == null) {
                System.out.print("Inserisci una data (formato: gg/mm/aaaa): ");
                String input = scanner.nextLine();

                try {
                    selectedDate = LocalDate.parse(input, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Data non valida. Riprova.");
                    return;
                }
            }
            String role=prompt("Ruolo (trainer/cliente): ").toLowerCase();
            //Creazione oggetti e registrazione utente
            switch (role){
                case "trainer":
                    TrainerBean trainerBean = new TrainerBean(new CredentialsBean(email,password, Role.TRAINER),nome,surname,gender,true,selectedDate);
                    controller.registerTrainer(trainerBean);
                    System.out.println("Registrazione avvenuta con successo");
                    login.login(trainerBean.getCredentialsBean());
//                    goNext(context,new TrainerHomepageCLI(trainerBean));
                    break;
                case "cliente":
                    CustomerBean customerBean = new CustomerBean(new CredentialsBean(email,password,Role.CLIENT),nome,surname,gender,true,selectedDate);
                    controller.registerCustomer(customerBean);
                    System.out.println("Registrazione avvenuta con successo");
                    login.login(customerBean.getCredentialsBean());
                    goNext(context,new CustomerHomepageCLI(customerBean));
                    break;
                default:
                    System.out.println("Ruolo non valido. Scegli 'trainer' o 'cliente'");
            }
        } catch (MailAlreadyExistsException e) {
            System.out.println("Errore: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Errore durante la registrazione. Riprova più tardi.");
        }
    }
    //metodo per chiedere l'input
    private String prompt(String message) {
        System.out.print(message);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("Inserire un valore valido");
            return prompt(message);
        }
        return input;
    }

    public static boolean isValidMail(String mail, Label errorMessage) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!mail.matches(emailRegex)) {
            errorMessage.setText("Mail non valida");
            errorMessage.setVisible(true);
            return true;
        }
        return false;
    }

    /*Pattern state*/

    @Override
    public void enter(StateMachineConcrete context) {
        stampa();
    }


    @Override
    public void stampa() {
        System.out.println("-------------- Registrazione --------------");
        System.out.println("Inserite le informazioni necessarie per la registrazione.");
    }

    @Override
    public void showMenu() {
        System.out.println("1. Conferma"); //in questo caso facciamo il login in automatico
        System.out.println("2. Indietro");
        System.out.println("0. Esci");
        System.out.println("Opzione scelta: ");
    }
}
