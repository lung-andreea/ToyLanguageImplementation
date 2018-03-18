package Model.Statements;

import Model.Exceptions.MyExpEvalException;
import Model.Exceptions.MyStmtExecException;
import Model.PrgState;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Await implements IStmt {
    String var;

    public Await(String var){this.var=var;}

    @Override
    public String toString() {
        return "await("+var+")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyStmtExecException {
        try
        {
            int index = state.getSymTable().lookUp(var);
            Pair p = state.getBarrierTable().lookUp(index);
            int len = ((List)p.getValue()).size();
            if((int)p.getKey()>len)
            {if(((List)p.getValue()).contains(state.getID()))
                    state.getExeStack().push(this);
            else {
                List ls = (List)p.getValue();
                ls.add(state.getID());
                state.getBarrierTable().update(index,new Pair<>(((Integer)p.getKey()),ls));
                state.getExeStack().push(this);
            }
            }
        }
        catch (MyExpEvalException e)
        {
            throw new MyStmtExecException(e.getMessage());
        }
        return null;
    }
}
