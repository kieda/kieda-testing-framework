package data;

import org.kieda.data_structures.Queue;
import data.keys.KeyPressed;
import data.keys.KeyReleased;
import data.mouse.MouseMotionAction;
import data.mouse.MousePressed;
import data.thekeys.IOMapping;
import java.io.*;
import java.nio.ByteBuffer;
import static opt.Environment.*;
import static funs.IntFuns.*;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * essentially a linked list of actions and the timed event that they should 
 * occur
 * 
 * large containing structure thing
 * 
 * @author kieda
 */
public class ActionList implements Cerealizable, Addable<Action> {
    public Action getHead(){
        return head;
    }
    
    public String toString(){
        return LIST_1_BEGIN + ((head != null)?head.toString():"") + LIST_1_END;
    }
    private Action head;
    private Action current;
    public void add(Action node){
        if(head == null){
            head = node;
            current = head;
        } else{
            current.next = node;
            current = current.next;
        }
    } 
    public void save()throws IOException{
        save(CURRENT_SAVE_NAME);
    }
    public void save(String filename)throws IOException{
        save(filename, CURRENT_SAVE_EXT);
    }
    public void save(String filename, String file_ext)throws IOException{
        System.out.println("saved to " +filename+"."+file_ext );
        write(new ObjectOutputStream(new FileOutputStream(filename + "."+file_ext)));
    }
    public static ActionList read() throws IOException, ClassNotFoundException{
        return read(CURRENT_SAVE_NAME + "."+CURRENT_SAVE_EXT);
    }
    public static ActionList read(String filename) throws IOException, ClassNotFoundException{
        return read(new FileInputStream(filename));
    }
//    static ArrayList<Integer> bbb = new ArrayList<Integer>();
    @Override public void write(ObjectOutputStream obj_out) throws IOException{
//        ObjectOutputStream obj_out = new ObjectOutputStream(out);
        switch(write_opt){
            case VERBOSE:
                obj_out.writeChars(toString());
//                if(head != null) head.write(obj_out);
//                obj_out.writeChars(LIST_1_END);
                break;
            case COMPACT:
                Queue<ByteBuffer> opt = new Queue<>();
                head.pass(opt);
//                System.out.println("-------------");
                int current_int = 0;
                int count = 0;
                while(!opt.isEmpty()){
                    ByteBuffer bb = opt.deq(); assert bb!=null;
                    while(bb.hasRemaining()){
                        byte b = bb.get(); 
//                        System.out.println(bin(b&0xFF)+ " byte "+ b +" to position " + count);
                        current_int |= (count==0)?((b<<24)&0xFF000000):
                                       (count==1)?((b<<16)&0x00FF0000):
                                       (count==2)?((b<< 8)&0x0000FF00):
                                       (b)&0xFF;
//                        System.out.println(bin((count==0)?((b<<24)&0xFF000000):
//                                       (count==1)?((b<<16)&0x00FF0000):
//                                       (count==2)?((b<< 8)&0x0000FF00):
//                                       (b)&0xFF));
////                        System.out.println(Integer.toBinaryString(b) + " "+Integer.toBinaryString(current_int) + " " +Integer.toBinaryString((b<<(24-8*count))&0xFF));
                        if((count = (count+1)%4)==0){
//                            System.out.println(bin(current_int)+ " %");
//                            out.write(current_int);
//                            bbb.add(current_int);
                            obj_out.writeInt(current_int);
                            current_int = 0;
                        }
                    }
                    //  AA BB CC DD EE
                    //0x00 00 00 00
                    //  get AA
                    //  AA<<(24)
                }
//                System.out.println("# "+Integer.toBinaryString(current_int));
                switch(count){
                    case 1: current_int |= (END_OF_BUFFER<<16)&0xFF0000;
//                        System.out.println(bin((END_OF_BUFFER<<16)&0xFF0000) + " $1");
                    case 2: current_int |= (END_OF_BUFFER<<8) &0x00FF00;
//                        System.out.println(bin((END_OF_BUFFER<<8)&0x00FF00)+ " $2");
                    case 3: current_int |= ((END_OF_BUFFER)    &0x0000FF);
//                        System.out.println(bin((END_OF_BUFFER)&0x0000FF)+ " $3");
//                        System.out.println(bin(current_int)+ " %");
//                        out.write(current_int);
//                        bbb.add(current_int);
                        obj_out.writeInt(current_int);
                    case 0: break; default: assert false;
                }
                break;
//        ObjectOutputStream oos = new ObjectOutputStream(fos);
//
//        oos.writeInt(12345);
//        oos.writeObject("Today");
//        oos.writeObject(new Date());
//
//        oos.close();
        }
//        obj_out.writeObject(bbb);
        obj_out.close();
//        out.close();
    } public static ActionList read(FileInputStream in)  throws IOException, ClassNotFoundException{
//        System.out.println(bbb);
        ObjectInputStream obj_in = new ObjectInputStream(in);
        ActionList al = new ActionList();
        
       
//        int current_int = 0;
//        int i = 0;
//        byte curr = 0;
        while(obj_in.available() != 0){
            
            byte len;
            byte action_id;
            byte id;
            int  time;
            
            
            
           /**
            * bytecode format:
            * 1(buffer_len) 1(ACTION_ID) 4(DELAY_TIME) 1(id)
            */
            
            len = obj_in.readByte();
            if(len == -1) break;
            action_id = obj_in.readByte();
            byte tt1, tt2, tt3, tt4;
            tt1 = obj_in.readByte();
            tt2 = obj_in.readByte();
            tt3 = obj_in.readByte();
            tt4 = obj_in.readByte();
            time = join(tt1, tt2, tt3, tt4);
            id  = obj_in.readByte();
            len-=6;
                //we ran through six elements just going through the 
                //beginning.
//            System.err.println(len/4);
            int[] args = new int[len/4];
            
            for(int i = 0; i<len/4;i++){
                
                byte arg1 = obj_in.readByte();
                byte arg2 = obj_in.readByte();
                byte arg3 = obj_in.readByte();
                byte arg4 = obj_in.readByte();
                
//                System.out.println(join(arg1, arg2, arg3, arg4)+ "<<<");
                args[i] = join(arg1, arg2, arg3, arg4);
            }
//            System.out.println(time);
//            System.out.println(IOMapping.inverse(id));
//            System.out.println(action_id);
//            System.out.println(Arrays.toString(args));
            al.add(Action.create(time, IOMapping.inverse(id),  action_id, args));
        }
        return al;
    }
}
class Tes{
    static int i;
    public static void main(String[] args){
//        int k = 0;
        ActionList al = new ActionList();
//        al.add(new MousePressed(1, 10, 30, 7));
        
        
        al.add(new KeyPressed (KeyEvent.VK_E, 1000));
        al.add(new KeyReleased(KeyEvent.VK_E, 1));
        al.add(new KeyReleased(KeyEvent.VK_E, 1));
        al.add(new KeyPressed (KeyEvent.VK_A, 1));
        al.add(new KeyReleased(KeyEvent.VK_A, 1));
        al.add(new KeyPressed (KeyEvent.VK_B, 1));
        al.add(new KeyReleased(KeyEvent.VK_B, 1));
        al.add(new KeyPressed (KeyEvent.VK_C, 1));
        al.add(new KeyReleased(KeyEvent.VK_C, 1));
        try {
            al.save();
            ActionList aa = ActionList.read();
            System.out.println(aa);
            write_opt = VERBOSE;
            aa.write(new ObjectOutputStream(new FileOutputStream("out_decrypted.txt")));
        } catch (IOException|ClassNotFoundException ex) {
            System.err.println("the file is corrupted.\n(core dumped)");
        }
    }
}