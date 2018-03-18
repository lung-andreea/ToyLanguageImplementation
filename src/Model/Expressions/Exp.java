package Model.Expressions;

import Model.DataStructures.HeapInterface;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyExpEvalException;

public abstract class Exp {
    public abstract int eval(MyIDictionary<String,Integer> symTable, HeapInterface heap) throws MyExpEvalException;
}
