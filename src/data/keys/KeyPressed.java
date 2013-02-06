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
public class KeyPressed extends KeyAction {
    public KeyPressed(int key_action, int delay_time) {
        super(key_action, delay_time, data.Cerealizable.KEY_PRESSED);
    }
    @Override public void run(){
//                System.out.println(Core.update_time()+" " + get_delay_time());
                Roboto.fire_keypress(id());
    }
}
