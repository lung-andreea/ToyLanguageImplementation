package Model.Expressions;

import Model.DataStructures.HeapInterface;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyExpEvalException;

public class BoolExp extends Exp {
    Exp op1,op2;
    String operator;

    public BoolExp(Exp e1, Exp e2, String op){op1=e1;op2=e2;operator=op;}

    @Override
    public int eval(MyIDictionary<String, Integer> symTable, HeapInterface heap) throws MyExpEvalException {
        switch (operator) {
            case "<": {
                return op1.eval(symTable, heap) < op2.eval(symTable, heap) ? 1 : 0;
            }
            case "<=": {
                return op1.eval(symTable, heap) <= op2.eval(symTable, heap) ? 1 : 0;
            }
            case "==": {
                return op1.eval(symTable, heap) == op2.eval(symTable, heap) ? 1 : 0;
            }
            case "!=": {
                return op1.eval(symTable, heap) != op2.eval(symTable, heap) ? 1 : 0;
            }
            case ">": {
                return op1.eval(symTable, heap) > op2.eval(symTable, heap) ? 1 : 0;
            }
            case ">=": {
                return op1.eval(symTable, heap) >= op2.eval(symTable, heap) ? 1 : 0;
            }
            default: {
                throw new MyExpEvalException("Invalid operand!\n");
            }
        }
    }

    @Override
    public String toString() {
        return "("+op1.toString()+operator+op2.toString()+")";
    }
}
