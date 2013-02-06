/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.keys;

import simulation.Roboto;

/**
 *
 * @author kieda
 */
public class KeyReleased extends KeyAction {
    public KeyReleased(int key_action, int delay_time) {
        super(key_action, delay_time, data.Cerealizable.KEY_RELEASED);
    }
    @Override public void run(){
//        System.out.println(Core.update_time()+" " + get_delay_time());
        Roboto.fire_keyrelease(id());
    }
}
