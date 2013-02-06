/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.EnumMap;
import java.util.HashMap;
import static funs.MapFuns.*;
import static funs.ArrayFuns.make;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.util.HashSet;
/**
 * @description Domo Arigato!
 * @author kieda
 */
public class Roboto {
    private static Robot robby;
    static {
        try {
            robby = new Robot();
        } catch (AWTException ex) {
            System.err.println("Error in creating robot.");
            System.exit(0);
        }
    }
    
    //we want to easily translate the key events that take
    public static void fire_mouse_press(int MOUSE_CODE, int x, int y){
        if(!isDown(MOUSE_CODE)){
            robby.mouseMove(x, y);
            robby.mousePress(MOUSE_CODE);
            setDown(MOUSE_CODE);
        }
    }
    public static void fire_mouse_release(int MOUSE_CODE, int x, int y){
//        if(isDown(MOUSE_CODE)){
            release(MOUSE_CODE);
            robby.mouseRelease(MOUSE_CODE);
//        }
//        System.out.println("x" + x + " y " + y + " code " +MOUSE_CODE);
    }
    public static void fire_mouse_move(int x, int y){
        robby.mouseMove(x, y);
    }
    public static void fire_keypress(int KEY_CODE){
        robby.keyPress(KEY_CODE);
    }
    public static void fire_keyrelease(int KEY_CODE){
        robby.keyRelease(KEY_CODE);
    }
    
    /**
     * types in a string of text with a int delay in milliseconds between each 
     * key press.
     */
    public static void fireString(String s, int delay){
        assert s != null && delay >= 0;
        char[] seq = s.toCharArray();
        assert !isDown(VK_SHIFT) : "shift_down was accessed out of thread.";
        for(int i = 0; i < seq.length; i++){
            Integer val_to_shift = SHIFTED_KEYS.get(seq[i]);
            if(val_to_shift != null){
                if(isDown(VK_SHIFT)){
                    robby.keyPress(val_to_shift);
                    //we already know that the shift button is held down.
                } else{
                    setDown(VK_SHIFT);
                    //we should press shift, then call the int.
                    robby.keyPress(VK_SHIFT);       wait(delay);
                    robby.keyPress(val_to_shift);   wait(delay);
                    robby.keyRelease(val_to_shift);
                }
            } else{
                if(release(VK_SHIFT)) {
                    robby.keyRelease(VK_SHIFT);
                }//release shift
                Integer val = REGULAR_KEYS.get(seq[i]);
                assert val != null : "invalid character: \'"+seq[i]+"\'";
                robby.keyPress(val);    wait(delay);
                robby.keyRelease(val);
            }
            wait(delay);
        }
        release();//release all keys.
    }
    private static void wait(int delay){
        assert delay >=0;
        try{
            Thread.currentThread().sleep(delay);
        } catch (Exception e){}
    }
//    private static int shifted_chars(char[] seq, int begin){
//        if(begin == seq.length ) return 0;
//        assert seq != null && begin >= 0 && begin < seq.length;
//        Integer first_val = SHIFTED_KEYS.get(seq[begin]);
//        if(first_val == null){
//            return 0;
//        } else{
//            int count = 1;
//            for(int i = begin; i < seq.length; i++){
//                if(SHIFTED_KEYS.containsKey(seq[i])) count++;
//                else break;
//            }
//            return count;
//        }
//    }
    private static final HashMap<Character, Integer> SHIFTED_KEYS =
                makeMap(make(
                        //chars that would require a shift to be hit down in 
                        //order to be fired properly by the keyboard
                                '!'     // => 1
                            ,   '@'     // => 2
                            ,   '#'     // => 3
                            ,   '$'     // => 4
                            ,   '%'     // => 5
                            ,   '^'     // => 6
                            ,   '&'     // => 7
                            ,   '*'     // => 8
                            ,   '('     // => 9
                            ,   ')'     // => 0
                            ,   '_'     // => -
                            ,   '+'     // => =
                            ,   '{'     // => [
                            ,   '}'     // => ]
                            ,   '|'     // => \
                            ,   ':'     // => ;
                            ,   '"'     // => '
                            ,   '~'     // => `
                            ,   '<'     // => ,
                            ,   '>'     // => .
                            ,   '?'     // => /
            
                            ,   'Q'     // => q
                            ,   'W'     // => w
                            ,   'E'     // => e
                            ,   'R'     // => r
                            ,   'T'     // => t
                            ,   'Y'     // => y
                            ,   'U'     // => u
                            ,   'I'     // => i
                            ,   'O'     // => o
                            ,   'P'     // => p
            
                            ,   'A'     // => a
                            ,   'S'     // => s
                            ,   'D'     // => d
                            ,   'F'     // => f
                            ,   'G'     // => g
                            ,   'H'     // => h
                            ,   'J'     // => j
                            ,   'K'     // => k
                            ,   'L'     // => l
            
                            ,   'Z'     // => z
                            ,   'X'     // => x
                            ,   'C'     // => c
                            ,   'V'     // => v
                            ,   'B'     // => b
                            ,   'N'     // => n
                            ,   'M'     // => m
                        ),make(
                        //the corresponding positions on the keyboard
                              VK_1              // <= !
                            , VK_2              // <= @
                            , VK_3              // <= #
                            , VK_4              // <= $
                            , VK_5              // <= %
                            , VK_6              // <= ^
                            , VK_7              // <= &
                            , VK_8              // <= *
                            , VK_9              // <= (
                            , VK_0              // <= )
                            , VK_MINUS          // <= _
                            , VK_EQUALS         // <= +
                            , VK_OPEN_BRACKET   // <= {
                            , VK_CLOSE_BRACKET  // <= }
                            , VK_BACK_SLASH     // <= |
                            , VK_SEMICOLON      // <= :
                            , VK_QUOTE          // <= '
                            , VK_BACK_QUOTE     // <= ~
                            , VK_COMMA          // <= <
                            , VK_PERIOD         // <= >
                            , VK_SLASH          // <= ?
            
                            , VK_Q
                            , VK_W
                            , VK_E
                            , VK_R
                            , VK_T
                            , VK_Y
                            , VK_U
                            , VK_I
                            , VK_O
                            , VK_P
            
                            , VK_A
                            , VK_S
                            , VK_D
                            , VK_F
                            , VK_G
                            , VK_H
                            , VK_J
                            , VK_K
                            , VK_L
            
                            , VK_Z
                            , VK_X
                            , VK_C
                            , VK_V
                            , VK_B
                            , VK_N
                            , VK_M
                        )
                );
    
    //here for faster access to commonly used key codes
    private static boolean shift_d = false;
    private static boolean ctrl_d  = false;
    private static boolean alt_d   = false;
    private static boolean isDown(int i){
        switch(i){
            case VK_SHIFT  : return shift_d;
            case VK_CONTROL: return ctrl_d;
            case VK_ALT    : return alt_d;
            default        : return keys_down.contains(i);
        }
    }
    private final static String MESSAGE = " mask was left down";
    /**
     * returns true if the key is 
     */
    private static boolean setDown(int i){
        switch(i){
            case VK_SHIFT  : assert !shift_d        :"shift"  + MESSAGE; return !(shift_d = true);
            case VK_CONTROL: assert !ctrl_d         :"control"+ MESSAGE; return !(ctrl_d  = true);
            case VK_ALT    : assert !alt_d          :"alt"    + MESSAGE; return !(alt_d   = true);
            default        : assert keys_down.add(i):i+" key" + MESSAGE; return false;
        }
    }
    private static boolean release(int i){
        boolean pre;
        switch(i){
            case VK_SHIFT  : pre = shift_d; shift_d = false; break;
            case VK_CONTROL: pre = ctrl_d;  ctrl_d  = false; break;
            case VK_ALT    : pre = alt_d;   alt_d   = false; break;
            default        : return keys_down.remove(i);
        }
        return pre;
    }
    private static HashSet<Integer> keys_down = new HashSet<>();
    private static void release(){
        if(shift_d) {shift_d = false;robby.keyRelease(VK_SHIFT);}
        if(alt_d)   {alt_d   = false;robby.keyRelease(VK_ALT);}
        if(ctrl_d)  {ctrl_d  = false;robby.keyRelease(VK_CONTROL);}
        Object[] obj = keys_down.toArray();
        for(int i = 0; i < obj.length;i++){
            int key = (int)obj[i];
            robby.keyRelease(key);
            keys_down.remove(key);
        }
        assert keys_down.isEmpty();
        assert !(shift_d||alt_d||ctrl_d);
    }
    /**
     * the list of all chars that can be made regularly via firing through the 
     * keyboard. 
     */
    private static final HashMap<Character, Integer> REGULAR_KEYS =
                makeMap(make(
                                '`'
                            ,   '1'
                            ,   '2'
                            ,   '3'
                            ,   '4'
                            ,   '5'
                            ,   '6'
                            ,   '7'
                            ,   '8'
                            ,   '9'
                            ,   '0'
                            ,   '-'
                            ,   '='
                            ,   'q'
                            ,   'w'
                            ,   'e'
                            ,   'r'
                            ,   't'
                            ,   'y'
                            ,   'u'
                            ,   'i'
                            ,   'o'
                            ,   'p'
                            ,   '['
                            ,   ']'
                            ,   '\\'
                            ,   'a'
                            ,   's'
                            ,   'd'
                            ,   'f'
                            ,   'g'
                            ,   'h'
                            ,   'j'
                            ,   'k'
                            ,   'l'
                            ,   ';'
                            ,   '\''
                            ,   'z'
                            ,   'x'
                            ,   'c'
                            ,   'v'
                            ,   'b'
                            ,   'n'
                            ,   'm'
                            ,   ','
                            ,   '.'
                            ,   '/'
                            ,   ' '
                            ,   '\n'
                            ,   '\t'
                        ),make(
                              VK_BACK_QUOTE
                            , VK_1
                            , VK_2
                            , VK_3
                            , VK_4
                            , VK_5
                            , VK_6
                            , VK_7
                            , VK_8
                            , VK_9
                            , VK_0
                            , VK_MINUS
                            , VK_EQUALS
                            , VK_Q
                            , VK_W
                            , VK_E
                            , VK_R
                            , VK_T
                            , VK_Y
                            , VK_U
                            , VK_I
                            , VK_O
                            , VK_P
                            , VK_OPEN_BRACKET
                            , VK_CLOSE_BRACKET
                            , VK_BACK_SLASH
                            , VK_A
                            , VK_S
                            , VK_D
                            , VK_F
                            , VK_G
                            , VK_H
                            , VK_J
                            , VK_K
                            , VK_L
                            , VK_SEMICOLON
                            , VK_QUOTE
                            , VK_Z
                            , VK_X
                            , VK_C
                            , VK_V
                            , VK_B
                            , VK_N
                            , VK_M
                            , VK_COMMA
                            , VK_PERIOD
                            , VK_SLASH
                            , VK_SPACE
                            , VK_ENTER
                            , VK_TAB
                        )
                );
    public static void main(String[] args){
        wait(1000);
        fireString("this is a test.\n\n"
                
                +  "`1234567890-=\n"
                + "\tqwertyuiop[]\\\n"
                + "asdfghjkl;'\n"
                + "zxcvbnm,./\n\n"
                
                + "~!@#$%^&*()_+\n"
                + "QWERTYUIOP{}|\n"
                + "ASDFGHJKL:\"\n"
                + "ZXCVBNM<>? \n"
                + "end.", 1);
    }
}
