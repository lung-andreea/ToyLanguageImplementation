package Model.DataStructures;

import Model.Exceptions.MyExpEvalException;

import java.util.Map;

public class Heap implements HeapInterface {
    MyDictionary<Integer,Integer> dict;
    static Integer key=0;

    public Heap(){
        dict = new MyDictionary<Integer, Integer>();
    }

    public int add(Integer p)
    {
        key++;
        dict.add(key,p);
        return key;
    }

    @Override
    public int lookUp(int key) throws MyExpEvalException {
        return dict.lookUp(key);
    }

    @Override
    public void update(Integer key, Integer val) throws MyExpEvalException{
        if(!dict.dict.containsKey(key))
            throw new MyExpEvalException("The specified address is not a valid heap address!\n");
        dict.add(key, val);
    }

    @Override
    public Map getContent() {
        return dict.getContent();
    }

    @Override
    public void setContent(Map<Integer, Integer> m) {
        this.dict.setContent(m);
    }

    public String toString()
    {
        return dict.toString();
    }

    public String toStringForFile()
    {
        return dict.toStringForFile();
    }

}
