package drivers;

import java.util.HashMap;
import java.util.HashSet;
import static opt.Environment.*;
import static funs.Assert.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import recorder.Core;
/**
 * the main file for the recorder.
 * @author kieda
 * 
 * args: 
 *          default settings
 * 
 * -m m      record mouse movements as well
 * _m m      override and do not record mouse movements
 * 
 * -m p      record mouse press
 * _m p
 * 
 * -m r      mouse release
 * _m r
 * 
 * -k p      key press
 * _k p
 * 
 * -k r      key release
 * _k r
 * 
 * default:  file name output
 * 
 * 
 * 
 * 
 * --nonstop    <key name>
 * or
 * -nsp         <key name>
 * 
 * the window is ran always on top, and consumes all inputs from the windows, 
 * alt, etc. keys (i.e. you won't be able to run anything on top.)
 * 
 * We then break and save ONCE the key in <key name> is pressed. <key name> must 
 * be a valid standard key on a US - type keyboard. If not, the program will 
 * throw an assertion error.
 */
public class MainRecorder {
    private static File SAVE_FILE;
    private static final String DEFAULT_SAVE_FILE = DEFAULT_SAVE_NAME + "."+DEFAULT_SAVE_EXT;
    
    /**
     * java enums: too cool!
     */
    private enum Args{
        NOT_MOUSE("_m", new execute_args(){
            @Override public int num_args(){return 1;}
            @Override public boolean exec(int pos, String[] args) {
                return mouse_parse(args[pos+1], false);
        }}),
        MOUSE("-m", new execute_args(){
            @Override public int num_args(){return 1;}
            @Override public boolean exec(int pos, String[] args) {
                return mouse_parse(args[pos+1], true);
        }}),
        KEY("-k", new execute_args(){
            @Override public int num_args(){return 1;}
            @Override public boolean exec(int pos, String[] args) {
                return key_parse(args[pos+1], true);
        }}),
        NOT_KEY("_k", new execute_args(){
            @Override public int num_args(){return 1;}
            @Override public boolean exec(int pos,String[] args) {
                return key_parse(args[pos+1], false);
        }}),
        SAVE_TO(null, new execute_args() {
            @Override public int num_args(){return 0;}
            @Override public boolean exec(int pos, String[] args) {
                try {
                    SAVE_FILE = new File(args[pos]); boolean b;
                    if(!(b = (SAVE_FILE.exists() && !SAVE_FILE.isDirectory() && SAVE_FILE.canWrite()) || (SAVE_FILE.createNewFile())))
                        log("cannot write to file "+args[pos]);
                    return b;
                } catch (IOException ex) {}
                log("cannot write to file "+args[pos]);
                
                
                return false;                
        }});
        private static boolean key_parse(String s, boolean val){
            switch(s){
                case PRESS:
                    record_key_press = val;     return true;
                case RELEASE:
                    record_key_release = val;   return true;
                default:    
                    log("illegal param for flag: \""+s+"\"");
                    return false;
            }
        }
        private static boolean mouse_parse(String s, boolean val){
            switch(s){
                case PRESS:
                    record_mouse_press = val;       return true;
                case RELEASE:
                    record_mouse_release = val;     return true;
                case MOVEMENT:
                    record_mouse_movement = val;    return true;
                default:    
                    log("illegal param for flag: \""+s+"\"");
                    return false;
            }
        }
        private static final String PRESS       = "p";
        private static final String MOVEMENT    = "m";
        private static final String RELEASE     = "r";
        private static final class data{
            private static HashMap<String, execute_args> flags = new HashMap<>();
            private static execute_args default_;
        }
        private interface execute_args{
            boolean exec(int pos, String[] args);
            int num_args();
        }
        private final String token;
        private final HashSet<String> next;
        private Args(String token, execute_args exec){
            if(token!=null)
                data.flags.put(token, exec);
            else{
                Assert(data.default_ ==null, "cannot have more than one default value.");
                data.default_ = exec;
            }
            this.token = token;
            HashSet<String> temp = new HashSet<String>();
            this.next = temp;
        }
        public HashSet<String> getNextOpts(){return next;}
        public String getTok(){return token;}
        public static void parseArgs(String[] default_val, String[] args){
            Assert(args != null);
            boolean default_set = false;
            for(int i = 0; i < args.length; i++){
                execute_args ex = data.flags.get(args[i]);
                if(ex == null){
                    Assert(!default_set, "the dault option has been set more than once.");
                    ex = data.default_;
                    Assert(ex!=null, "no default option");
                    default_set = true;
                } 
                Assert(i+ex.num_args()<args.length, "too few args");
                Assert(ex.exec(i, args),"unsuccessful parse of "+args[i]); i+=ex.num_args();
            }
            if(!default_set){
                data.default_.exec(0, default_val);
            }
        } static {
            Assert(data.default_ != null, "the default case for inputs must be handled");
        }
    }
    
    public static void main(String[] args) {
        IS_RECORDING = true;
//        args = new String[]{
//            "-m", "p",
//            "-m", "r",
//            "-m", "m",
//            "-k", "p",
//            "-k", "r",
//            "paint_time1.bro"
//        };
        Args.parseArgs(new String[]{DEFAULT_SAVE_FILE}, args);
            //sets up the environment based on the args
        
        String name = SAVE_FILE.getName();
        int idx = name.indexOf(".");
        if(idx != -1){
            CURRENT_SAVE_NAME = name.substring(0, idx);
            CURRENT_SAVE_EXT  = name.substring(idx+1, name.length());
        } else{
            CURRENT_SAVE_NAME = name;
        }
        Core.open_core("Kieda Testing Framework", "v1.1");
    }
}
