package Model.Statements;

import Model.DataStructures.HeapInterface;
import Model.DataStructures.MyIDictionary;
import Model.DataStructures.MyIStack;
import Model.Exceptions.MyExpEvalException;
import Model.Exceptions.MyStmtExecException;
import Model.Expressions.Exp;
import Model.PrgState;

public class WhileStmt implements IStmt {
    Exp exp;
    IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt){this.exp=exp;this.stmt=stmt;}

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        MyIDictionary<String,Integer> symTable = state.getSymTable();
        HeapInterface heap = state.getHeap();
        try{
            if(exp.eval(symTable,heap)!=0)
            {
                exeStack.push(this);
                exeStack.push(stmt);
            }
        }
        catch (MyExpEvalException e)
        {
            throw new MyStmtExecException(e.getMessage());
        }
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public String toString() {
        return "while("+exp.toString()+")"+stmt.toString();
    }
}
