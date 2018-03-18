package Model.Expressions;

import Model.DataStructures.HeapInterface;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyExpEvalException;

public class VarExp extends Exp {
    String var;

    public VarExp(String var){this.var = var;}

    @Override
    public int eval(MyIDictionary<String, Integer> symTable, HeapInterface heap) throws MyExpEvalException {
        try
        {
            return symTable.lookUp(var);
        }
        catch (MyExpEvalException e)
        {
            throw e;
        }
    }

    @Override
    public String toString() {
        return var;
    }
}
