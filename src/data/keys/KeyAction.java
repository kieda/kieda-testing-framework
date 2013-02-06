/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.keys;

import data.Action;
import java.io.BufferedWriter;
import java.io.IOException;
import static funs.ArrayFuns.make;
/**
 *
 * @author kieda
 */
public abstract class KeyAction extends Action{
    final int key;
    public KeyAction(int key_action, int delay_time, final byte KEY_ACTION) {
        super(delay_time, KEY_ACTION);
        this.key = key_action;
    }  
//    
//    @Override public void write(BufferedWriter out) throws IOException{
//        out.write("[");
//        out.write(key_action+" "+delay_time);
//        out.write("]");
//        if(next != null){
//            out.write(" => ");
//            next.write(out);
//        }
//    }

    @Override protected int[] important() {
        return key_important;
    }
    private static int[] key_important = new int[]{};
    @Override protected int id() {
        return key;
    }
}
