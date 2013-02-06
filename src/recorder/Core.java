package recorder;

import org.kieda.util.console.Console;
import data.Action;
import data.ActionList;
import data.keys.KeyPressed;
import data.keys.KeyReleased;
import data.mouse.MouseMotionAction;
import data.mouse.MousePressed;
import data.mouse.MouseReleased;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.JFrame;
import static opt.Environment.record_key_press;
import static opt.Environment.record_key_release;
import static opt.Environment.record_mouse_movement;
import static opt.Environment.record_mouse_press;
import static opt.Environment.record_mouse_release;
import static opt.Environment.TRANSLUCENT_VAL;
import static opt.Environment.PER_PIXEL_TRANSP_VAL;
import static opt.Environment.PER_PIXEL_TRANSLU_VAL;
import static opt.Environment.CHOSEN_OPTION;
public class Core {
    public static void open_core(String project_name, String version){
        System.setProperty("project_name", project_name);
        System.setProperty("project_version", version);
        initiate_console();
        initiate_frame();
    }
    private static void initiate_frame(){
        Frame frame = new Frame();
        Thread t = new Thread(frame);
        t.start();
    }
    private static void initiate_console(){
        Console.sleep = 100;
        Console.open();
        new Console.Module() {
            @Override
            public String helpF() {
                return "exits this program";
            }@Override public String name() {
                return "exit";
            }@Override public void execute(String... params) {
                exit();
            }
        };
        new Console.Module() {
            @Override
            public String helpF() {
                return "prints the current status of the action list";
            }@Override public String name() {
                return "print";
            }@Override public void execute(String... params) {
                System.out.println(list);
            }
        };
    }
    
    
    private static ActionList list = new ActionList();
    static LocalListeners listeners = new LocalListeners();
    
    private final static long begin_time = System.currentTimeMillis();
    private static long last_evt = begin_time;
    
    private static long update_time(){
        long time_curr = System.currentTimeMillis();
        int time_dif = (int)(time_curr - last_evt);
        last_evt = time_curr;
        return time_dif/2;
    }
    
//    public static long update_time(){
////        long time_curr = System.currentTimeMillis();
//        return (System.currentTimeMillis() - begin_time);// /2
//    }
    
    
    
    /**currently nothing*/
    private static void paint_mousepress_to_screen(int x, int y){}
    private static void paint_mouserelease_to_screen(int x, int y){}
    private static void paint_mousemove_to_screen(int x, int y){}
    private static void paint_keypress_to_screen(int code){}
    private static void paint_keyrelease_to_screen(int code){}
    
    static class LocalListeners implements MouseListener, KeyListener, MouseMotionListener{
        public void feed(Action a){list.add(a);}
        
        @Override public void mouseClicked(MouseEvent e) {
        } @Override public void mousePressed(MouseEvent e) {
            
            if(record_mouse_press && !button_down) {
                int x, y; x = e.getXOnScreen(); y = e.getYOnScreen();
                paint_mousepress_to_screen(x,y);
                switch(e.getButton()){
                    case MouseEvent.BUTTON1:
                        feed(new MousePressed(InputEvent.BUTTON1_MASK, x, y, (int)update_time()));
                        break;
                    case MouseEvent.BUTTON2:
                        feed(new MousePressed(InputEvent.BUTTON3_DOWN_MASK, x, y, (int)update_time()));
                        break;
                    case MouseEvent.BUTTON3:
                        feed(new MousePressed(InputEvent.BUTTON3_MASK, x, y, (int)update_time()));
                        break;
                }
                
            }
            button_down = true;
        } @Override public void mouseReleased(MouseEvent e) {
            button_down = false;
            
            if(record_mouse_release) {
                int x, y; x = e.getXOnScreen(); y = e.getYOnScreen();
//                paint_mouserelease_to_screen(x,y);
                switch(e.getButton()){
                    case MouseEvent.BUTTON1:
//                        System.out.println(e.getButton()+" 1");
                        feed(new MouseReleased(InputEvent.BUTTON1_MASK, x, y, (int)update_time()));
                        break;
                    case MouseEvent.BUTTON2:
//                        System.out.println(e.getButton()+" 2");
                        feed(new MouseReleased(InputEvent.BUTTON3_DOWN_MASK, x, y, (int)update_time()));
                        break;
                    case MouseEvent.BUTTON3:
//                        System.out.println(e.getButton()+" 3");
                        feed(new MouseReleased(InputEvent.BUTTON3_MASK, x, y, (int)update_time()));
                        break;
//                    default: System.out.println(e.getButton());
                }
            }
        } @Override public void mouseEntered(MouseEvent e) {
        } @Override public void mouseExited(MouseEvent e) {
        } @Override public void keyTyped(KeyEvent e) {
        } @Override public void keyPressed(KeyEvent e) {
            if(record_key_press){
                int code = e.getKeyCode();
                paint_keyrelease_to_screen(code);
                feed(new KeyPressed(code, (int)update_time()));
            }
        } @Override public void keyReleased(KeyEvent e) {
            if(record_key_release){
                int code = e.getKeyCode();
                paint_keypress_to_screen(code);
                feed(new KeyReleased(code, (int)update_time()));
            }
        }
        @Override public void mouseDragged(MouseEvent e) {}
        @Override public void mouseMoved(MouseEvent e) {
            if(record_mouse_movement){
                int x, y; x = e.getXOnScreen(); y = e.getYOnScreen();
                paint_mousemove_to_screen(x, y);
                feed(new MouseMotionAction(x, y, (int)update_time()));
            }
        }
    }
    static boolean button_down = false;
    //this is the core for all things in the recording framework.
    
    private static void exit(){
//        System.out.println("list: " + list.toString());
        try {
            list.save();
        } catch (IOException ex) {ex.printStackTrace();}
        System.out.println("operation sucessful.");
        System.exit(0);
    }
    static void update_mouse_position(){
        Point p = MouseInfo.getPointerInfo().getLocation();
        int x, y; x = p.x; y = p.y;
        paint_mousemove_to_screen(x, y);
        listeners.feed(new MouseMotionAction(x, y, (int)update_time()));
    }
}
class Frame extends JFrame implements Runnable
                {
    private static final Dimension screen_d = Toolkit.getDefaultToolkit().getScreenSize();
    public Frame(){
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBounds(0, 0, screen_d.width, screen_d.height);
        setUndecorated(true);
        
        if(record_key_press||record_key_release){
            addKeyListener(Core.listeners);
            key_list = true;
        }if(record_mouse_movement){
            addMouseMotionListener(Core.listeners);
            motion_list = true;
        }if(record_mouse_press||record_mouse_release){
            addMouseListener(Core.listeners);
            mouse_list = true;
        }
        switch(CHOSEN_OPTION){
            case PER_PIXEL_TRANSLU_VAL:
            case PER_PIXEL_TRANSP_VAL:
                //will come in later
            case TRANSLUCENT_VAL:
                setOpacity(0.02f);
        }
        setVisible(true);
        
    }
    private static boolean key_list = false;
    private static boolean mouse_list = false;
    private static boolean motion_list = false;
    public static int sleep_time = 50;
    
    @Override public void run() {
        while(true){
//            if(key_list != (record_key_press||record_key_release)){
//                key_list = !key_list;
//            } if(motion_list != (record_mouse_movement)){
//                motion_list = !motion_list;
//            } if(mouse_list != (record_mouse_press||record_mouse_release)){
//                mouse_list = ! mouse_list;
//            }
//            repaint();
            if(Core.button_down && record_mouse_movement){
                Core.update_mouse_position();
            }
            try{
                Thread.currentThread().sleep(sleep_time);
            }catch(InterruptedException e){}
        }
    }
    
}