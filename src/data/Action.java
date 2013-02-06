package data;

import org.kieda.data_structures.Queue;
import org.kieda.util.BufferUtils;
import data.keys.KeyPressed;
import data.keys.KeyReleased;
import data.mouse.MouseMotionAction;
import data.mouse.MousePressed;
import data.mouse.MouseReleased;
import data.thekeys.IOMapping;
import java.io.IOException;
import static funs.ArrayFuns.make;
import static funs.ArrayFuns.funString;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.TimerTask;
import opt.Environment;
import static opt.Environment.*;
/**
 * 
 * @author kieda
 */
public abstract class Action extends TimerTask implements Cerealizable, Addable<Action>, BufferPassable{
    
    public static final int NO_ID = -1;
      public Action next(){
        return next;
    } @Override public String toString(){
        return imp() + ((next != null)?CONNECTIVE_LIST+next.toString():"");
    }
    private String imp(){
        StringBuilder sb = new StringBuilder();
        sb.append(LIST_2_BEGIN).append(ACTION_ID).append(CONNECTIVE_LIST).append(id()).append(CONNECTIVE_LIST);
        int[] i = important();
        for(int s = 0; s < i.length; s++){
            sb.append(i[s]).append(CONNECTIVE_LIST);
        }
        sb.append(delay_time).append(LIST_2_END);
        return sb.toString();
    }
    private final int delay_time;
    private final byte ACTION_ID;
    public int get_delay_time(){
        return delay_time;
    } 
//      public byte get_action_id() {
//        return ACTION_ID;
//    } public int get_id(){
//        return id();
//    } public int[] get_info(){
//        return important();
//    }
    Action next;
      public void add(Action node){
        if(next == null){
            next = node;
        } else{
            next.add(node);
        }
    }
    public Action(int delay_time, final byte ACTION_ID){
        this.delay_time = delay_time;
        this.ACTION_ID = ACTION_ID;
    }
    protected abstract int   id();
    protected abstract int[] important();
    @Override public final void write(ObjectOutputStream oos) throws IOException{
//        ObjectOutputStream oos = new ObjectOutputStream(out);
        switch(Environment.write_opt){
            case Environment.VERBOSE:
                oos.writeChars(funString(
                        make(ACTION_ID),make(important()),make(delay_time)
                    ));
                if(next != null){
                    oos.writeChars(Environment.CONNECTIVE_NEXT);
                    oos.close();
                    next.write(oos);
                } break;
            case Environment.COMPACT:
                assert false:"should not be called here.";
                break;
        }
        
    }
    /*
     * bytecode format:
     * 1(buffer_len) 1(ACTION_ID) 4(DELAY_TIME) 1(id)
     * 
     */
    @Override public void pass(Queue<ByteBuffer> b) {
        byte buffer_len = 6;
            //the length of the byte array.
            //1 for buffer len, 1 for action id,  4 for time delay, and one for the id.
        int[] info = important();
        int len = info.length;
        assert len*4 < (255-buffer_len) : "buffer too long to be interpreted";
        buffer_len += (byte)len*4;
        ByteBuffer add = BufferUtils.createByteBuffer(buffer_len+1);
        add.put(buffer_len);
//        System.out.println(buffer_len+ " len ");
//        System.out.println(ACTION_ID + " action_id ");
//        System.out.println(((delay_time>>24)&0xFF) + " delay 1");
//        System.out.println(((delay_time>>16)&0xFF) + " delay 2");
//        System.out.println(((delay_time>>8)&0xFF) + " delay 3");
//        System.out.println(((delay_time)&0xFF) + " delay 4");
//        System.out.println(IOMapping.fun(id()) + " mapping ");
        
        add.put(ACTION_ID);
        add.put((byte)((delay_time>>24)&0xFF));
        add.put((byte)((delay_time>>16)&0xFF));
        add.put((byte)((delay_time>>8 )&0xFF));
        add.put((byte)((delay_time    )&0xFF));
        add.put(IOMapping.fun(id()));
        
        for(int i = 0; i < len; i++){
            int inf = info[i];
            add.put((byte)((inf>>24)&0xFF));
            add.put((byte)((inf>>16)&0xFF));
            add.put((byte)((inf>> 8)&0xFF));
            add.put((byte)((inf    )&0xFF));
        }
        
        add.position(0);
        b.enq(add);
        if(next != null)
            next.pass(b);
    }
    static Action create(int time, int id, byte action_id, int[] args){
        switch(action_id){
            case KEY_PRESSED:
                assert args.length == 0;
                return new KeyPressed(id, time);
            case KEY_RELEASED:
                assert args.length == 0;
                return new KeyReleased(id, time);
            case MOUSE_MOTION:   
                assert args.length == 2;
                return new MouseMotionAction(args[0], args[1], time);
            case MOUSE_PRESSED:  
                assert args.length == 2;
                return new MousePressed(id, args[0], args[1], time);
            case MOUSE_RELEASED: 
                assert args.length == 2;
                return new MouseReleased(id, args[0], args[1], time);
            default: assert false : "unknown action type.";  return null;
        }
    }
}
