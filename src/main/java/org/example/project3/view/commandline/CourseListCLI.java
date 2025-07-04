package org.example.project3.view.commandline;

import javafx.print.Printer;
import org.example.project3.controller.CourseListController;
import org.example.project3.model.*;
import org.example.project3.beans.*;
import org.example.project3.patterns.state.AbstractState;
import org.example.project3.patterns.state.StateMachineConcrete;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseListCLI extends AbstractState {

    protected CustomerBean user;
    protected CourseListController courseListController = new CourseListController();

    Scanner scanner = new Scanner(System.in);

    public CourseListCLI(CustomerBean user) {
        this.user = user;
    }


    @Override
    public void action(StateMachineConcrete context){
        try{

            List<CourseBean> courses = new ArrayList<>();
            courseListController.retrieveCourses(courses);

            if(courses.isEmpty()){
                System.out.println("Non ci sono corsi");
                goBack(context);
            }else{
                System.out.println("----------------Lista Corsi--------------------");
                for(int i=0; i<courses.size(); i++){
                    System.out.println((i+1) + ". " + courses.get(i).getCourseName() + " " + courses.get(i).getRemainingSlots() + " " + courses.get(i).getSlots() + " " + courses.get(i).getDay() + " " + courses.get(i).getHour() + " " + courses.get(i).getLevel());
                }
                showMenu();
                int scelta = scanner.nextInt();

                System.out.println( "Selezione il corso(Inserici 0 per tornare indietro): ");
                int selectedIndex = scanner.nextInt();

                if(scelta == 1){

                    if(selectedIndex > 0 && selectedIndex < courses.size()){
                        CourseBean selectedCourse = courses.get(selectedIndex - 1);
                        ReservationBean reservationBean = new ReservationBean(user, selectedCourse, selectedCourse.getDay(), selectedCourse.getHour());
                        if(!courseListController.alreadyHasRequest(reservationBean)){
                            courseListController.sendReservationReq(reservationBean);
                            System.out.println( " Richiesta inviata ");
                        }else{
                            System.out.println( " Hai già iniato una richiesta ");
                        }


                        goNext(context,new CourseListCLI(user));

                    }else if( selectedIndex == 0 ){
                        goBack(context);
                    } else{
                        System.out.println("Scelta non valida");
                    }

                }else if(scelta == 2){
                    if(selectedIndex > 0 && selectedIndex < courses.size()){
                        CourseBean selectedCourse = courses.get(selectedIndex - 1);
                        goNext(context, new TrainerDetailCLI(user, selectedCourse));
                    }else if( selectedIndex == 0 ){
                        goBack(context);
                    } else{
                        System.out.println("Scelta non valida");
                    }
                }
            }

        }catch (Exception e){
            System.out.println("Errore durante recupero corsi");
            goBack(context);
        }
    }



    @Override
    public void showMenu() {
        System.out.println("1.Prenota Corso");
        System.out.println("2.Visualizza Dettagli Trainer");
        System.out.println("0.Indietro");
        System.out.println("Scelta:");
    }

    @Override
    public void stampa() {
        System.out.println(" ");
        System.out.println("-------------------Benvenuto pagina prenotazione corsi -------------------");
        System.out.println("Scegli cosa vuoi fare:");
    }

    @Override
    public void enter(StateMachineConcrete context) {
        stampa();
        showMenu();
    }



}
