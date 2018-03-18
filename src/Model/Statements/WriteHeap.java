package Model.Statements;

import Model.DataStructures.HeapInterface;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyExpEvalException;
import Model.Exceptions.MyStmtExecException;
import Model.Expressions.Exp;
import Model.PrgState;

public class WriteHeap implements IStmt {
    String var_name;
    Exp exp;

    public WriteHeap(String var, Exp e){var_name=var;exp=e;}

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        MyIDictionary<String,Integer> symTable = state.getSymTable();
        HeapInterface heap = state.getHeap();
        try
        {
            int key = symTable.lookUp(var_name);
            heap.update(key,exp.eval(symTable,heap));
        }
        catch(MyExpEvalException e)
        {
            throw new MyStmtExecException(e.getMessage());
        }
        state.setHeap(heap);
        return null;
    }

    @Override
    public String toString() {
        return "wH("+var_name+","+exp.toString()+")";
    }
}
