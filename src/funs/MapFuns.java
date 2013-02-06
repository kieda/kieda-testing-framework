/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package funs;

import java.util.HashMap;
import java.util.HashSet;

/**
 * functions on maps
 * @author kieda
 */
public class MapFuns {
    /**
     * does NOT copy the internal values.
     * Returns null if keys or values is null
     * 
     * if keys.length != values.length we return a map that is of maximum size
     * (keys.length > values.length) ? keys.length : values.length
    */
    public static <K, V> HashMap<K, V> makeMap(K[] keys, V[] values){
        if(keys==null || values == null) return null;
        HashMap<K, V> ret = new HashMap<>();
        
        final int max = (keys.length > values.length) ? keys.length : values.length;
        
        assert max <= keys.length && max <= values.length;
        
        for(int i = 0; i < max; i++){
            ret.put(keys[i], values[i]);
        }
        
        return ret;
    }
    
    public static <S> HashSet<S> makeSet(S[] values){
        HashSet<S> temp = new HashSet<S>();
        for(int i = 0; i <values.length; i++){
            temp.add(values[i]);
        }
        return temp;
    }
    
}
