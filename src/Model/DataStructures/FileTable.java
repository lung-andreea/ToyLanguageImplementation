package Model.DataStructures;

import Model.Exceptions.MyExpEvalException;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  FileTable implements FileTableInterface{
    HashMap<Integer,Pair<String,BufferedReader>> dict;
    static Integer key=0;

    public FileTable(){
        dict = new HashMap<>();
    }

    public int add(Pair p)
    {
        key++;
        dict.put(key,p);
        return key;
    }

    public boolean exists(String filename)
    {
        for(Pair p: dict.values()) {
            if((p.getKey()==filename))
                return true;
        }
        return false;
    }

    public String toString()
    {
        String s="{";
        for(Map.Entry ent: dict.entrySet()) {
            if(ent.getValue() instanceof Pair)
                s += ent.getKey().toString() + "->" + ((Pair) ent.getValue()).getKey()+ ",";
        }
        if(s.length()==1)
            return "{}";
        return s.substring(0,s.length()-1)+"}";
    }

    public String toStringForFile()
    {
        String s="";
        for(Map.Entry ent: dict.entrySet()) {
            if(ent.getValue() instanceof Pair)
                s += ent.getKey().toString() + "->" + ((Pair) ent.getValue()).getKey() + "\n";
        }
        return s;
    }

    public Pair lookUp(Integer key) throws MyExpEvalException
    {
        if(!dict.containsKey(key))
            throw new MyExpEvalException("There is no entry for "+key.toString()+"\n");
        return dict.get(key);
    }

    public void deleteEntry(Integer key)
    {
        dict.remove(key);
    }

    @Override
    public void update(Integer index, Pair p) {

    }

    @Override
    public HashMap getContentForGUI() {
        HashMap map = new HashMap();
        for(Map.Entry ent: dict.entrySet()) {
            if(ent.getValue() instanceof Pair)
                map.put(ent.getKey(),((Pair) ent.getValue()).getKey());
        }
        return map;
    }

    @Override
    public HashMap getContent() {
        return dict;
    }
}
