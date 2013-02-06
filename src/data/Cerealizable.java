/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author kieda
 */
public interface Cerealizable {
    public static final byte MOUSE_PRESSED       = -2;
    public static final byte MOUSE_RELEASED      = -3;
     
    public static final byte MOUSE_MOTION        = -4;
    public static final byte KEY_PRESSED         = -5;
    public static final byte KEY_RELEASED        = -6;
    
    public static final byte END_OF_BUFFER       = -1;
    public void write(ObjectOutputStream out) throws IOException;
}
class Test {
    public static void main(String[] args){
        System.out.println(Cerealizable.MOUSE_PRESSED);
        System.out.println(Cerealizable.MOUSE_MOTION);
        System.out.println(Cerealizable.MOUSE_RELEASED);
        System.out.println(Cerealizable.KEY_PRESSED);
        System.out.println(Cerealizable.KEY_RELEASED);
        System.out.println(Cerealizable.END_OF_BUFFER);
    }
}
