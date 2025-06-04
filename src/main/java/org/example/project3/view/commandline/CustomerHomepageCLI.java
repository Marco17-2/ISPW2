package org.example.project3.view.commandline;

import org.example.project3.beans.CustomerBean;
import org.example.project3.beans.LoggedUserBean;
import org.example.project3.patterns.state.AbstractState;
import org.example.project3.patterns.state.InitialState;
import org.example.project3.patterns.state.StateMachineConcrete;

import java.util.Scanner;

public class CustomerHomepageCLI extends AbstractState {

    protected LoggedUserBean user;
    public CustomerHomepageCLI(LoggedUserBean user) {
        this.user = user;
    }
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void action(StateMachineConcrete context){
        boolean exit=false;
        while(!exit){
            try{
                int choice=Integer.parseInt(scanner.nextLine());
                switch(choice){
                    case 1:
                        goNext(context, new );
                    case 2:
                        goNext(context, new RequestCLI((CustomerBean) user ));
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
        System.out.println("1. Prenotati per un corso");
        System.out.println("2. Richiedi la modifica di una scheda");
//        System.out.println("3. Vedi progressi");
//        System.out.println("4. Modifica prenotazione");
//        System.out.println("5. Richiedi una scheda");
//        System.out.println("6. Vedi schede");
        System.out.println("0. Logout");
        System.out.println("Opzione scelta:");
    }

    @Override
    public void stampa(){
        System.out.println(" ");
        System.out.println("-------------------Benvenuto nella home di"+ " " + user.getName()+ " "+ user.getSurname() +"-------------------");
        System.out.println("Ciao"+ " " + user.getName()+ " "+ user.getSurname() +",scegli cosa vuoi fare:");
    }

    @Override
    public void enter(StateMachineConcrete context) {
        stampa();
        showMenu();
    }
}
