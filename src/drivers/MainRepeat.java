package drivers;

import data.Action;
import data.ActionList;
import java.io.File;
import static funs.Assert.Assert;
import java.io.IOException;
import static opt.Environment.*;
import playback.Repeater;
public class MainRepeat {
    static final String DEFAULT = CURRENT_SAVE_NAME+"."+CURRENT_SAVE_EXT;
    static File file_to_open;
    static String[] args;
    enum ArgOptions{
        NO_ARGS_GO_DEFAULT(new todo() { @Override public void proceed() {
                file_to_open = new File(DEFAULT);
                Assert(file_to_open.canRead(), "the default file doesn't seem to exist.");
            }}),
        SINGLE_STRING_GIVEN(new todo() { @Override public void proceed() {
                file_to_open = new File(args[0]);
                Assert(file_to_open.canRead(), "input file does not exist.");
            }}),
        TOO_MANY_ARGS_EXIT(new todo() { @Override public void proceed() {
                System.err.println("too many args: "+funs.ArrayFuns.funString(args));
                System.exit(0);
            }});
        private interface todo{
            public void proceed();
        }
        private todo td;
        ArgOptions(todo td){
            this.td = td;
        }
        public void go(){
            td.proceed();
        }
    }
    public static ArgOptions parse_args(String[] ars){
        args = ars;
        switch(ars.length){
            case 0:  return ArgOptions.NO_ARGS_GO_DEFAULT;
            case 1:  return ArgOptions.SINGLE_STRING_GIVEN;
            default: return ArgOptions.TOO_MANY_ARGS_EXIT;
        }
    }
    public static void main(String[] args){
        IS_RECORDING = false;
//        args = new String[]{"paint_time1.bro"};
        parse_args(args).go();
        
        System.out.println("opening file \""+file_to_open+"\"");
        try {
            ActionList al = ActionList.read(file_to_open.getPath());
            Repeater.repeat_after_me(al);
        } catch (IOException ex) {} catch (ClassNotFoundException ex) {}
    }
    
}