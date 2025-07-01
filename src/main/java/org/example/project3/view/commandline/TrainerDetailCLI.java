package org.example.project3.view.commandline;

import org.example.project3.beans.*;
import org.example.project3.controller.TrainerDescriptionController;
import org.example.project3.patterns.state.AbstractState;
import org.example.project3.patterns.state.StateMachineConcrete;


import java.util.Scanner;
import java.util.List;

public class TrainerDetailCLI extends AbstractState  {

    protected CustomerBean user;
    protected CourseBean selectedCourse;

    protected TrainerDescriptionController trainerDescriptionController= new TrainerDescriptionController();

    Scanner scanner = new Scanner(System.in);

    public TrainerDetailCLI(CustomerBean user, CourseBean selectedBean) {
        this.user = user;
        this.selectedCourse = selectedBean;
    }

    @Override
    public void action(StateMachineConcrete context){

        TrainerBean trainerBean = trainerDescriptionController.trainerDescription(selectedCourse);

        if(trainerBean == null){
            System.out.println("Trainer non trovato");
            goBack(context);
        }

        System.out.println("-------------------Trainer-------------------");
        System.out.println("Name: " + trainerBean.getName());
        System.out.println("Surname: " + trainerBean.getSurname());
        System.out.println("Mail" + trainerBean.getCredentialsBean().getMail());
        System.out.println("gender: " + trainerBean.getGender());

        List<String> specializations = trainerBean.getSpecializations();

        System.out.println("Specializations: ");

        for (String spec : specializations  ) {
            System.out.println(spec);
        }

        int scelta;
        while((scelta = Integer.parseInt(scanner.nextLine())) != 0){
            try{

                switch(scelta){
                    case 1 -> goBack(context);
                    default ->  System.out.println("errore di selezione");
                }

            }catch (Exception e){
                System.out.println("Scelta non valida");
            }
        }
    }


    @Override
    public void showMenu() {
        System.out.println("1.Torna indietro");
        System.out.println("Opzione scelta:");
    }


    @Override
    public void stampa() {
        System.out.println(" ");
        System.out.println("-------------------Trainer-------------------");
        System.out.println("Queste sono le informazioni del trainer");
    }


    @Override
    public void enter(StateMachineConcrete context) {
        stampa();
        showMenu();
    }
}
