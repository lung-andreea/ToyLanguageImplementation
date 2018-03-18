package Model.Expressions;

import Model.DataStructures.HeapInterface;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyExpEvalException;

public class ArithExp extends Exp {
    Exp exp1;
    Exp exp2;
    int op;

    public ArithExp(Exp exp1, Exp exp2, int op){this.exp1=exp1; this.exp2=exp2; this.op=op;}

    @Override
    public int eval(MyIDictionary<String, Integer> symTable, HeapInterface heap) throws MyExpEvalException {
        switch (op) {
            case 1:
            {
                return exp1.eval(symTable,heap)+exp2.eval(symTable,heap);
            }
            case 2:
            {
                return exp1.eval(symTable,heap)-exp2.eval(symTable,heap);
            }
            case 3:
            {
                return exp1.eval(symTable,heap)*exp2.eval(symTable,heap);
            }
            case 4:
            {
                if (exp2.eval(symTable,heap)==0)
                    throw new MyExpEvalException("Division by zero!\n");
                return exp1.eval(symTable,heap)/exp2.eval(symTable,heap);
            }
            default:
            {
                throw new MyExpEvalException("Invalid operand!\n");
            }
        }
    }

    @Override
    public String toString() {
        switch (op)
        {
            case 1:
            {
                return exp1.toString()+"+"+exp2.toString();
            }
            case 2:
            {
                return exp1.toString()+"-"+exp2.toString();
            }
            case 3:
            {
                return exp1.toString()+"*"+exp2.toString();
            }
            case 4:
            {
                return exp1.toString()+"/"+exp2.toString();
            }
            default:
            {
                return "Invalid operand!";
            }
        }
    }
}
