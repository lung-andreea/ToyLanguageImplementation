package Model.Commands;

import Controller.Controller;
import Model.Exceptions.EmptyStackException;
import Model.Exceptions.MyStmtExecException;

public abstract class Command {
    private String key, description;
    public Command(String key, String description) { this.key = key; this.description = description;}
    public abstract void executeOneStep() throws EmptyStackException,MyStmtExecException;
    public String getKey(){return key;}
    public abstract void prepareForExecution();
    public abstract Controller getController();

    @Override
    public String toString() {
        return key+".  "+description;
    }
}
