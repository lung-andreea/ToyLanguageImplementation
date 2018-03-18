package Model.Expressions;

import Model.DataStructures.HeapInterface;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyExpEvalException;

public class ConstExp extends Exp {
    int number;

    public ConstExp(int number){this.number=number;}

    @Override
    public int eval(MyIDictionary<String, Integer> symTable, HeapInterface heap) throws MyExpEvalException{
        return number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
