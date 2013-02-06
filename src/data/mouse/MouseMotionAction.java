/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.mouse;

import data.Action;
import recorder.Core;
import simulation.Roboto;
/**
 *
 * @author kieda
 */
public class MouseMotionAction extends Action{
    final int x_pos, y_pos;
    public MouseMotionAction(int x_pos, int y_pos, int delay_time) {
        super(delay_time, MOUSE_MOTION);
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }
//    @Override public void write(BufferedWriter out) throws IOException{
//        out.write("[");
//        out.write(x_pos+" "+y_pos+" "+delay_time);
//        out.write("]");
//        if(next != null){
//            out.write(" => ");
//            next.write(out);
//        }
//    }
    @Override protected int[] important() {
        return new int[]{x_pos, y_pos};
    }
    @Override protected int id() {
        return NO_ID;
    }
    @Override public void run(){
        int[] info = important();
        assert info.length == 2;
        Roboto.fire_mouse_move(info[0], info[1]);
    }
}
