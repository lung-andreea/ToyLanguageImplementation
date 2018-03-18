package Model.Statements;

import Model.DataStructures.HeapInterface;
import Model.DataStructures.MyIStack;
import Model.Exceptions.MyExpEvalException;
import Model.Exceptions.MyStmtExecException;
import Model.Expressions.Exp;
import Model.PrgState;

public class IfStmt implements IStmt {
    Exp exp;
    IStmt thenStmt;
    IStmt elseStmt;

    public IfStmt(Exp ex, IStmt th, IStmt el){exp = ex;thenStmt = th;elseStmt = el;}

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        MyIStack<IStmt> stk = state.getExeStack();
        HeapInterface heap = state.getHeap();
        try {
            if(exp.eval(state.getSymTable(),heap)!=0)
                stk.push(thenStmt);
            else
                stk.push(elseStmt);
        }
        catch (MyExpEvalException e)
        {
            throw new MyStmtExecException(e.getMessage());
        }
        state.setExeStack(stk);
        return null;
    }

    @Override
    public String toString() {
        return "IF("+exp.toString()+")THEN("+thenStmt.toString()+")ELSE("+elseStmt.toString()+")";
    }
}
