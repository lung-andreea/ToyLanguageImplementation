package Model.Statements;

import Model.DataStructures.MyIStack;
import Model.PrgState;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt second;

    public CompStmt(IStmt f, IStmt s){first = f; second = s;}

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(second);
        stk.push(first);
        state.setExeStack(stk);
        return null;
    }

    @Override
    public String toString() {
        return "("+first.toString()+";"+second.toString()+")";
    }

}
