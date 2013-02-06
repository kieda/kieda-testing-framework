/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.thekeys;

import data.Action;
import funs.Bijection;
import static funs.MapFuns.makeMap;
import static funs.ArrayFuns.make;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 *
 * @author kieda
 */
public class IOMapping {
    private static Ject fun = new Ject();
    private static final class Ject implements Bijection<Integer, Byte> {
        
        private static final HashMap<Integer, Byte> function;
        private static final HashMap<Byte, Integer> inverse;
        static {
            Integer[] inputs = make(
                    VK_ESCAPE,
                    VK_F1,
                    VK_F2,
                    VK_F3,
                    VK_F4,
                    VK_F5,
                    VK_F6,
                    VK_F7,
                    VK_F8,
                    VK_F9,
                    VK_F10,
                    VK_F11,
                    VK_F12,
                    VK_PRINTSCREEN,
                    VK_SCROLL_LOCK,
                    VK_PAUSE,
                    VK_INSERT,
                    VK_DELETE,
                    VK_HOME,
                    VK_END,
                    VK_PAGE_UP,
                    VK_PAGE_DOWN,
                    VK_BACK_QUOTE,
                    VK_1,
                    VK_2,
                    VK_3,
                    VK_4,
                    VK_5,
                    VK_6,
                    VK_7,
                    VK_8,
                    VK_9,
                    VK_0,
                    VK_MINUS,
                    VK_EQUALS,
                    VK_BACK_SPACE,
                    VK_TAB,
                    VK_Q,
                    VK_W,
                    VK_E,
                    VK_R,
                    VK_T,
                    VK_Y,
                    VK_U,
                    VK_I,
                    VK_O,
                    VK_P,
                    VK_OPEN_BRACKET,
                    VK_CLOSE_BRACKET,
                    VK_BACK_SLASH,
                    VK_CAPS_LOCK,                    
                    VK_A,
                    VK_S,
                    VK_D,
                    VK_F,
                    VK_G,
                    VK_H,
                    VK_J,
                    VK_K,
                    VK_L,
                    VK_SEMICOLON,
                    VK_QUOTE,
                    VK_ENTER,
                    VK_SHIFT,
                    VK_Z,
                    VK_X,
                    VK_C,
                    VK_V,
                    VK_B,
                    VK_N,
                    VK_M,
                    VK_COMMA,
                    VK_PERIOD,
                    VK_SLASH,
                    VK_CONTROL,
                    VK_WINDOWS,
                    VK_ALT,
                    VK_SPACE,
                    VK_CONTEXT_MENU,
                    VK_UP,
                    VK_DOWN,
                    VK_LEFT,
                    VK_RIGHT,
                    InputEvent.BUTTON1_MASK,
                    InputEvent.BUTTON3_MASK,
                    InputEvent.BUTTON3_DOWN_MASK,
                    Action.NO_ID
                );
            
            Byte[] outputs = new Byte[]{
                     0, 1, 2, 3, 4, 5, 6, 7, 8, 9
                   ,10,11,12,13,14,15,16,17,18,19
                   ,20,21,22,23,24,25,26,27,28,29 
                   ,30,31,32,33,34,35,36,37,38,39 
                   ,40,41,42,43,44,45,46,47,48,49 
                   ,50,51,52,53,54,55,56,57,58,59 
                   ,60,61,62,63,64,65,66,67,68,69 
                   ,70,71,72,73,74,75,76,77,78,79 
                   ,80,81,82,83,84,85,-69
                };
            function = makeMap(inputs, outputs);
            inverse  = makeMap(outputs, inputs);
        }
        @Override public Byte fun(Integer in) {
            return function.get(in);
        }
        @Override public Integer inverse(Byte out) {
            return inverse.get(out);
        }
    }
    
    public static byte fun(int code){
        Byte b = fun.fun(code);
        assert b != null : "unsupported code : "+code;
        return b;
    }
    public static int inverse(byte b){
        Integer i = fun.inverse(b);
        assert i != null : "unknown byte "+b;
        return i;
    }
}