package playback;

import data.Action;
import data.ActionList;
import static data.Action.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import simulation.Roboto;
/**
 *
 * @author kieda
 */
public class Repeater {
    private static Timer evt_timer = new Timer();
    public static void time_event(Action act){
        evt_timer.schedule(act, act.get_delay_time());
        try {
            Thread.currentThread().sleep(act.get_delay_time());
        } catch (InterruptedException ex) {}
    }
    public static void repeat_after_me(ActionList al){
        Action node = al.getHead();
        do{
            time_event(node);
        }while((node = node.next()) != null);
        System.exit(0);
    }
}
