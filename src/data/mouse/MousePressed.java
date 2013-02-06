/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.mouse;

import simulation.Roboto;

/**
 *
 * @author kieda
 */
public class MousePressed extends MouseAction{
    public MousePressed(int mouse_action, int x_pos, int y_pos, int delay_time) {
        super(mouse_action,x_pos,y_pos, delay_time, data.Cerealizable.MOUSE_PRESSED);
    }
    @Override public void run(){
        int[] info = important();
        assert info.length == 2;
        Roboto.fire_mouse_press(id(), info[0], info[1]); 
    }
}
