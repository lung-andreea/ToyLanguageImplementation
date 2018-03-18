package Model.Commands;

import Controller.Controller;
import Model.Exceptions.EmptyStackException;
import Model.Exceptions.MyStmtExecException;

public class RunExample extends Command {
    private Controller ctrl;
    public RunExample(String key, String desc, Controller ctrl){
        super(key, desc);
        this.ctrl=ctrl;
    }
    @Override
    public void executeOneStep() throws EmptyStackException,MyStmtExecException { ctrl.executeOneStep();}

    @Override
    public void prepareForExecution() {
        ctrl.prepareExecutionOfNewPrg();
    }

    @Override
    public Controller getController() {
        return ctrl;
    }
}
