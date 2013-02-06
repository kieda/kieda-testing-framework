/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package funs;

/**
 *
 * @author kieda
 */
public class Assert {
    public static void log(String mess){
        System.err.println(mess);
    }
    public static void Assert(boolean b, String mess){
        if(!b){System.err.println(mess+"\n(core dumped)");System.exit(0);}
    }
    public static void Assert(boolean b){
        if(!b){System.err.println("assertion failed.\n(core dumped)");System.exit(0);}
    }
}
