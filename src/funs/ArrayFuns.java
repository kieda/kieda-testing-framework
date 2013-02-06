/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package funs;
import static opt.Environment.*;
/**
 *
 * @author kieda
 */
public class ArrayFuns {
    public static String funString(Object[]... os){
        StringBuilder sb = new StringBuilder();
        sb.append(LIST_2_BEGIN);
        if(os!=null)
        for(int i = 0; i < os.length; i++)
            if(os[i] != null)
                for(int j = 0; j < os[i].length; j++)
                    sb.append((i==0 && j==0)?"":(CONNECTIVE_LIST))
                      .append(os[i][j].toString());
        
        sb.append(LIST_2_END);
        return sb.toString();
    }
    /**
     * just a shortcut to make an array
     */
    public static <K> K[] make(K... vals){
        return vals;
    }
}
