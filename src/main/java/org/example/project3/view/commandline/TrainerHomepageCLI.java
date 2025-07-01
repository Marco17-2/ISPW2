package org.example.project3.view.commandline;


import org.example.project3.beans.TrainerBean;
import org.example.project3.beans.LoggedUserBean;
import org.example.project3.patterns.state.AbstractState;
import org.example.project3.patterns.state.InitialState;
import org.example.project3.patterns.state.StateMachineConcrete;

import java.util.Scanner;


public class TrainerHomepageCLI extends AbstractState{

    protected LoggedUserBean user;
    private final Scanner scanner = new Scanner(System.in);
    public TrainerHomepageCLI(LoggedUserBean user) {
        this.user = user;
    }


    @Override
    public void action(StateMachineConcrete context){
        boolean exit=false;
        while(!exit){
            try{
                int choice=Integer.parseInt(scanner.nextLine());
                switch(choice){
                    case 1:
                        goNext(context, new RequestTrainerCLI((TrainerBean) user));
                    case 0:
                        exit=true;
                        goNext(context, new InitialState());
                }
            }catch(Exception e){
                System.out.println("Scelta non valida!");
                scanner.nextLine();
            }
        }
    }


    @Override
    public void showMenu(){
        System.out.println("1. Visualizza Richieste");
        System.out.println("0. Logout");

    }

    @Override
    public void stampa(){
        System.out.println(" ");
        System.out.println("-------------------Benvenuto nella home del trainer-------------------");
    }

    @Override
    public void enter(StateMachineConcrete context){
        stampa();
        showMenu();
    }
}
