package org.example.project3.patterns.state;

import org.example.project3.view.commandline.LoginCLI;
import org.example.project3.view.commandline.RegisterCLI;

import java.util.Scanner;

public class  InitialState extends AbstractState{
    public InitialState() {super();}
    @Override
    public void action(StateMachineConcrete context){
        AbstractState nextState;
        Scanner scanner = new Scanner(System.in);
        int choice;
        while(context.getCurrentState()!=null){
            try{
                this.stampa();
                this.showMenu();
                choice = scanner.nextInt();
                switch(choice){
                    case 1:
                        context.setState();
                        System.exit(0);
                        return;
                    case 2:
                        nextState = new LoginCLI();
                        goNext(context,nextState);
                        break;
                    case 3:
                        nextState = new RegisterCLI();
                        goNext(context,nextState);
                        break;
                    default:
                        System.out.println("Opzione non valida!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Errore nella scelta!");
                scanner.nextLine();
            }
        }
    }

    @Override
    public void showMenu() {
        System.out.println("1) Esci");
        System.out.println("2) Login");
        System.out.println("3) Registrati");
        System.out.print("Scelta: ");
    }
    @Override
    public void stampa() {
        System.out.println("");
        System.out.println("--------------------Benvenuto in Bodybuild--------------------");
        System.out.println("---------necessario effettuare il login o registrarsi---------");
    }
    @Override
    public void exit(StateMachineConcrete stateMachine){
        System.out.println("Uscita dallo stato iniziale");
    }
}
