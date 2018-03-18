package Model.Statements;

import Model.DataStructures.*;
import Model.Exceptions.MyStmtExecException;
import Model.PrgState;

public class ForkStmt implements IStmt {
    IStmt prg;
    static int ct = 0;

    public ForkStmt(IStmt prg){this.prg=prg;}

    @Override
    public String toString() {
        return "fork("+prg.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        MyIStack<IStmt> exeStack2 = new MyStack<>();
        MyIDictionary<String,Integer> symTable2 = state.getSymTable().clone();
        MyIList<Integer> out2 = state.getOut();
        FileTableInterface fl2 = state.getFileTable();
        HeapInterface h2 = state.getHeap();
        FileTableInterface br = state.getBarrierTable();
        int id2 = (state.getID()*10)+ct;
        ct++;
        PrgState state2 = new PrgState(exeStack2,symTable2,out2,fl2,h2,br,prg,id2);
        return state2;
    }
}