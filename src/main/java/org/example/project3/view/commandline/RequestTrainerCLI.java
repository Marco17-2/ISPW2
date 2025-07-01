package org.example.project3.view.commandline;


import org.example.project3.beans.TrainerBean;
import org.example.project3.beans.ReservationBean;
import org.example.project3.beans.LoggedUserBean;
import org.example.project3.controller.ReservationListController;
import org.example.project3.controller.ReservationReqApplicationController;
import org.example.project3.model.Reservation;
import org.example.project3.patterns.observer.Observer;
import org.example.project3.patterns.observer.ReservationManagerConcreteSubject;
import org.example.project3.patterns.state.AbstractState;
import org.example.project3.patterns.state.InitialState;
import org.example.project3.patterns.state.StateMachineConcrete;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


public class RequestTrainerCLI extends AbstractState implements Observer {

    private final List<ReservationBean> reservations = new ArrayList<>();
    protected ReservationListController reservationListController = new ReservationListController();
    protected ReservationReqApplicationController reservationReqApplicationController = new ReservationReqApplicationController();
    private final ReservationManagerConcreteSubject reservationManagerConcreteSubject = ReservationManagerConcreteSubject.getInstance();

    protected LoggedUserBean user;
    private final Scanner scanner = new Scanner(System.in);

    public RequestTrainerCLI(LoggedUserBean user){
        this.user = user;
        this.reservationManagerConcreteSubject.addObserver(this);
    }

    @Override
    public void action(StateMachineConcrete context){
        boolean running = true;
        while(running){
            try{
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch(choice){
                    case 1 -> displayReservation();
                    case 2 -> handleReservation(true);
                    case 3 -> handleReservation(false);
                    case 4 -> viewDetail();
                    case 5 -> {
                        goBack(context);
                        running = false;
                    }
                }

            }catch(Exception e){
                System.out.println("Errore durante l'elaborazione");
            }
        }
    }


    private void handleReservation(boolean accept){
        if(reservations.isEmpty()){
            System.out.println("Richieste non trovate");
            return;
        }

        displayReservation();
        int reservationIndex =  getReservationIndex();
        if(reservationIndex >= 0 && reservationIndex < reservations.size()){
            ReservationBean reservation = reservations.get(reservationIndex);
            processReservation(reservation, accept);
            reservations.remove(reservationIndex);
            loadReservation();
        }
    }

    private void processReservation(ReservationBean reservation, boolean accept){
        reservationReqApplicationController.deleteReservationReq(reservation);
        if(accept){
            reservationReqApplicationController.addReservation(reservation);
            System.out.println("Richiesta accettata prenotazione registrata");
        }else{
            System.out.println("Richiesta rifiutata");
        }

    }


    private int getReservationIndex(){
        int reservationIndex = -1;
        boolean valid = false;
        while(!valid){
            try{
                System.out.println("Errore durante l'elaborazione");
                reservationIndex = Integer.parseInt(scanner.nextLine().trim()) -1;
                if(reservationIndex < 0 || reservationIndex >= reservations.size()){
                    System.out.println("Errore nella scelta");
                }else {
                    valid = true;
                }
            }catch(NumberFormatException e){
                System.out.println("Errore nella scelta");
            }
        }

        return reservationIndex;
    }

    private void displayReservation(){
        if(!reservations.isEmpty()){
            System.out.println("Non sono presneti richieste");
        }else{
            System.out.println("\n ----------------- Lista di Richieste -----------------\n");
            int index = 1;
            for ( ReservationBean reservation : reservations){
                System.out.println(index + "Richiesta da: " + reservation.getCustomer().getName() + " | "
                + "Corso: " + reservation.getCourse().getCourseName() + " | " + reservation.getDay() + " | " + reservation.getHour());
                index++;
            }
        }
    }


    public void loadReservation(){
        List<ReservationBean> reservationsReq = new ArrayList<>();
        reservationListController.getReservationReq((TrainerBean)user, reservationsReq);

        if(reservationsReq.isEmpty()){
            System.out.println("Richieste non presenti");
            return;
        }
        reservations.clear();
        reservations.addAll(reservationsReq);
    }


    private void showDetail(ReservationBean reservation){

        System.out.println("-------------------Customer-------------------");
        System.out.println("Name: " + reservation.getCustomer().getName());
        System.out.println("Surname: " + reservation.getCustomer().getSurname());
        System.out.println("Mail" + reservation.getCustomer().getCredentialsBean().getMail());
        System.out.println("gender: " + reservation.getCustomer().getGender());
        System.out.println("Injury " + reservation.getCustomer().getInjury());

    }

    private void viewDetail(){

        if(reservations.isEmpty()){
            System.out.println("Richieste non trovate");
            return;
        }

        displayReservation();
        int reservationIndex =  getReservationIndex();
        if(reservationIndex >= 0 && reservationIndex < reservations.size()){
            ReservationBean reservation = reservations.get(reservationIndex);
            showDetail(reservation);
        }
    }


    @Override
    public void showMenu(){
        System.out.println("1.Visualizza richieste");
        System.out.println("2.Accetta Richiesta");
        System.out.println("3.Rifiuta richiesta");
        System.out.println("4.Visualizza dettagli richiesta");
        System.out.println("5.Indietro");
        System.out.println("Opzione scelta");

    }

    @Override
    public void stampa(){
        System.out.println(" ");
        System.out.println("----------------------Richieste-----------------------");

    }

    @Override
    public void enter(StateMachineConcrete context){
        loadReservation();
        stampa();
        showMenu();
    }

    //pattern observer
    @Override
    public void update(){
        loadReservation();
        if(reservations.isEmpty()){
            System.out.println("Richieste non trovate");
        }else{
            System.out.println("Aggiornamento Richieste");
        }
    }

}
