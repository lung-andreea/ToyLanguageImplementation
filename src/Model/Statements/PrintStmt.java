package Model.Statements;

import Model.DataStructures.HeapInterface;
import Model.DataStructures.MyIList;
import Model.Exceptions.MyExpEvalException;
import Model.Exceptions.MyStmtExecException;
import Model.Expressions.Exp;
import Model.PrgState;

public class PrintStmt implements IStmt{
    Exp exp;

    public PrintStmt(Exp e){exp = e;}

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException{
        MyIList<Integer> out = state.getOut();
        HeapInterface heap = state.getHeap();
        try
        {
            out.add(exp.eval(state.getSymTable(),heap));
        }
        catch (MyExpEvalException e)
        {
            throw new MyStmtExecException(e.getMessage());
        }
        state.setOut(out);
        return null;
    }

    @Override
    public String toString() {
        return "print("+exp.toString()+")";
    }
}
