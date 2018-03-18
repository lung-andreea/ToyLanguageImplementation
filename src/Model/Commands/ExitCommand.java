package Model.Commands;

import Controller.Controller;

public class ExitCommand extends Command {
    public ExitCommand(String key, String desc){
        super(key, desc);
    }
    @Override
    public void executeOneStep() {
        System.exit(0);
    }

    @Override
    public Controller getController() {
        return null;
    }

    @Override
    public void prepareForExecution() {
        return;
    }
}

