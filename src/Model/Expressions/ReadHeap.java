package Model.Expressions;

import Model.DataStructures.HeapInterface;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyExpEvalException;

public class ReadHeap extends Exp {
    String var_name;

    public ReadHeap(String var){var_name=var;}

    @Override
    public int eval(MyIDictionary<String, Integer> symTable, HeapInterface heap) throws MyExpEvalException {
        int key = symTable.lookUp(var_name);
        int value = heap.lookUp(key);
        return value;
    }

    @Override
    public String toString() {
        return "rH("+var_name+")";
    }
}
