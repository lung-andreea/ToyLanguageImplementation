package Model.Statements;

import Model.DataStructures.HeapInterface;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyExpEvalException;
import Model.Exceptions.MyStmtExecException;
import Model.Expressions.Exp;
import Model.PrgState;

public class NewStmt implements IStmt {
    String var_name;
    Exp exp;

    public NewStmt(String var,Exp e){var_name=var;exp=e;}

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        MyIDictionary<String,Integer> symTable = state.getSymTable();
        HeapInterface heap = state.getHeap();
        try
        {
            int key = heap.add(exp.eval(symTable,heap));
            symTable.add(var_name,key);
        }
        catch (MyExpEvalException e)
        {
            throw new MyStmtExecException(e.getMessage());
        }
        state.setSymTable(symTable);
        state.setHeap(heap);
        return null;
    }

    @Override
    public String toString() {
        return "new("+var_name+","+exp.toString()+")";
    }
}
