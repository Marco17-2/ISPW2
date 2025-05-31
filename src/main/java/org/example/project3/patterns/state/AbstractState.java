package org.example.project3.patterns.state;

public abstract class AbstractState {

    public static final String SCELTA_NON_VALIDA = "Scelta non valida";
    protected AbstractState(){};

    public void enter(StateMachineConcrete context){}
    public void exit(StateMachineConcrete context){}
    public void goBack(StateMachineConcrete context){}
    public void goNext(StateMachineConcrete context, AbstractState nextState){}

    public void action(StateMachineConcrete context){}
    public void showMenu(){}
    public void stampa(){}

}
