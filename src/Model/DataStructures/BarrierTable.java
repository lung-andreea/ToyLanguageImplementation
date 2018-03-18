package Model.DataStructures;

import Model.Exceptions.MyExpEvalException;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarrierTable implements FileTableInterface {
    HashMap<Integer,Pair<Integer,List<Integer>>> dict;
    static Integer key=0;

    public BarrierTable(){
        dict = new HashMap<>();
    }

    public int add(Pair p)
    {
        synchronized (this) {
            key++;
            dict.put(key, p);
            return key;
        }
    }

    public boolean exists(Integer i)
    {
        for(Pair p: dict.values()) {
            if((p.getKey()==i))
                return true;
        }
        return false;
    }

    public String toString()
    {
        String s="{";
        for(Map.Entry ent: dict.entrySet()) {
            if(ent.getValue() instanceof Pair) {
                String list = ((Pair) ent.getValue()).getValue().toString();
                s += ent.getKey().toString() + "->(" + ((Pair) ent.getValue()).getKey() + "," + list.substring(1, list.length()-1) + ")";
            }
            }
        if(s.length()==1)
            return "{}";
        return s+"}";
    }

    public String toStringForFile()
    {
        String s="";
        for(Map.Entry ent: dict.entrySet()){
            if(ent.getValue() instanceof Pair) {
                String list = ((Pair) ent.getValue()).getValue().toString();
                s += ent.getKey().toString() + "->(" + ((Pair) ent.getValue()).getKey() + "," + list.substring(1, list.length()-1) + ")";
            }
        }
        return s;
    }

    @Override
    public void update(Integer index, Pair p) {
        synchronized (this) {
            dict.put(index, p);

        }    }

    public Pair lookUp(Integer key) throws MyExpEvalException
    {
        synchronized (this)
        {
            if(!dict.containsKey(key))
            throw new MyExpEvalException("There is no entry in BarrierTable for "+key.toString()+"\n");
            return dict.get(key);
        }
    }

    public void deleteEntry(Integer key)
    {
        dict.remove(key);
    }

    @Override
    public boolean exists(String filename) {
        return false;
    }

    @Override
    public HashMap getContentForGUI() {
        HashMap map = new HashMap();
        for(Map.Entry ent: dict.entrySet()) {
            if(ent.getValue() instanceof Pair) {
                Integer value = (Integer) ((Pair) ent.getValue()).getKey();
                String list = ((Pair) ent.getValue()).getValue().toString();
                String str = list.substring(1,list.length()-1);
                map.put(ent.getKey(), new Pair<>(value,str));
            }
            }
        return map;
    }

    @Override
    public HashMap getContent() {
        return dict;
    }
}
