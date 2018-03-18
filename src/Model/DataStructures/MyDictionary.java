package Model.DataStructures;

import Model.Exceptions.MyExpEvalException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<T1,T2> implements MyIDictionary<T1,T2> {
    protected HashMap<T1,T2> dict;

    public <T1,T2> MyDictionary(){
        this.dict=new HashMap<>();
    }
    @Override
    public T2 lookUp(T1 key)throws MyExpEvalException {
        if(dict.containsKey(key)==false)
            throw new MyExpEvalException("There is no entry for "+key.toString()+"\n");
        return dict.get(key);
    }

    //Adds the pair (key,value) if key is not in dict and updates the value for the pair if key is already in dict
    @Override
    public void add(T1 key, T2 value) {
        dict.put(key,value);
    }

    @Override
    public void remove(T1 key) {
        dict.remove(key);
    }

    @Override
    public Map getContent() {
//        Map<T1,T2> map = new HashMap<>();
//        ArrayList<T1> keys = new ArrayList<T1>(dict.keySet());
//        for(int i=keys.size()-1; i>=0;i--){
//            map.put(keys.get(i),dict.get(keys.get(i)));
//        }
//        return map;
        return dict;
    }

    @Override
    public void setContent(Map<Integer, Integer> m) {
        this.dict=(HashMap<T1, T2>) m;
    }

    @Override
    public String toString() {
        String s="{";
        for(Map.Entry ent: dict.entrySet()) {
            s += ent.getKey().toString() + "->" + ent.getValue().toString() + ",";
        }
        if(s.length()==1)
            return "{}";
        return s.substring(0,s.length()-1)+"}";
    }

    @Override
    public MyDictionary clone() {
        MyDictionary copy = new MyDictionary();
        for(Map.Entry ent: dict.entrySet())
        {
            copy.add(ent.getKey(),ent.getValue());
        }
        return copy;
    }

    @Override
    public String toStringForFile() {
        String s="";
        for(Map.Entry ent: dict.entrySet()) {
            s += ent.getKey().toString() + "->" + ent.getValue().toString() + "\n";
        }
        return s;
    }
}
