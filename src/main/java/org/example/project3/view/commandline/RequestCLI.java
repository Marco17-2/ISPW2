package org.example.project3.view.commandline;

import org.example.project3.beans.*;
import org.example.project3.controller.RequestModifyController;
import org.example.project3.controller.ScheduleDetailsController;
import org.example.project3.patterns.observer.Observer;
import org.example.project3.patterns.state.AbstractState;
import org.example.project3.patterns.state.InitialState;
import org.example.project3.patterns.state.StateMachineConcrete;
import org.example.project3.utilities.others.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RequestCLI extends AbstractState implements Observer {

    protected ScheduleDetailsController scheduleDetailsController = new ScheduleDetailsController();
    List<ScheduleBean> scheduleBeans=new ArrayList<>();
    List<ExerciseBean> exerciseBeans=new ArrayList<>();
    RequestBean requestBean;
    protected RequestModifyController requestModifyController = new RequestModifyController();
    protected LoggedUserBean user;
    public RequestCLI(LoggedUserBean user) {
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
                    case 1: {
                        stampa();
                        if (displaySchedules(requestBean)) {
                            loadExercises(requestBean);
                            if (displayExercises(requestBean)) {
                                Printer.println("Specifica la motivazione della modifica:");
                                String motivazione=scanner.nextLine();
                                requestBean.setReason(motivazione);
                                requestModifyController.sendRequest(requestBean);
                                update();
                                goNext(context, new CustomerHomepageCLI( user ));
                            } else {
                                Printer.errorPrint("L'esercizio inserito non è presente!");
                            }
                        } else {
                            Printer.errorPrint("È state inserito un ID non corretto!");
                        }
                    }
                    case 2:
                        goNext(context, new CustomerHomepageCLI( user ));
                    case 0:
                        exit=true;
                        goNext(context, new InitialState());
                }
            }catch(Exception e){
                Printer.errorPrint("Errore durante la richiesta. Riprova più tardi.");
                scanner.nextLine();
            }
        }
    }

    private void loadSchedules(){
        scheduleBeans.clear();
        scheduleDetailsController.retriveScheduleDetails((CustomerBean) user,scheduleBeans);
    }

    private boolean displaySchedules(RequestBean requestBean){
        if(scheduleBeans.isEmpty()){
            Printer.errorPrint("Non è stata trovata nessuna scheda!");
            return false;
        }else{
            Printer.println("--------------------Lista delle schede--------------------\n");
            Printer.println("(1)ID | (2)Nome | (3)Utente | (4)Trainer\n");
            for(ScheduleBean scheduleBean : scheduleBeans){
                Printer.println(scheduleBean.getId()+" | "+scheduleBean.getName()+" | "+scheduleBean.getCustomerBean().getCredentialsBean().getMail()+" | "+scheduleBean.getTrainerBean().getCredentialsBean().getMail());
            }
            Printer.println("Inserici l'ID della scheda che vuoi modificare");
            Long id=Long.parseLong(scanner.nextLine());
            for(ScheduleBean scheduleBean : scheduleBeans){
                if((id.equals(scheduleBean.getId()))&&(!requestModifyController.hasAlreadySentARequest(scheduleBean))){
                    this.requestBean= new RequestBean(scheduleBean);
                    return true;
                }
            }
            return false;
        }
    }

    private void loadExercises(RequestBean requestBean){
        exerciseBeans.clear();
        scheduleDetailsController.retriveExercises(requestBean.getScheduleBean());
        exerciseBeans.addAll(requestBean.getScheduleBean().getExercisesBean());
    }

    private boolean displayExercises(RequestBean requestBean){
        if(exerciseBeans.isEmpty()){
            Printer.errorPrint("Non è stata trovato nessun esercizio!");
            return false;
        }else{
            Printer.println("--------------------Lista degli esercizi della scheda "+requestBean.getScheduleBean().getName()+"--------------------\n");
            Printer.println("(1)Nome | (2)Descrizione | (3)Numero di serie | (4)Numero di ripetizioni | (5)Tempo di recupero\n");
            for(ExerciseBean exerciseBean : exerciseBeans){
                Printer.println(exerciseBean.getName()+" | "+exerciseBean.getDescription()+" | "+exerciseBean.getNumberSeries()+" | "+exerciseBean.getNumberReps()+" | "+exerciseBean.getRestTime());
            }
            Printer.println("Inserici il nome dell'esercizio che vuoi modificare");
            String name=scanner.nextLine();
            for(ExerciseBean exerciseBean : exerciseBeans){
                if(name.toLowerCase().equals(exerciseBean.getName().toLowerCase())){
                    requestBean.setExercise(exerciseBean);
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public void showMenu() {
        Printer.println("1. Scegli scheda"); //in questo caso facciamo il login in automatico
        Printer.println("2. Indietro");
        Printer.println("0. Esci");
        Printer.print("Opzione scelta: ");
    }

    @Override
    public void stampa(){
        Printer.println(" ");
        Printer.println("-------------------Modifica scheda-------------------");
        Printer.println("Ciao"+ " " + user.getName()+ ",scegli quale scheda modificare:");
    }

    @Override
    public void enter(StateMachineConcrete context){
        loadSchedules();
        showMenu();
        action(context);
    }

    @Override
    public void update(){
        Printer.println("Richiesta inviata con successo");
    }
}
