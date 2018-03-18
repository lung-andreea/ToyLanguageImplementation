package Model.DataStructures;

import Model.Exceptions.MyExpEvalException;

import java.util.Map;

public interface MyIDictionary<T1,T2> {

    T2 lookUp(T1 key) throws MyExpEvalException;
    void add(T1 key, T2 value);
    void remove(T1 key);
    Map getContent();
    void setContent(Map<Integer, Integer> m);
    String toStringForFile();
    MyDictionary clone();
}
