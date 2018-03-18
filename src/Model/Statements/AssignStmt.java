package Model.Statements;

import Model.DataStructures.HeapInterface;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyExpEvalException;
import Model.Exceptions.MyStmtExecException;
import Model.Expressions.Exp;
import Model.PrgState;

public class AssignStmt implements IStmt {
    String var;
    Exp exp;

    public AssignStmt(String var, Exp e)
    {
        this.var = var;
        this.exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException
    {
        MyIDictionary<String,Integer> dict = state.getSymTable();
        HeapInterface heap = state.getHeap();
        try
        {
            dict.add(var,exp.eval(dict,heap));
        }
        catch (MyExpEvalException e)
        {
            throw new MyStmtExecException(e.getMessage());
        }
        state.setSymTable(dict);
        return null;
    }

    @Override
    public String toString() {
        return var+"="+exp.toString();
    }
}
